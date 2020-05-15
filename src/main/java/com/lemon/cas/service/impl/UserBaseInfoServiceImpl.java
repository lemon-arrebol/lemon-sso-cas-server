package com.lemon.cas.service.impl;


import com.lemon.cas.entity.UserBaseInfoEntity;
import com.lemon.cas.service.UserBaseInfoService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @author lemon
 * @description
 * @date 2020-05-14 21:20
 */
public class UserBaseInfoServiceImpl implements UserBaseInfoService {
    @Getter
    @Setter
    private String querUserSql;

    @Getter
    private final JdbcTemplate jdbcTemplate;

    public UserBaseInfoServiceImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public UserBaseInfoEntity selectByUserName(String userName) {
        return (UserBaseInfoEntity) this.jdbcTemplate.queryForObject(this.querUserSql, new Object[]{userName}, new BeanPropertyRowMapper(UserBaseInfoEntity.class));
    }

}
