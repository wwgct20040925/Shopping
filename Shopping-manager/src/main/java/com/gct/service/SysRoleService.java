package com.gct.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.gct.pojo.dto.system.SysRoleDto;
import com.gct.pojo.entity.system.SysRole;
import com.gct.pojo.vo.Result.Result;

import java.util.Map;

/**
* @author 33980
* @description 针对表【sys_role(角色)】的数据库操作Service
* @createDate 2024-03-28 22:52:59
*/
public interface SysRoleService extends IService<SysRole> {

    /**
     * 根据角色名称查询分页数据
     * @param
     * @param pageNumber
     * @param pageSize
     * @return
     */
    Result<Map<String,Object>> getSysRoleListByPage(SysRoleDto sysRoleDto, int pageNumber, int pageSize);

    Result saveSysRole(SysRole sysRole);

    Result updateSyeRoleById(SysRole sysRole);

    Result removeSysRoleById(int id);

    Result<Map<String, Object>> getAllRoleListAndRoleNameById(int id);
}
