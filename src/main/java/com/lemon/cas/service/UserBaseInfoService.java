package com.lemon.cas.service;

import com.lemon.cas.entity.UserBaseInfoEntity;

import javax.security.auth.login.AccountException;

/**
 * @author lemon
 * @description
 * @date 2020-05-14 21:20
 */
public interface UserBaseInfoService {
    /**
     * @param userName
     * @return com.lemon.cas.entity.UserBaseInfoEntity
     * @description
     * @author lemon
     * @date 2020-05-16 08:23
     */
    UserBaseInfoEntity queryByUserName(String userName) throws AccountException;
}
