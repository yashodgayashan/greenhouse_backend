package com.teamgreen.greenhouse.store;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class DbHandler {

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedJdbcTemplate;

    public DbHandler(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedJdbcTemplate = namedJdbcTemplate;
    }

    public JdbcTemplate jdbcTemplate() {
        return this.jdbcTemplate;
    }

    public NamedParameterJdbcTemplate namedJdbcTemplate() {
        return this.namedJdbcTemplate;
    }

}
