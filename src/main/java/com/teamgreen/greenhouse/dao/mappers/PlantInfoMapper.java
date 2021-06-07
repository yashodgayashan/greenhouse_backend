package com.teamgreen.greenhouse.dao.mappers;

import com.teamgreen.greenhouse.dao.PlantInfo;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.teamgreen.greenhouse.constants.Constants.MODIFIED_AT;
import static com.teamgreen.greenhouse.constants.Constants.IS_DISABLED;
import static com.teamgreen.greenhouse.constants.Constants.CREATED_AT;
import static com.teamgreen.greenhouse.plant.Constants.PLANT_INFO_ID;
import static com.teamgreen.greenhouse.plant.Constants.PLANT_INFO_SPECIES;
import static com.teamgreen.greenhouse.plant.Constants.PLANT_INFO_NAME;
import static com.teamgreen.greenhouse.plant.Constants.PLANT_INFO_DESCRIPTION;
import static com.teamgreen.greenhouse.plant.Constants.PLANT_INFO_DURATION;
import static com.teamgreen.greenhouse.plant.Constants.PLANT_INFO_MIN_TEMP_LOW;
import static com.teamgreen.greenhouse.plant.Constants.PLANT_INFO_MIN_TEMP_HIGH;
import static com.teamgreen.greenhouse.plant.Constants.PLANT_INFO_MAX_TEMP_LOW;
import static com.teamgreen.greenhouse.plant.Constants.PLANT_INFO_MAX_TEMP_HIGH;
import static com.teamgreen.greenhouse.plant.Constants.PLANT_INFO_SPACING;
import static com.teamgreen.greenhouse.plant.Constants.PLANT_INFO_PLANTS_PER_PLOT;
import static com.teamgreen.greenhouse.plant.Constants.PLANT_INFO_MIN_NO_OF_HARVEST;
import static com.teamgreen.greenhouse.plant.Constants.PLANT_INFO_MAX_NO_OF_HARVEST;
import static com.teamgreen.greenhouse.plant.Constants.PLANT_INFO_AVG_WEIGHT_OF_HARVEST;
import static com.teamgreen.greenhouse.plant.Constants.PLANT_INFO_STAGE1_DURATION;
import static com.teamgreen.greenhouse.plant.Constants.PLANT_INFO_STAGE2_DURATION;
import static com.teamgreen.greenhouse.plant.Constants.PLANT_INFO_STAGE3_DURATION;
import static com.teamgreen.greenhouse.plant.Constants.PLANT_INFO_STAGE4_DURATION;
import static com.teamgreen.greenhouse.plant.Constants.PLANT_INFO_SOLID;

public class PlantInfoMapper implements RowMapper<PlantInfo> {

    @Override
    public PlantInfo mapRow(ResultSet rs, int i) throws SQLException {
        PlantInfo plantInfo = new PlantInfo();
        plantInfo.setId(rs.getLong(PLANT_INFO_ID));
        plantInfo.setName(rs.getString(PLANT_INFO_NAME));
        plantInfo.setSpecies(rs.getString(PLANT_INFO_SPECIES));
        plantInfo.setDescription(rs.getString(PLANT_INFO_DESCRIPTION));
        plantInfo.setPlantDuration(rs.getDouble(PLANT_INFO_DURATION));
        plantInfo.setMinTemperatureLow(rs.getDouble(PLANT_INFO_MIN_TEMP_LOW));
        plantInfo.setMinTemperatureHigh(rs.getDouble(PLANT_INFO_MIN_TEMP_HIGH));
        plantInfo.setMaxTemperatureLow(rs.getDouble(PLANT_INFO_MAX_TEMP_LOW));
        plantInfo.setMaxTemperatureHigh(rs.getDouble(PLANT_INFO_MAX_TEMP_HIGH));
        plantInfo.setSpacing(rs.getDouble(PLANT_INFO_SPACING));
        plantInfo.setPlantsPerPot(rs.getInt(PLANT_INFO_PLANTS_PER_PLOT));
        plantInfo.setMinNoOfHarvest(rs.getInt(PLANT_INFO_MIN_NO_OF_HARVEST));
        plantInfo.setMaxNumberOfHarvest(rs.getInt(PLANT_INFO_MAX_NO_OF_HARVEST));
        plantInfo.setAverageWeightOfHarvest(rs.getDouble(PLANT_INFO_AVG_WEIGHT_OF_HARVEST));
        plantInfo.setStage1Duration(rs.getDouble(PLANT_INFO_STAGE1_DURATION));
        plantInfo.setStage2Duration(rs.getDouble(PLANT_INFO_STAGE2_DURATION));
        plantInfo.setStage3Duration(rs.getDouble(PLANT_INFO_STAGE3_DURATION));
        plantInfo.setStage4Duration(rs.getDouble(PLANT_INFO_STAGE4_DURATION));
        plantInfo.setSolid(rs.getString(PLANT_INFO_SOLID));
        plantInfo.setDisabled(rs.getBoolean(IS_DISABLED));
        plantInfo.setCreatedAt(rs.getTimestamp(CREATED_AT));
        plantInfo.setModifiedAt(rs.getTimestamp(MODIFIED_AT));
        return plantInfo;
    }
}
