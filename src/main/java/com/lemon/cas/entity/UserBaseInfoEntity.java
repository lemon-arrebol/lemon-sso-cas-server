package com.lemon.cas.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author lemon
 * @description
 * @date 2020-05-15 20:56
 */
@Setter
@Getter
public class UserBaseInfoEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 账号ID
     */
    private String id;

    /**
     * 账号
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 状态
     */
    private Boolean status;
    /**
     * 手机号
     */
    private String mobile;
}
