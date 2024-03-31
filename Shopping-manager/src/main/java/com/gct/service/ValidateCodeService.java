package com.gct.service;

import com.gct.pojo.vo.Result.Result;

public interface ValidateCodeService {
    /**
     * 获取验证码
     * @return
     */

    Result getCaptcha();
}
