package com.lemon.cas.service;

import com.lemon.cas.entity.UserBaseInfoEntity;

/**
 * @description
 * @author lemon
 * @date 2020-05-14 21:20
 */
public interface UserBaseInfoService {
    UserBaseInfoEntity selectByUserName(String userName);
}
