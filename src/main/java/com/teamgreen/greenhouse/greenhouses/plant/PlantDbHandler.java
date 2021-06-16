package com.teamgreen.greenhouse.greenhouses.plant;

import com.teamgreen.greenhouse.dao.Plant;
import com.teamgreen.greenhouse.dao.mappers.PlantMapper;
import com.teamgreen.greenhouse.store.DbHandler;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

import static com.teamgreen.greenhouse.greenhouses.plant.Constants.*;
import static com.teamgreen.greenhouse.utils.MiscellaneousUtils.*;

public class PlantDbHandler extends DbHandler {

    public PlantDbHandler(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }

    public List<Plant> getPlants(long greenhousePlantId) {
        final String query = "SELECT * FROM " + PLANTS_TABLE + " WHERE "
                + PLANTS_GREENHOUSE_PLANT_ID + " = ? ORDER BY " + PLANTS_ID + " DESC";
        return this.jdbcTemplate().query(query, new PlantMapper(), greenhousePlantId);
    }


    public int addPlant(Plant plant)  {
        final String insertQuery =
                "INSERT INTO " + PLANTS_TABLE + " ("  + withComma(PLANTS_PLANT_ID) + withComma(PLANTS_GREENHOUSE_ID)
                        + encapFieldWithBackTick(PLANTS_GREENHOUSE_PLANT_ID) + ") VALUES "
                        + getStatementParams(3);

        return this.jdbcTemplate().update(insertQuery, plant.getPlantId(), plant.getGreenhouseId(),
                plant.getGreenhousePlantId());
    }
}
