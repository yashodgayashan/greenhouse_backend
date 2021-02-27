package com.teamgreen.greenhouse.store;

import org.springframework.jdbc.core.JdbcTemplate;

public class DbHandler {

    private JdbcTemplate jdbcTemplate;

    public DbHandler(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public JdbcTemplate jdbcTemplate() {
        return this.jdbcTemplate;
    }

}
