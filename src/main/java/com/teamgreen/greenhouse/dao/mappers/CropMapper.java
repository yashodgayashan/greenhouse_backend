package com.teamgreen.greenhouse.dao.mappers;

import com.teamgreen.greenhouse.dao.Crop;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.teamgreen.greenhouse.constants.Constants.*;
import static com.teamgreen.greenhouse.greenhouses.crop.Constants.*;

public class CropMapper implements RowMapper<Crop> {

    @Override
    public Crop mapRow(ResultSet rs, int i) throws SQLException {
        Crop crop = new Crop();
        crop.setId(rs.getLong(CROP_ID));
        crop.setPlantId(rs.getLong(CROP_PLANT_ID));
        crop.setDepth(rs.getDouble(CROP_DEPTH));
        crop.setLength(rs.getDouble(CROP_LENGTH));
        crop.setHeight(rs.getDouble(CROP_HEIGHT));
        return crop;
    }
}
