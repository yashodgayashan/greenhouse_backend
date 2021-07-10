package com.teamgreen.greenhouse.greenhouses.plantDisease;

import com.teamgreen.greenhouse.dao.PlantDisease;
import com.teamgreen.greenhouse.dao.mappers.PlanDiseaseMapper;
import com.teamgreen.greenhouse.store.DbHandler;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

import static com.teamgreen.greenhouse.greenhouses.plantDisease.Constants.*;
import static com.teamgreen.greenhouse.utils.MiscellaneousUtils.*;

public class PlanDiseaseDbHandler extends DbHandler {

    public PlanDiseaseDbHandler(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }

    public List<PlantDisease> getPlantDiseases(long plantId) {
        final String query = "SELECT * FROM " + PLANT_DISEASES_TABLE + " WHERE "
                + PLANT_DISEASES_PLANT_ID + " = ? ORDER BY " + PLANT_DISEASES_ID + " DESC";
        return this.jdbcTemplate().query(query, new PlanDiseaseMapper(), plantId);
    }

    public int addPlantDisease(PlantDisease plantDisease)  {
        final String insertQuery =
                "INSERT INTO " + PLANT_DISEASES_TABLE + " ("  + withComma(PLANT_DISEASES_PLANT_ID) + withComma(PLANT_DISEASES_DISEASE_ID)
                        + encapFieldWithBackTick(PLANT_DISEASES_SOLUTION_ID) + ") VALUES "
                        + getStatementParams(3);
        System.out.println(plantDisease.getPlantId());
        System.out.println(plantDisease.getDiseaseId());
        System.out.println(plantDisease.getSolutionId());
        return this.jdbcTemplate().update(insertQuery, plantDisease.getPlantId(), plantDisease.getDiseaseId(),
                plantDisease.getSolutionId());
    }

    public int updatePlantDisease(long plantId, PlantDisease plantDisease) {
        final String updateQuery =
                "UPDATE " + PLANT_DISEASES_TABLE + " SET " + getUpdateSyntax(PLANT_DISEASES_SOLUTION_ID)
                        + getUpdateSyntax(PLANT_DISEASES_APPLIED_DATE) + getUpdateSyntaxFinal(PLANT_DISEASES_RESOLVED_DATE)
                        + " WHERE id = ?";
        return this.jdbcTemplate().update(updateQuery, plantDisease.getSolutionId(), plantDisease.getAppliedDate(),
                plantDisease.getResolvedDate(), plantId);
    }
}
