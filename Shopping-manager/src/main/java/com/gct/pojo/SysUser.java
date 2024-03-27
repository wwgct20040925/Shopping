package com.gct.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @TableName sys_user
 */
@TableName(value ="sys_user")
@Data
public class SysUser implements Serializable {
    private Long id;

    private String username;

    private String password;

    private String name;

    private String phone;

    private String avatar;

    private String description;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    private Integer isDeleted;

    private static final long serialVersionUID = 1L;
}