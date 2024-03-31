package com.gct.controller;

import com.gct.pojo.dto.system.SysRoleDto;
import com.gct.pojo.entity.system.SysRole;
import com.gct.pojo.vo.Result.Result;
import com.gct.service.SysRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@Tag(name = "角色接口")
@RequestMapping("admin/system/sysRole")
public class SysRoleController {

    @Resource
    private SysRoleService sysRoleService;
//    @Operation(summary = "根据用户角色名查询")
//    @PostMapping("getPageByRoleName/{pageNumber}/{pageSize}")
//    public Result<List<SysRole>> getPageByRoleName(@RequestBody SysRoleDto sysRoleDto,
//                                                   @PathVariable("pageNumber") int pageNumber,
//                                                   @PathVariable("pageSize") int pageSize){
//        return sysRoleService.getPageByRoleName(sysRoleDto,pageNumber,pageSize);
//    }
    @Operation(summary = "根据角色名字和分页数据查询")
    @GetMapping("getSysRoleListByPage/{pageNumber}/{pageSize}")
    public Result<Map<String,Object>> getSysRoleListByPage(SysRoleDto sysRoleDto,
                                                           @PathVariable("pageNumber") int pageNumber,
                                                           @PathVariable("pageSize") int pageSize){
        return sysRoleService.getSysRoleListByPage(sysRoleDto,pageNumber,pageSize);
    }
//    @Operation(summary = "根据角色名字和分页数据查询")
//    @GetMapping("getSysRoleListByPage/{pageNumber}/{pageSize}")
//    public Result getSysRoleListByPage(  SysRoleDto sysRoleDto,
//                                                           @PathVariable("pageNumber") int pageNumber,
//                                                           @PathVariable("pageSize") int pageSize){
//        log.info(sysRoleDto.toString());
//        return Result.success(null);
////        return sysRoleService.getSysRoleListByPage(name,pageNumber,pageSize);
//    }

    @Operation(summary = "添加角色")
    @PostMapping("saveSysRole")
    public Result saveSysRole(@RequestBody SysRole sysRole){
        log.info(sysRole.toString());
        return sysRoleService.saveSysRole(sysRole);
    }

    @Operation(summary = "修改角色")
    @PutMapping("updateSyeRoleById")
    public  Result updateSyeRoleById(@RequestBody SysRole sysRole){
        return sysRoleService.updateSyeRoleById(sysRole);
    }

    @Operation(summary = "删除角色")
    @DeleteMapping("removeSysRoleById")
    public  Result removeSysRoleById(@RequestParam("id") int id){
        return sysRoleService.removeSysRoleById(id);
    }
    @Operation(summary = "查询所有用户角色，返回类型是SysUser，同时根据id查询此用户的角色类型")
    @GetMapping("getAllRoleListAndRoleNameById")
    private Result<Map<String,Object>> getAllRoleListAndRoleNameById(@RequestParam("id") int id){
        return sysRoleService.getAllRoleListAndRoleNameById(id);
    }



}
