package com.gct.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gct.mapper.SysRoleMapper;
import com.gct.pojo.dto.system.SysRoleDto;
import com.gct.pojo.entity.system.SysRole;
import com.gct.pojo.vo.Result.Result;
import com.gct.service.SysRoleService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author 33980
* @description 针对表【sys_role(角色)】的数据库操作Service实现
* @createDate 2024-03-28 22:52:59
*/
@Service
@Slf4j
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole>
    implements SysRoleService{

    @Resource
    private SysRoleMapper sysRoleMapper;

    @Override
    public Result<Map<String,Object>> getSysRoleListByPage(SysRoleDto sysRoleDto, int pageNumber, int pageSize) {
        Page<SysRole> page = new Page(pageNumber,pageSize);
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRole::getIsDeleted,0)
                .orderByAsc(SysRole::getCreateTime);
        if(!StrUtil.isEmpty(sysRoleDto.getRoleName())){
            wrapper.like(SysRole::getRoleName,sysRoleDto.getRoleName());
        }
        sysRoleMapper.selectPage(page, wrapper);
        List<SysRole> records = page.getRecords();
        Map<String,Object> map = new HashMap<>();
        log.info(records.toString());
        map.put("list",page.getRecords());
        map.put("value",page.getTotal());
        return Result.success(map);

    }

    @Override
    public Result saveSysRole(SysRole sysRole) {
        int insert = sysRoleMapper.insert(sysRole);
        return Result.success(null);
    }

    @Override
    public Result updateSyeRoleById(SysRole sysRole) {
        LambdaUpdateWrapper<SysRole> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(SysRole::getId,sysRole.getId())
                .set(SysRole::getUpdateTime, DateUtil.format(new Date(),"YYYY-MM-dd HH:mm:ss"));
        if(sysRole.getRoleName()!=null){
            wrapper.set(SysRole::getRoleName,sysRole.getRoleName());
        }
        if(sysRole.getRoleCode()!=null){
            wrapper.set(SysRole::getRoleCode,sysRole.getRoleCode());
        }
        sysRoleMapper.update(wrapper);
        return Result.success(null);
    }

    @Override
    public Result removeSysRoleById(int id) {
        LambdaUpdateWrapper<SysRole> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(SysRole::getId,id)
                .set(SysRole::getIsDeleted,1);
        sysRoleMapper.update(wrapper);
        return Result.success(null);
    }

    @Override
    public Result<Map<String, Object>> getAllRoleListAndRoleNameById(int id) {
        LambdaQueryWrapper<SysRole> wrapper  = new LambdaQueryWrapper<>();
        wrapper.eq(SysRole::getIsDeleted,0);
        List<SysRole> sysRoles = sysRoleMapper.selectList(wrapper);
        List<Integer> RoleId = sysRoleMapper.selectRoleIdByUserId(id);
        Map<String,Object> map = new HashMap<>();
        map.put("list",sysRoles);
        map.put("value",RoleId);
        return Result.success(map);

    }
}




