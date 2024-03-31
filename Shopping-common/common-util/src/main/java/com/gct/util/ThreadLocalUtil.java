package com.gct.util;

import com.gct.pojo.entity.system.SysUser;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ThreadLocalUtil{

    //创建一个TheadLocal对象
    public static final ThreadLocal<SysUser> threadLocal = new ThreadLocal<>();

    //定义set方法
    public static void set(SysUser sysUser){
        threadLocal.set(sysUser);
    }
    //定义get方法
    public static SysUser get(){
        return threadLocal.get();
    }
    //定义删除方法
    public static void remove(){
        threadLocal.remove();
    }
}
