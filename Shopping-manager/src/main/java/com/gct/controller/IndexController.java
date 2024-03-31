package com.gct.controller;


import com.gct.pojo.dto.system.LoginDto;
import com.gct.pojo.entity.system.SysUser;
import com.gct.pojo.vo.Result.Result;
import com.gct.pojo.vo.system.LoginVo;
import com.gct.pojo.vo.system.ValidateCodeVo;
import com.gct.service.SysUserService;
import com.gct.service.ValidateCodeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;


@Tag(name = "用户接口")
@RestController
@RequestMapping(value = "admin/system/index")
public class IndexController {

    @Resource
    private SysUserService sysUserService;

    @Resource
    private ValidateCodeService validateCodeService;

    /**
     * 验证用户名和密码以及验证码
     * @param loginDto 包含用户名和密码
     * @return  Result类型的数据对象
     */
    @Operation(summary = "用户登录的接口")
    @PostMapping("login")
    public Result<LoginVo> login(@RequestBody LoginDto loginDto){
        return  sysUserService.login(loginDto);

    }

    @Operation(summary = "获取验证码图片")
    @GetMapping("getCaptcha")
    public Result<ValidateCodeVo> getCaptcha(){
        return validateCodeService.getCaptcha();
    }

    @Operation(summary = "根据token获取用户信息")
    @GetMapping("getUserInfo")
    public Result<SysUser> getUserInfo(@RequestHeader(name = "token") String token){
        return sysUserService.getUserInfo(token);
    }
    @Operation(summary = "根据token用户退出")
    @PutMapping("logout")
    public Result logout(@RequestHeader(name = "token") String token){
        return sysUserService.logout(token);
    }



}
