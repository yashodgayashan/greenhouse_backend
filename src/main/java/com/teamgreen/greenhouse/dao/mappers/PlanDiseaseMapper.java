package com.teamgreen.greenhouse.dao.mappers;

import com.teamgreen.greenhouse.dao.PlantDisease;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.teamgreen.greenhouse.constants.Constants.CREATED_AT;
import static com.teamgreen.greenhouse.constants.Constants.MODIFIED_AT;
import static com.teamgreen.greenhouse.greenhouses.plantDisease.Constants.*;

public class PlanDiseaseMapper  implements RowMapper<PlantDisease> {

    @Override
    public PlantDisease mapRow(ResultSet rs, int i) throws SQLException {
        PlantDisease plantDisease = new PlantDisease();
        plantDisease.setId(rs.getLong(PLANT_DISEASES_ID));
        plantDisease.setPlantId(rs.getLong(PLANT_DISEASES_PLANT_ID));
        plantDisease.setDiseaseId(rs.getLong(PLANT_DISEASES_DISEASE_ID));
        plantDisease.setSolutionId(rs.getLong(PLANT_DISEASES_SOLUTION_ID));
        plantDisease.setAppliedDate(rs.getTimestamp(PLANT_DISEASES_APPLIED_DATE));
        plantDisease.setResolvedDate(rs.getTimestamp(PLANT_DISEASES_RESOLVED_DATE));
        plantDisease.setCreatedAt(rs.getTimestamp(CREATED_AT));
        plantDisease.setModifiedAt(rs.getTimestamp(MODIFIED_AT));
        return plantDisease;
    }
}
