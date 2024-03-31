package com.gct.controller;

import com.gct.pojo.dto.system.AssginRoleDto;
import com.gct.pojo.dto.system.SysUserDto;
import com.gct.pojo.entity.system.SysUser;
import com.gct.pojo.vo.Result.Result;
import com.gct.pojo.vo.Result.ResultCodeEnum;
import com.gct.service.SysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/admin/system/sysUser")
@Tag(name = "用户接口")
public class SysUserController {

    @Resource
    private SysUserService sysUserService;

    @Operation(summary = "根据用户名和时间和分页数据查询用户")
    @GetMapping("getSysUserListByPage/{pageNumber}/{pageSize}")
    public Result<Map<String,Object>> getSysUserListByPage(SysUserDto sysUserDto,
                                                           @PathVariable("pageNumber") int pageNumber,
                                                           @PathVariable("pageSize") int pageSize){


        return sysUserService.getSysUserListByPage(sysUserDto,pageNumber,pageSize);
    }

    @Operation(summary = "增加用户")
    @PostMapping("saveSysUser")
    public Result saveSysUser(@RequestBody SysUser sysUser){
        return sysUserService.saveSysUser(sysUser);
    }
    @Operation(summary = "根据id修改用户信息")
    @PutMapping("updateSysUserById")
    public Result updateSysUserById(@RequestBody SysUser sysUser){
        return sysUserService.updateSysUserById(sysUser);
    }
    @Operation(summary = "删除用户")
    @DeleteMapping ("removeSysUserById")
    public Result removeSysUserById(@RequestParam("id") int id){
        return sysUserService.removeSysUserById(id);
    }

    @Operation(summary = "上传图片的接口")
    @PostMapping("fileUpload")
    public Result<String> fileUpload(@RequestParam("file") MultipartFile multipartFile){
        log.info("fyguhjokpl;");
        String url = sysUserService.fileUpload(multipartFile);
        return Result.build(url, ResultCodeEnum.SUCCESS);

    }
    @Operation(summary = "根据用户Id给用户分配角色请求")
    @PostMapping("AssignRoleByUserId")
    public Result AssignRoleByUserId(@RequestBody AssginRoleDto assginRoleDto){
        return sysUserService.AssignRoleByUserId(assginRoleDto);
    }
    

}
