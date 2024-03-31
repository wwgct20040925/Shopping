package com.gct.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gct.pojo.dto.system.AssginRoleDto;
import com.gct.pojo.dto.system.LoginDto;
import com.gct.pojo.dto.system.SysUserDto;
import com.gct.pojo.entity.system.SysUser;
import com.gct.pojo.vo.Result.Result;
import com.gct.pojo.vo.system.LoginVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
* @author 33980
* @description 针对表【sys_user(用户表)】的数据库操作Service
* @createDate 2024-03-27 09:39:13
*/
public interface SysUserService extends IService<SysUser> {
    /**
     * 验证用户名和密码
     * @param loginDto
     * @return
     */

    Result<LoginVo> login(LoginDto loginDto);

    /**
     * 根据token获取用户信息
     * @param token
     * @return
     */
    Result<SysUser> getUserInfo(String token);

    /**
     * 根据token用户退出
     * @param token
     * @return
     */
    Result logout(String token);


    Result<Map<String, Object>> getSysUserListByPage(SysUserDto sysUserDto, int pageNumber, int pageSize);

    Result saveSysUser(SysUser sysUser);

    Result updateSysUserById(SysUser sysUser);

    Result removeSysUserById(int id);

    String fileUpload(MultipartFile multipartFile);

    Result AssignRoleByUserId(AssginRoleDto assginRoleDto);
}
