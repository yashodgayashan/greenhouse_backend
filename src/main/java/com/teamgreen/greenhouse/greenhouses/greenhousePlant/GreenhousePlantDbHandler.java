package com.teamgreen.greenhouse.greenhouses.greenhousePlant;

import com.teamgreen.greenhouse.dao.GreenhousePlants;
import com.teamgreen.greenhouse.dao.Plant;
import com.teamgreen.greenhouse.dao.PlantDisease;
import com.teamgreen.greenhouse.dao.mappers.GreenhousePlantMapper;
import com.teamgreen.greenhouse.exceptions.MysqlHandlerException;
import com.teamgreen.greenhouse.greenhouses.plant.PlantDbHandler;
import com.teamgreen.greenhouse.greenhouses.plantDisease.PlanDiseaseDbHandler;
import com.teamgreen.greenhouse.store.DbHandler;
import com.teamgreen.greenhouse.utils.DbUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.Timestamp;
import java.util.List;
import java.util.ArrayList;

import static com.teamgreen.greenhouse.constants.Constants.IS_DISABLED;
import static com.teamgreen.greenhouse.greenhouses.greenhousePlant.Constants.*;
import static com.teamgreen.greenhouse.utils.MiscellaneousUtils.*;
import static com.teamgreen.greenhouse.utils.MiscellaneousUtils.getUpdateSyntaxFinal;

public class GreenhousePlantDbHandler  extends DbHandler {

    private PlantDbHandler plantDbHandler;
    private PlanDiseaseDbHandler planDiseaseDbHandler;
    private DbUtils dbUtils;

    public GreenhousePlantDbHandler(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
        plantDbHandler = new PlantDbHandler(jdbcTemplate, namedParameterJdbcTemplate);
        planDiseaseDbHandler =  new PlanDiseaseDbHandler(jdbcTemplate, namedParameterJdbcTemplate);
        dbUtils = new DbUtils(jdbcTemplate);
    }

    public List<GreenhousePlants> getGreenhousePlants() {
        final String query = "SELECT * FROM " + GREENHOUSE_PLANTS_TABLE + " WHERE " + IS_DISABLED + " = 0 ORDER BY "
                + GREENHOUSE_PLANTS_ID + " DESC";
        return this.jdbcTemplate().query(query, new GreenhousePlantMapper());
    }

    public List<PlantDisease> getGreenhouseDiseases(long greenhouseId) {
        String getGreenhousePlantId = "Select id from " + GREENHOUSE_PLANTS_TABLE + " WHERE " + GREENHOUSE_PLANTS_GREENHOUSE_ID + " = ?";
        Integer greenhousePlantId = this.jdbcTemplate().queryForObject(getGreenhousePlantId, new Object[] {greenhouseId}, Integer.class);
        String getPlantIds = "Select id from plant where `greenhouse_plant_id` = ?";
        List<Integer> plantIds = this.jdbcTemplate().query(getPlantIds, new Object[] {greenhousePlantId}, Integer.class);
        List<PlantDisease> plantDiseases = new ArrayList<>();
        for (Integer plantId : plantIds) {
            List<PlantDisease> plantDiseaseSet = planDiseaseDbHandler.getPlantDiseases(plantId);
            if (plantDiseaseSet.size() == 0) {
                continue;
            } else {
                plantDiseases.addAll(plantDiseaseSet);
            }
        }
        return plantDiseases;
    }

    public List<GreenhousePlants> getGreenhousePlants(long greenhouseId) {
        final String query = "SELECT * FROM " + GREENHOUSE_PLANTS_TABLE + " WHERE " + IS_DISABLED + " = 0 AND "
                + GREENHOUSE_PLANTS_GREENHOUSE_ID + " = ? ORDER BY " + GREENHOUSE_PLANTS_ID + " DESC";
        return this.jdbcTemplate().query(query, new GreenhousePlantMapper(), greenhouseId);
    }

    public boolean hasActivePlant(long greenhouseId) {
        List<GreenhousePlants> greenhousePlants = getGreenhousePlants(greenhouseId);
        for (GreenhousePlants greenhousePlant : greenhousePlants) {
            if (greenhousePlant.getEndedAt() == null) {
                return true;
            }
        }
        return false;
    }

    public int addPlant(GreenhousePlants greenhousePlant) throws MysqlHandlerException {
        final String insertQuery =
                "INSERT INTO " + GREENHOUSE_PLANTS_TABLE + " ("  + withComma(GREENHOUSE_PLANTS_PLANT_ID) + withComma(GREENHOUSE_PLANTS_GREENHOUSE_ID)
                        + withComma(GREENHOUSE_PLANTS_NO_OF_PLANTS) + withComma(GREENHOUSE_PLANTS_STARTED_AT)
                        + encapFieldWithBackTick(GREENHOUSE_PLANTS_ENDED_AT) + ") VALUES "
                        + getStatementParams(5);

        int result = this.jdbcTemplate().update(insertQuery, greenhousePlant.getPlantId(), greenhousePlant.getGreenhouseId(),
                greenhousePlant.getNumberOfPlants(), greenhousePlant.getStartedAt(), greenhousePlant.getEndedAt());
        if (result > 0) {
            Plant plant = new Plant(greenhousePlant.getPlantId(), greenhousePlant.getGreenhouseId(), dbUtils.getLastIdFromTable(GREENHOUSE_PLANTS_TABLE));
            for (int i = 0; i < greenhousePlant.getNumberOfPlants(); i++) {
                plantDbHandler.addPlant(plant);
            }
            return result;
        } else {
            return result;
        }
    }

    public int updateGreenhousePlant(long id, Timestamp endedAt, boolean isCompleted)  {
        final String updateQuery =
                "UPDATE " + GREENHOUSE_PLANTS_TABLE + " SET " + getUpdateSyntax(GREENHOUSE_PLANTS_ENDED_AT)
                        + getUpdateSyntaxFinal(GREENHOUSE_PLANTS_IS_COMPLETED) + " WHERE id = ?";
        return this.jdbcTemplate().update(updateQuery, endedAt, true, id);
    }
}
