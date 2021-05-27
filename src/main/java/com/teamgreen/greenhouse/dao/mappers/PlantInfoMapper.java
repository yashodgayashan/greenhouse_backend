package com.teamgreen.greenhouse.dao.mappers;

import com.teamgreen.greenhouse.dao.PlantInfo;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.teamgreen.greenhouse.constants.Constants.MODIFIED_AT;
import static com.teamgreen.greenhouse.constants.Constants.IS_DISABLED;
import static com.teamgreen.greenhouse.constants.Constants.CREATED_AT;
import static com.teamgreen.greenhouse.plant.Constants.PLANT_INFO_ID;
import static com.teamgreen.greenhouse.plant.Constants.PLANT_INFO_NAME;
import static com.teamgreen.greenhouse.plant.Constants.PLANT_INFO_DESCRIPTION;
import static com.teamgreen.greenhouse.plant.Constants.PLANT_INFO_DURATION;
import static com.teamgreen.greenhouse.plant.Constants.PLANT_INFO_MIN_TEMP;
import static com.teamgreen.greenhouse.plant.Constants.PLANT_INFO_MAX_TEMP;

public class PlantInfoMapper implements RowMapper<PlantInfo> {

    @Override
    public PlantInfo mapRow(ResultSet rs, int i) throws SQLException {
        PlantInfo plantInfo = new PlantInfo();
        plantInfo.setId(rs.getLong(PLANT_INFO_ID));
        plantInfo.setName(rs.getString(PLANT_INFO_NAME));
        plantInfo.setDescription(rs.getString(PLANT_INFO_DESCRIPTION));
        plantInfo.setPlantDuration(rs.getDouble(PLANT_INFO_DURATION));
        plantInfo.setMinTemperature(rs.getDouble(PLANT_INFO_MIN_TEMP));
        plantInfo.setMaxTemperature(rs.getDouble(PLANT_INFO_MAX_TEMP));
        plantInfo.setDisabled(rs.getBoolean(IS_DISABLED));
        plantInfo.setCreatedAt(rs.getTimestamp(CREATED_AT));
        plantInfo.setModifiedAt(rs.getTimestamp(MODIFIED_AT));
        return plantInfo;
    }
}
