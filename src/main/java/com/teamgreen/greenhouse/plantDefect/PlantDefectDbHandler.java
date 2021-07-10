package com.teamgreen.greenhouse.plantDefect;

import com.teamgreen.greenhouse.dao.PlantDisease;
import com.teamgreen.greenhouse.dao.mappers.PlanDiseaseMapper;
import com.teamgreen.greenhouse.exceptions.CustomException;
import com.teamgreen.greenhouse.store.DbHandler;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

import static com.teamgreen.greenhouse.constants.Constants.IS_DISABLED;
import static com.teamgreen.greenhouse.plantDefect.Constants.*;
import static com.teamgreen.greenhouse.utils.MiscellaneousUtils.getUpdateSyntaxFinal;
import static com.teamgreen.greenhouse.utils.MiscellaneousUtils.withComma;
import static com.teamgreen.greenhouse.utils.MiscellaneousUtils.encapFieldWithBackTick;
import static com.teamgreen.greenhouse.utils.MiscellaneousUtils.getStatementParams;
import static com.teamgreen.greenhouse.utils.MiscellaneousUtils.getUpdateSyntax;

public class PlantDefectDbHandler extends DbHandler {

    public PlantDefectDbHandler(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }

    public List<PlantDisease> getPlantDisease(int plant) {
        final String query = "SELECT * FROM " + PLANT_TABLE + " WHERE " + PLANT_PLANT_ID + " = ? ORDER BY " + PLANT_ID + " DESC";
        return this.jdbcTemplate().query(query, new PlanDiseaseMapper(), plant);
    }
}
