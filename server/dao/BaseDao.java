package com.banking.grievance.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class BaseDao {

    private final JdbcTemplate jdbcTemplate;

    public BaseDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public JdbcTemplate jdbc() {
        return jdbcTemplate;
    }
}
