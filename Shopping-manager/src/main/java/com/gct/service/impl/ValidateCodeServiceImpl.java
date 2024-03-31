package com.gct.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import com.gct.pojo.vo.Result.Result;
import com.gct.pojo.vo.system.ValidateCodeVo;
import com.gct.service.ValidateCodeService;
import com.gct.util.ConstUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class ValidateCodeServiceImpl implements ValidateCodeService {

    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public Result getCaptcha() {
        // 使用hutool工具包中的工具类生成图片验证码
        //参数：宽  高  验证码位数 干扰线数量
        CircleCaptcha captcha = CaptchaUtil.createCircleCaptcha(150, 48, 6, 10);
        String code = captcha.getCode();
        String imageBase64 = captcha.getImageBase64();
        //生成时间戳
        String uuid = UUID.randomUUID().toString().replace("-", "");
        //向redis中缓存时间戳，以及对应的真实验证码，后续验证验证码时传入的是时间戳作为key，让后比较value即可
        redisTemplate.opsForValue().set(ConstUtil.USER_VALIDATE +uuid,code,1, TimeUnit.MINUTES);
        //创建向前端返回的实体类
        ValidateCodeVo validateCodeVo = new ValidateCodeVo();
        validateCodeVo.setCodeKey(uuid);
        //此处返回图片，直接粘贴即可
        validateCodeVo.setCodeValue("data:image/png;base64," + imageBase64);
        return Result.success(validateCodeVo);
     }
}
