package com.teamgreen.greenhouse.plant;

import com.teamgreen.greenhouse.dao.PlantInfo;
import com.teamgreen.greenhouse.dao.mappers.PlantInfoMapper;
import com.teamgreen.greenhouse.dao.search.dao.PlantInfoSearchDao;
import com.teamgreen.greenhouse.exceptions.CustomException;
import com.teamgreen.greenhouse.store.DbHandler;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

import static com.teamgreen.greenhouse.constants.Constants.IS_DISABLED;
import static com.teamgreen.greenhouse.plant.Constants.PLANT_INFO_ID;
import static com.teamgreen.greenhouse.plant.Constants.PLANT_INFO_TABLE;
import static com.teamgreen.greenhouse.plant.Constants.PLANT_INFO_DESCRIPTION;
import static com.teamgreen.greenhouse.plant.Constants.PLANT_INFO_DURATION;
import static com.teamgreen.greenhouse.plant.Constants.PLANT_INFO_MIN_TEMP;
import static com.teamgreen.greenhouse.plant.Constants.PLANT_INFO_MAX_TEMP;
import static com.teamgreen.greenhouse.plant.Constants.PLANT_INFO_NAME;
import static com.teamgreen.greenhouse.utils.MiscellaneousUtils.getUpdateSyntaxFinal;
import static com.teamgreen.greenhouse.utils.MiscellaneousUtils.withComma;
import static com.teamgreen.greenhouse.utils.MiscellaneousUtils.encapFieldWithBackTick;
import static com.teamgreen.greenhouse.utils.MiscellaneousUtils.getStatementParams;
import static com.teamgreen.greenhouse.utils.MiscellaneousUtils.getUpdateSyntax;

public class PlantInfoDbHandler extends DbHandler {

    public PlantInfoDbHandler(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }

    List<PlantInfo> getPlantInfo() {
        final String query = "SELECT * FROM " + PLANT_INFO_TABLE + " ORDER BY " + PLANT_INFO_ID + " DESC";
        return this.jdbcTemplate().query(query, new PlantInfoMapper());
    }

    PlantInfo getPlanInfo(long id) throws CustomException {
        try {
            final String query = "SELECT * FROM " + PLANT_INFO_TABLE + " WHERE " + PLANT_INFO_ID + " = ?";
            return this.jdbcTemplate().queryForObject(query, new PlantInfoMapper(), id);
        } catch (Exception e) {
            throw new CustomException("There is no plant found with id: " + id);
        }
    }

    int addPlantInfo(PlantInfo plantInfo)  {
        final String insertQuery =
                "INSERT INTO " + PLANT_INFO_TABLE + " ("  + withComma(PLANT_INFO_NAME) + withComma(PLANT_INFO_DESCRIPTION)
                        + withComma(PLANT_INFO_DURATION) + withComma(PLANT_INFO_MIN_TEMP)
                        + encapFieldWithBackTick(PLANT_INFO_MAX_TEMP) + ") VALUES "
                        + getStatementParams(5);

        return this.jdbcTemplate().update(
                insertQuery,
                plantInfo.getName(),
                plantInfo.getDescription(),
                plantInfo.getPlantDuration(),
                plantInfo.getMinTemperature(),
                plantInfo.getMaxTemperature()
        );
    }

    int updatePlantInfo(long id, PlantInfo plantInfo)  {
        final String updateQuery =
                "UPDATE " + PLANT_INFO_TABLE + " SET " + getUpdateSyntax(PLANT_INFO_NAME)
                        + getUpdateSyntax(PLANT_INFO_DESCRIPTION) + getUpdateSyntax(PLANT_INFO_DURATION)
                        + getUpdateSyntax(PLANT_INFO_MIN_TEMP) + getUpdateSyntaxFinal(PLANT_INFO_MAX_TEMP)
                        + " WHERE id = ?";
        return this.jdbcTemplate().update(
                updateQuery,
                plantInfo.getName(),
                plantInfo.getDescription(),
                plantInfo.getPlantDuration(),
                plantInfo.getMinTemperature(),
                plantInfo.getMaxTemperature(),
                id
        );
    }

    int deletePlantInfo(long id) {
        final String deleteQuery =
                "UPDATE " + PLANT_INFO_TABLE + " SET " + getUpdateSyntaxFinal(IS_DISABLED) + " WHERE id = ?";
        return this.jdbcTemplate().update(deleteQuery, 1, id);
    }

    List<PlantInfo> searchPlantInfo(PlantInfoSearchDao searchDao) throws CustomException {
        return this.namedJdbcTemplate().query(
                searchDao.query(true), searchDao.namedParameterMap(), new PlantInfoMapper());
    }
}
