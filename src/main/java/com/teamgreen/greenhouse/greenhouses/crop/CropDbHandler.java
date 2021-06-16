package com.teamgreen.greenhouse.greenhouses.crop;

import com.teamgreen.greenhouse.dao.Crop;
import com.teamgreen.greenhouse.dao.CropInfo;
import com.teamgreen.greenhouse.dao.mappers.CropInfoMapper;
import com.teamgreen.greenhouse.dao.mappers.CropMapper;
import com.teamgreen.greenhouse.store.DbHandler;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

import static com.teamgreen.greenhouse.greenhouses.crop.Constants.*;
import static com.teamgreen.greenhouse.utils.MiscellaneousUtils.*;

public class CropDbHandler extends DbHandler {

    public CropDbHandler(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }

    public int createCrop(Crop crop)  {
        final String insertQuery =
                "INSERT INTO " + CROP_TABLE + " ("  + withComma(CROP_PLANT_ID) + withComma(CROP_DEPTH) + withComma(CROP_HEIGHT)
                        + encapFieldWithBackTick(CROP_LENGTH) + ") VALUES "
                        + getStatementParams(4);

        return this.jdbcTemplate().update(insertQuery, crop.getPlantId(), crop.getDepth(), crop.getHeight(),
                crop.getLength());
    }

    public List<Crop> getCrops(long plantId) {
        final String query = "SELECT * FROM " + CROP_TABLE + " WHERE "
                + CROP_PLANT_ID + " = ? ORDER BY " + CROP_ID + " DESC";
        return this.jdbcTemplate().query(query, new CropMapper(), plantId);
    }

    public int createCropInfo(CropInfo cropInfo)  {
        final String insertQuery =
                "INSERT INTO " + CROP_INFO_TABLE + " ("  + withComma(CROP_INFO_CROP_ID) + withComma(CROP_INFO_LENGTH)
                        + encapFieldWithBackTick(CROP_INFO_DATE) + ") VALUES "
                        + getStatementParams(4);

        return this.jdbcTemplate().update(insertQuery, cropInfo.getCropId(), cropInfo.getLength(), cropInfo.getDate());
    }

    public List<CropInfo> getCropInfo(long cropId) {
        final String query = "SELECT * FROM " + CROP_INFO_TABLE + " WHERE "
                + CROP_INFO_CROP_ID + " = ? ORDER BY " + CROP_ID + " DESC";
        return this.jdbcTemplate().query(query, new CropInfoMapper(), cropId);
    }
}
