package com.teamgreen.greenhouse.dao.mappers;

import com.teamgreen.greenhouse.dao.PlantHarvest;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.teamgreen.greenhouse.constants.Constants.CREATED_AT;
import static com.teamgreen.greenhouse.constants.Constants.MODIFIED_AT;
import static com.teamgreen.greenhouse.greenhouses.plantHarvest.Constants.*;

public class PlantHarvestMapper implements RowMapper<PlantHarvest> {

    @Override
    public PlantHarvest mapRow(ResultSet rs, int i) throws SQLException {
        PlantHarvest plantDisease = new PlantHarvest();
        plantDisease.setId(rs.getLong(PLANT_HARVEST_ID));
        plantDisease.setPlantId(rs.getLong(PLANT_HARVEST_PLANT_ID));
        plantDisease.setCount(rs.getLong(PLANT_HARVEST_COUNT));
        plantDisease.setDate(rs.getTimestamp(PLANT_HARVEST_DATE));
        plantDisease.setCreatedAt(rs.getTimestamp(CREATED_AT));
        plantDisease.setModifiedAt(rs.getTimestamp(MODIFIED_AT));
        return plantDisease;
    }
}
