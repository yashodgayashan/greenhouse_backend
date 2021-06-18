package com.teamgreen.greenhouse.dao.mappers;

import com.teamgreen.greenhouse.dao.Greenhouse;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.teamgreen.greenhouse.constants.Constants.MODIFIED_AT;
import static com.teamgreen.greenhouse.constants.Constants.IS_DISABLED;
import static com.teamgreen.greenhouse.constants.Constants.CREATED_AT;
import static com.teamgreen.greenhouse.greenhouses.Constants.*;

public class GreenhouseMapper  implements RowMapper<Greenhouse> {

    @Override
    public Greenhouse mapRow(ResultSet rs, int i) throws SQLException {
        Greenhouse greenhouse = new Greenhouse();
        greenhouse.setId(rs.getLong(GREENHOUSE_ID));
        greenhouse.setName(rs.getString(GREENHOUSE_NAME));
        greenhouse.setLocationId(rs.getLong(GREENHOUSE_LOCATIONS_ID));
        greenhouse.setLocation(rs.getString(GREENHOUSE_LOCATION));
        greenhouse.setImageURL(rs.getString(GREENHOUSE_IMAGE_URL));
        greenhouse.setLength(rs.getDouble(GREENHOUSE_LENGTH));
        greenhouse.setWidth(rs.getDouble(GREENHOUSE_WIDTH));
        greenhouse.setHeight(rs.getDouble(GREENHOUSE_HEIGHT));
        greenhouse.setWaterFlow(rs.getString(GREENHOUSE_WATER_FLOW));
        greenhouse.setDisabled(rs.getBoolean(IS_DISABLED));
        greenhouse.setCreatedAt(rs.getTimestamp(CREATED_AT));
        greenhouse.setModifiedAt(rs.getTimestamp(MODIFIED_AT));
        return greenhouse;
    }
}
