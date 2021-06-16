package com.teamgreen.greenhouse.dao.mappers;

import com.teamgreen.greenhouse.dao.CropInfo;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.teamgreen.greenhouse.greenhouses.crop.Constants.*;

public class CropInfoMapper implements RowMapper<CropInfo> {

    @Override
    public CropInfo mapRow(ResultSet rs, int i) throws SQLException {
        CropInfo cropInfo = new CropInfo();
        cropInfo.setId(rs.getLong(CROP_INFO_ID));
        cropInfo.setCropId(rs.getLong(CROP_INFO_CROP_ID));
        cropInfo.setLength(rs.getDouble(CROP_INFO_LENGTH));
        cropInfo.setDate(rs.getTimestamp(CROP_INFO_DATE));
        return cropInfo;
    }
}