package com.lemon.cas.service.impl;


import com.lemon.cas.entity.UserBaseInfoEntity;
import com.lemon.cas.exception.auth.login.AccountConfuseException;
import com.lemon.cas.exception.auth.login.AccountNotPresenceException;
import com.lemon.cas.service.UserBaseInfoService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.security.auth.login.AccountException;
import javax.sql.DataSource;
import java.util.List;

/**
 * @author lemon
 * @description
 * @date 2020-05-14 21:20
 */
@Slf4j
public class UserBaseInfoServiceImpl implements UserBaseInfoService {
    @Getter
    @Setter
    private String querUserSql;

    @Getter
    private final JdbcTemplate jdbcTemplate;

    public UserBaseInfoServiceImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * @param userName
     * @return com.lemon.cas.entity.UserBaseInfoEntity
     * @description 登录页面异常信息展示 authenticationFailure. + e.getClass().getSimpleName()
     * @author houjuntao
     * @date 2020-05-16 11:30
     */
    @Override
    public UserBaseInfoEntity queryByUserName(String userName) throws AccountException {
        List<UserBaseInfoEntity> userList = this.jdbcTemplate.query(this.querUserSql, new Object[]{userName}, new BeanPropertyRowMapper(UserBaseInfoEntity.class));

        if (CollectionUtils.isEmpty(userList)) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(String.format("Account [%s] does not exist", userName));
            }

            throw new AccountNotPresenceException();
        }

        if (userList.size() > 1) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(String.format("There are %s accounts [%s]", userList.size(), userName));
            }

            throw new AccountConfuseException();
        }

        return userList.get(0);
    }
}
