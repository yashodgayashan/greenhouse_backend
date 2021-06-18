package com.teamgreen.greenhouse.data;

import com.teamgreen.greenhouse.dao.Data;
import com.teamgreen.greenhouse.dao.mappers.DataMapper;
import com.teamgreen.greenhouse.dao.mappers.NodeSensorMapper;
import com.teamgreen.greenhouse.exceptions.CustomException;
import com.teamgreen.greenhouse.store.DbHandler;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.Date;
import java.util.List;

import static com.teamgreen.greenhouse.data.Constants.*;
import static com.teamgreen.greenhouse.nodeSensors.Constants.*;
import static com.teamgreen.greenhouse.utils.MiscellaneousUtils.*;

public class DataDbHandler extends DbHandler {

    public DataDbHandler(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedJdbcTemplate) {
        super(jdbcTemplate, namedJdbcTemplate);
    }

    List<Data> getData() {
        final String query = "SELECT * FROM " + DATA_TABLE + " ORDER BY " + DATA_ID + " DESC";
        return this.jdbcTemplate().query(query, new DataMapper());
    }

    public List<Data> getDataByNodeSensorId(long nodeSensorId, Date startDate, Date endDate) {
        final String query = "SELECT * FROM " + DATA_TABLE + " WHERE " + DATA_NODE_SENSOR_ID
                + " =? AND " + DATA_CREATED_AT + " BETWEEN " + "? AND ? ORDER BY " + DATA_ID + " DESC";
        return this.jdbcTemplate().query(query, new DataMapper(), nodeSensorId, startDate, endDate);
    }

    Data getData(long id) throws CustomException {
        try {
            final String query = "SELECT * FROM " + DATA_TABLE + " WHERE " + DATA_ID + " = ?";
            return this.jdbcTemplate().queryForObject(query, new DataMapper(), id);
        } catch (Exception e) {
            throw new CustomException("There is no data found with id: " + id);
        }
    }

    public int addData(Data data)  {
        final String insertQuery =
                "INSERT INTO " + DATA_TABLE + " ("  + withComma(DATA_NODE_SENSOR_ID)
                        + encapFieldWithBackTick(DATA_DATA) + ") VALUES "
                        + getStatementParams(2);

        return this.jdbcTemplate().update(insertQuery, data.getNodeSensorId(), data.getData());
    }
}
