package com.teamgreen.greenhouse.sensors;

import com.teamgreen.greenhouse.dao.Sensor;
import com.teamgreen.greenhouse.dao.mappers.SensorMapper;
import com.teamgreen.greenhouse.exceptions.CustomException;
import com.teamgreen.greenhouse.store.DbHandler;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static com.teamgreen.greenhouse.constants.Constants.IS_DISABLED;
import static com.teamgreen.greenhouse.sensors.Constants.SENSORS_TABLE;
import static com.teamgreen.greenhouse.sensors.Constants.SENSOR_ID;
import static com.teamgreen.greenhouse.sensors.Constants.*;
import static com.teamgreen.greenhouse.utils.MiscellaneousUtils.*;
import static com.teamgreen.greenhouse.utils.MiscellaneousUtils.getUpdateSyntaxFinal;

public class SensorDbHandler extends DbHandler {

    public SensorDbHandler(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    List<Sensor> getSensors() {
        final String query = "SELECT * FROM " + SENSORS_TABLE + " ORDER BY " + SENSOR_ID + " DESC";
        return this.jdbcTemplate().query(query, new SensorMapper());
    }

    Sensor getSensor(long id) throws CustomException {
        try {
            final String query = "SELECT * FROM " + SENSORS_TABLE + " WHERE " + SENSOR_ID + " = ?";
            return this.jdbcTemplate().queryForObject(query, new SensorMapper(), id);
        } catch (Exception e) {
            throw new CustomException("There is no sensor found with id: " + id);
        }
    }

    int addSensor(Sensor sensor)  {
        final String insertQuery =
                "INSERT INTO " + SENSORS_TABLE + " ("  + withComma(SENSOR_NAME) + withComma(SENSOR_DESCRIPTION)
                        + withComma(SENSOR_DATA_TYPE) + withComma(SENSOR_MIN_VALUE) + withComma(SENSOR_MAX_VALUE)
                        + withComma(SENSOR_TECHNOLOGY) +  withComma(SENSOR_WORKING_VOLTAGE)
                        + withComma(SENSOR_DIMENSIONS) + encapFieldWithBackTick(SENSOR_SPECIAL_FACTS) + ") VALUES "
                        + getStatementParams(9);

        return this.jdbcTemplate().update(
                insertQuery,
                sensor.getName(),
                sensor.getDescription(),
                sensor.getDataType(),
                sensor.getMinValue(),
                sensor.getMaxValue(),
                sensor.getTechnology(),
                sensor.getWorkingVoltage(),
                sensor.getDimensions(),
                sensor.getSpecialFacts()
        );
    }

    int updateSensor(long id, Sensor sensor)  {
        final String updateQuery =
                "UPDATE " + SENSORS_TABLE + " SET " + getUpdateSyntax(SENSOR_NAME) + getUpdateSyntax(SENSOR_DESCRIPTION)
                        + getUpdateSyntax(SENSOR_DATA_TYPE) + getUpdateSyntax(SENSOR_MIN_VALUE)
                        + getUpdateSyntax(SENSOR_MAX_VALUE) + getUpdateSyntax(SENSOR_TECHNOLOGY)
                        + getUpdateSyntax(SENSOR_WORKING_VOLTAGE) + getUpdateSyntax(SENSOR_DIMENSIONS)
                        +getUpdateSyntaxFinal(SENSOR_SPECIAL_FACTS) + " WHERE id = ?";
        return this.jdbcTemplate().update(
                updateQuery,
                sensor.getName(),
                sensor.getDescription(),
                sensor.getDataType(),
                sensor.getMinValue(),
                sensor.getMaxValue(),
                sensor.getTechnology(),
                sensor.getWorkingVoltage(),
                sensor.getDimensions(),
                sensor.getSpecialFacts(),
                id
        );
    }

    int deleteSensor(long id) {
        final String deleteQuery =
                "UPDATE " + SENSORS_TABLE + " SET " + getUpdateSyntaxFinal(IS_DISABLED) + " WHERE id = ?";
        return this.jdbcTemplate().update(deleteQuery, 1, id);
    }

}
