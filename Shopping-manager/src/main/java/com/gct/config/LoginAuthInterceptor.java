package com.gct.config;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.gct.pojo.entity.system.SysUser;
import com.gct.pojo.vo.Result.Result;
import com.gct.pojo.vo.Result.ResultCodeEnum;
import com.gct.util.ConstUtil;
import com.gct.util.ThreadLocalUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

@Configuration
public class LoginAuthInterceptor implements HandlerInterceptor {

    @Resource
    private RedisTemplate<String,String> redisTemplate;

    //请求刚进来时拦截执行的方法
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1 获取请求方式，如果是跨域请求，直接放行
        String method = request.getMethod();
        if(method.equals("OPTIONS")){
            return true;
        }
        //2 判断token是否为空
        String token = request.getHeader("token");
        if(StrUtil.isEmpty(token)){
            responseNoLoginInfo(response);     //向前端响应208状态码
           return false;
        }
        //3 判断token在redis是否有数据
        String userJson = redisTemplate.opsForValue().get(ConstUtil.USER_TOKEN+token);
        if(StrUtil.isEmpty(userJson)){
            responseNoLoginInfo(response);     ////向前端响应208状态码
            return false;
        }
        //把通过token获取的值转成对象
        SysUser sysUser = JSON.parseObject(userJson, SysUser.class);
        //向threadLocal里添加sysUser
        ThreadLocalUtil.set(sysUser);

        //4 更新token过期时间
        redisTemplate.expire(ConstUtil.USER_TOKEN,7, TimeUnit.DAYS);
        return true;

    }
    //响应208状态码给前端
    private void responseNoLoginInfo(HttpServletResponse response) {
        Result<Object> result = Result.build(null, ResultCodeEnum.LOGIN_AUTH);
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print(JSON.toJSONString(result));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) writer.close();
        }
    }

    //所有都做完之后执行（中间还有一步请求结束返回给前端之前的方法：postHandle）
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ThreadLocalUtil.remove();  // 移除threadLocal中的数据
    }
}
