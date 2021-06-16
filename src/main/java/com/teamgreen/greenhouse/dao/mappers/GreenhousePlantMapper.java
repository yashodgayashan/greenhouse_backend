package com.teamgreen.greenhouse.dao.mappers;

import com.teamgreen.greenhouse.dao.GreenhousePlants;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.teamgreen.greenhouse.constants.Constants.CREATED_AT;
import static com.teamgreen.greenhouse.constants.Constants.MODIFIED_AT;
import static com.teamgreen.greenhouse.constants.Constants.IS_DISABLED;
import static com.teamgreen.greenhouse.greenhouses.greenhousePlant.Constants.*;

public class GreenhousePlantMapper  implements RowMapper<GreenhousePlants> {

    @Override
    public GreenhousePlants mapRow(ResultSet rs, int i) throws SQLException {
        GreenhousePlants greenhousePlant = new GreenhousePlants();
        greenhousePlant.setId(rs.getLong(GREENHOUSE_PLANTS_ID));
        greenhousePlant.setPlantId(rs.getLong(GREENHOUSE_PLANTS_PLANT_ID));
        greenhousePlant.setGreenhouseId(rs.getLong(GREENHOUSE_PLANTS_GREENHOUSE_ID));
        greenhousePlant.setNumberOfPlants(rs.getInt(GREENHOUSE_PLANTS_NO_OF_PLANTS));
        greenhousePlant.setCompleted(rs.getBoolean(GREENHOUSE_PLANTS_IS_COMPLETED));
        greenhousePlant.setStartedAt(rs.getTimestamp(GREENHOUSE_PLANTS_STARTED_AT));
        greenhousePlant.setEndedAt(rs.getTimestamp(GREENHOUSE_PLANTS_ENDED_AT));
        greenhousePlant.setDeleted(rs.getBoolean(IS_DISABLED));
        greenhousePlant.setCreatedAt(rs.getTimestamp(CREATED_AT));
        greenhousePlant.setModifiedAt(rs.getTimestamp(MODIFIED_AT));
        return greenhousePlant;
    }
}

