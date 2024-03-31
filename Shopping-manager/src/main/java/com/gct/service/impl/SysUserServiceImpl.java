package com.gct.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gct.MyProperties.MinioProperties;
import com.gct.mapper.SysUserMapper;
import com.gct.pojo.dto.system.AssginRoleDto;
import com.gct.pojo.dto.system.LoginDto;
import com.gct.pojo.dto.system.SysUserDto;
import com.gct.pojo.entity.system.SysUser;
import com.gct.pojo.vo.Result.Result;
import com.gct.pojo.vo.Result.ResultCodeEnum;
import com.gct.pojo.vo.system.LoginVo;
import com.gct.service.SysUserService;
import com.gct.util.ConstUtil;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
* @author 33980
* @description 针对表【sys_user(用户表)】的数据库操作Service实现
* @createDate 2024-03-27 09:39:13
*/
@Service
@Slf4j
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser>
    implements SysUserService{
    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private RedisTemplate<String,String> redisTemplate;

    @Resource
    private MinioProperties minioProperties;

    @Override
    public Result<LoginVo> login(LoginDto loginDto) {
        String codeKey = loginDto.getCodeKey();
        String captcha = loginDto.getCaptcha();
        //构造条件查询
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUserName,loginDto.getUserName());
        //查取结果
        SysUser sysUsers = sysUserMapper.selectOne(wrapper);
        //结果为空，直接返回用户名或者密码错误
        if(sysUsers==null){
            return Result.fail(ResultCodeEnum.LOGIN_ERROR);
        }
        //判断密码是否相同使用DigestUtils.md5DigestAsHex对前端过来的密码加密后比较
        String loginPassword = DigestUtils.md5DigestAsHex(loginDto.getPassword().getBytes());
        if(!loginPassword.equals(sysUsers.getPassword())){
            return Result.fail(ResultCodeEnum.LOGIN_ERROR);
        }
        //判断验证码是否错误
//        String value = redisTemplate.opsForValue().get(ConstUtil.USER_VALIDATE + codeKey);
//        if(StrUtil.isEmpty(value)||!captcha.equals(value)){
//            return Result.fail(ResultCodeEnum.VALIDATECODE_ERROR);
//        }
        //获取token
        String token = UUID.randomUUID().toString().replace("-", "");
        //向数据库中缓存token
        redisTemplate.opsForValue().set(ConstUtil.USER_TOKEN+token, JSON.toJSONString(sysUsers),7, TimeUnit.DAYS);
        //创建返回实体类
        LoginVo loginVo = new LoginVo();
        loginVo.setToken(token);
        loginVo.setRefresh_token(null);
        return Result.success(loginVo);
    }

    @Override
    public Result<SysUser> getUserInfo(String token) {
        String userJson = redisTemplate.opsForValue().get(ConstUtil.USER_TOKEN + token);
        SysUser sysUser = JSON.parseObject(userJson, SysUser.class);
        log.info(sysUser.toString());
        return Result.success(sysUser);
    }


    @Override
    public Result logout(String token) {
        redisTemplate.delete(ConstUtil.USER_TOKEN+token);
        return Result.success(null);
    }

    @Override
    public Result<Map<String, Object>> getSysUserListByPage(SysUserDto sysUserDto, int pageNumber, int pageSize) {
        Page<SysUser> page = new Page<>(pageNumber,pageSize);
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getIsDeleted,0);
        if(!StrUtil.isEmpty(sysUserDto.getKeyword())){
            wrapper.eq(SysUser::getUserName,sysUserDto.getKeyword());
        }
        if(!StrUtil.isEmpty(sysUserDto.getCreateTimeEnd())&&!StrUtil.isEmpty(sysUserDto.getCreateTimeEnd())){
            wrapper.between(SysUser::getCreateTime,sysUserDto.getCreateTimeBegin(),sysUserDto.getCreateTimeEnd());
        }
        sysUserMapper.selectPage(page,wrapper);
        Map<String,Object> map = new HashMap<>();
        map.put("list",page.getRecords());
        map.put("total",page.getTotal());
        return Result.success(map);

    }

    @Override
    public Result saveSysUser(SysUser sysUser) {
        SysUser user1 = sysUserMapper.selectById(sysUser.getId());
        if(user1!=null){
            return Result.fail(ResultCodeEnum.USER_NAME_IS_EXISTS);
        }
        sysUser.setPassword(DigestUtils.md5DigestAsHex(sysUser.getPassword().getBytes()));
        sysUserMapper.insert(sysUser);
        return Result.success(null);
    }

    @Override
    public Result updateSysUserById(SysUser sysUser) {
        LambdaUpdateWrapper<SysUser> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(SysUser::getId,sysUser.getId());
        wrapper.set(SysUser::getCreateTime, DateUtil.format(new Date(),"YYYY-MM-dd HH:mm:ss"));
        sysUserMapper.update(sysUser,wrapper);
        return Result.success(null);
    }

    @Override
    public Result removeSysUserById(int id) {
        LambdaUpdateWrapper<SysUser> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(SysUser::getId,id)
                .set(SysUser::getIsDeleted,1);
        sysUserMapper.update(wrapper);
        return Result.success(null);
    }

    @Override
    public String fileUpload(MultipartFile multipartFile) {

        try {
            // 创建一个Minio的客户端对象
            MinioClient minioClient = MinioClient.builder()
                    .endpoint(minioProperties.getEndpointUrl())
                    .credentials(minioProperties.getUsername(), minioProperties.getPassword())
                    .build();
            log.info("wertyuiopokjbvcvbnm");

            // 判断桶是否存在
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(minioProperties.getBucketName()).build());
            if (!found) {       // 如果不存在，那么此时就创建一个新的桶
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(minioProperties.getBucketName()).build());
            } else {  // 如果存在打印信息
                System.out.println("Bucket 'shopping-minio' already exists.");
            }
            log.info("qw");
            // 设置存储对象名称
            String dateDir = DateUtil.format(new Date(), "yyyyMMdd");
            String uuid = UUID.randomUUID().toString().replace("-", "");
            //20230801/443e1e772bef482c95be28704bec58a901.jpg
            String fileName = dateDir + "/" + uuid + multipartFile.getOriginalFilename();
            System.out.println(fileName);

            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .bucket(minioProperties.getBucketName())
                    .stream(multipartFile.getInputStream(), multipartFile.getSize(), -1)
                    .object(fileName)
                    .build();
            minioClient.putObject(putObjectArgs);

            return minioProperties.getEndpointUrl() + "/" + minioProperties.getBucketName() + "/" + fileName;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Result AssignRoleByUserId(AssginRoleDto assginRoleDto) {
        sysUserMapper.deleteRoleUserByUserId(assginRoleDto.getUserId());
        sysUserMapper.insertRoleUserByUserid(assginRoleDto.getUserId(),assginRoleDto.getRoleIdList());
        return null;
    }
}




