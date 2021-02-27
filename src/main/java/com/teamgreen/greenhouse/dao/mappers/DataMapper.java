package com.teamgreen.greenhouse.dao.mappers;

import com.teamgreen.greenhouse.dao.Data;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.teamgreen.greenhouse.constants.Constants.*;
import static com.teamgreen.greenhouse.data.Constants.*;

public class DataMapper  implements RowMapper<Data> {

    @Override
    public Data mapRow(ResultSet rs, int i) throws SQLException {
        Data data = new Data();
        data.setId(rs.getLong(DATA_ID));
        data.setNodeSensorId(rs.getLong(DATA_NODE_SENSOR_ID));
        data.setData(rs.getDouble(DATA_DATA));
        data.setCreatedAt(rs.getTimestamp(CREATED_AT));
        return data;
    }
}
