package com.teamgreen.greenhouse.dao.mappers;

import com.teamgreen.greenhouse.dao.Plant;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.teamgreen.greenhouse.constants.Constants.*;
import static com.teamgreen.greenhouse.greenhouses.plant.Constants.*;

public class PlantMapper implements RowMapper<Plant> {

    @Override
    public Plant mapRow(ResultSet rs, int i) throws SQLException {
        Plant greenhousePlant = new Plant();
        greenhousePlant.setId(rs.getLong(PLANTS_ID));
        greenhousePlant.setPlantId(rs.getLong(PLANTS_PLANT_ID));
        greenhousePlant.setGreenhouseId(rs.getLong(PLANTS_GREENHOUSE_ID));
        greenhousePlant.setGreenhousePlantId(rs.getInt(PLANTS_GREENHOUSE_PLANT_ID));
        greenhousePlant.setCreatedAt(rs.getTimestamp(CREATED_AT));
        greenhousePlant.setModifiedAt(rs.getTimestamp(MODIFIED_AT));
        return greenhousePlant;
    }
}