package com.gct.util;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class ConstUtil {

    public static final String USER_TOKEN = "user:token";      //redis中token的前缀

    public static final String USER_VALIDATE = "user:validate";//redis中验证码的前缀


}
