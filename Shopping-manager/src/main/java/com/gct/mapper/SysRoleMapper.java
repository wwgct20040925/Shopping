package com.gct.mapper;


import com.gct.pojo.entity.system.SysRole;
import com.github.yulichang.base.MPJBaseMapper;

import java.util.List;

/**
* @author 33980
* @description 针对表【sys_role(角色)】的数据库操作Mapper
* @createDate 2024-03-28 22:52:59
* @Entity com.gct.pojo.SysRole
*/
public interface SysRoleMapper extends MPJBaseMapper<SysRole> {


    List<Integer> selectRoleIdByUserId(int id);
}




