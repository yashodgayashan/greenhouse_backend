package com.teamgreen.greenhouse.greenhouses.plantHarvest;

import com.teamgreen.greenhouse.dao.PlantHarvest;
import com.teamgreen.greenhouse.dao.mappers.PlantHarvestMapper;
import com.teamgreen.greenhouse.store.DbHandler;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.Timestamp;
import java.util.List;

import static com.teamgreen.greenhouse.greenhouses.plantHarvest.Constants.*;
import static com.teamgreen.greenhouse.utils.MiscellaneousUtils.*;

public class PlantHarvestDbHandler extends DbHandler {

    public PlantHarvestDbHandler(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }

    public List<PlantHarvest> getPlantHarvest(long plantId) {
        final String query = "SELECT * FROM " + PLANT_HARVEST_TABLE + " WHERE "
                + PLANT_HARVEST_PLANT_ID + " = ? ORDER BY " + PLANT_HARVEST_ID + " DESC";
        return this.jdbcTemplate().query(query, new PlantHarvestMapper(), plantId);
    }

    public boolean hasPlantHarvest(long plantId, Timestamp date) {
        final String query = "SELECT * FROM " + PLANT_HARVEST_TABLE + " WHERE "
                + PLANT_HARVEST_PLANT_ID + " = ? AND " + PLANT_HARVEST_DATE + " = ? ORDER BY " + PLANT_HARVEST_ID + " DESC";
        List<PlantHarvest>  plantHarvests = this.jdbcTemplate().query(query, new PlantHarvestMapper(), plantId, date);
        if (plantHarvests.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public PlantHarvest getPlantHarvest(long plantId, Timestamp date) {
        final String query = "SELECT * FROM " + PLANT_HARVEST_TABLE + " WHERE "
                + PLANT_HARVEST_PLANT_ID + " = ? AND " + PLANT_HARVEST_DATE + " = ? ORDER BY " + PLANT_HARVEST_ID + " DESC";
        List<PlantHarvest>  plantHarvests = this.jdbcTemplate().query(query, new PlantHarvestMapper(), plantId, date);
        return plantHarvests.get(0);
    }

    public int addPlantHarvest(PlantHarvest plantHarvest)  {
        if (hasPlantHarvest(plantHarvest.getPlantId(), plantHarvest.getDate())) {
            PlantHarvest plantHarvest1 = getPlantHarvest(plantHarvest.getPlantId(), plantHarvest.getDate());
            long count = plantHarvest.getCount();
            plantHarvest.setCount(plantHarvest1.getCount() + count);
            return updatePlantDisease(plantHarvest);
        } else {
            return createPlantHarvest(plantHarvest);
        }
    }

    public int createPlantHarvest(PlantHarvest plantHarvest)  {
        final String insertQuery =
                "INSERT INTO " + PLANT_HARVEST_TABLE + " ("  + withComma(PLANT_HARVEST_PLANT_ID) + withComma(PLANT_HARVEST_COUNT)
                        + encapFieldWithBackTick(PLANT_HARVEST_DATE) + ") VALUES "
                        + getStatementParams(3);

        return this.jdbcTemplate().update(insertQuery, plantHarvest.getPlantId(), plantHarvest.getCount(),
                plantHarvest.getDate());
    }

    public int updatePlantDisease(PlantHarvest plantHarvest) {
        final String updateQuery =
                "UPDATE " + PLANT_HARVEST_TABLE + " SET "  + getUpdateSyntaxFinal(PLANT_HARVEST_COUNT)
                        + " WHERE " + PLANT_HARVEST_PLANT_ID + " = ?";
        return this.jdbcTemplate().update(updateQuery, plantHarvest.getCount(), plantHarvest.getPlantId());
    }

}
