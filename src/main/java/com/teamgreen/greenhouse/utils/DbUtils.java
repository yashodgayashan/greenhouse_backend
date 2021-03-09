package com.teamgreen.greenhouse.utils;

import com.teamgreen.greenhouse.exceptions.MysqlHandlerException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

public class DbUtils {

    final JdbcTemplate jdbc;

    public DbUtils(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public long getLastIdFromTable(String table) throws MysqlHandlerException {
        String query = "SELECT id FROM " + table + " ORDER BY id DESC LIMIT 1";
        Long lastId;
        try {
            lastId = jdbc.queryForObject(query, long.class);
            if (lastId != null && lastId > 0) {
                return lastId;
            } else {
                throw new MysqlHandlerException("empty result when retrieving last id of '" + table + "' table");
            }
        } catch (EmptyResultDataAccessException e) {
            return 0;
        }
    }
}