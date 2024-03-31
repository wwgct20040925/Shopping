package com.gct.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gct.pojo.entity.system.SysUser;

import java.util.List;

/**
* @author 33980
* @description 针对表【sys_user(用户表)】的数据库操作Mapper
* @createDate 2024-03-27 09:39:13
* @Entity com.gct.domain.SysUser
*/
public interface SysUserMapper extends BaseMapper<SysUser> {

    void deleteRoleUserByUserId(Long userId);

    void insertRoleUserByUserid(Long userId, List<Long> roleIdList);
}




