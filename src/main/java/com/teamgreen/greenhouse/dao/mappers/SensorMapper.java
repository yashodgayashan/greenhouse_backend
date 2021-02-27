package com.teamgreen.greenhouse.dao.mappers;

import com.teamgreen.greenhouse.dao.Sensor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.teamgreen.greenhouse.constants.Constants.*;
import static com.teamgreen.greenhouse.sensors.Constants.*;

public class SensorMapper implements RowMapper<Sensor> {

    @Override
    public Sensor mapRow(ResultSet rs, int i) throws SQLException {
        Sensor sensor = new Sensor();
        sensor.setId(rs.getLong(SENSOR_ID));
        sensor.setName(rs.getString(SENSOR_NAME));
        sensor.setDescription(rs.getString(SENSOR_DESCRIPTION));
        sensor.setDataType(rs.getString(SENSOR_DATA_TYPE));
        sensor.setMinValue(rs.getDouble(SENSOR_MIN_VALUE));
        sensor.setMaxValue(rs.getDouble(SENSOR_MAX_VALUE));
        sensor.setTechnology(rs.getString(SENSOR_TECHNOLOGY));
        sensor.setWorkingVoltage(rs.getDouble(SENSOR_WORKING_VOLTAGE));
        sensor.setDimensions(rs.getString(SENSOR_DIMENSIONS));
        sensor.setSpecialFacts(rs.getString(SENSOR_SPECIAL_FACTS));
        sensor.setDisabled(rs.getBoolean(IS_DISABLED));
        sensor.setCreatedAt(rs.getTimestamp(CREATED_AT));
        sensor.setModifiedAt(rs.getTimestamp(MODIFIED_AT));
        return sensor;
    }
}
