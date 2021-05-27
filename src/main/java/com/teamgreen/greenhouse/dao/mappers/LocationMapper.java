package com.teamgreen.greenhouse.dao.mappers;

import com.teamgreen.greenhouse.dao.Location;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.teamgreen.greenhouse.constants.Constants.CREATED_AT;
import static com.teamgreen.greenhouse.constants.Constants.IS_DISABLED;
import static com.teamgreen.greenhouse.constants.Constants.MODIFIED_AT;
import static com.teamgreen.greenhouse.locations.Constants.LOCATION_ID;
import static com.teamgreen.greenhouse.locations.Constants.LOCATION_NAME;
import static com.teamgreen.greenhouse.locations.Constants.LOCATION_LOCATION;
import static com.teamgreen.greenhouse.locations.Constants.LOCATION_IMAGE_URL;
import static com.teamgreen.greenhouse.locations.Constants.LOCATION_LATITUDE;
import static com.teamgreen.greenhouse.locations.Constants.LOCATION_LONGATUDE;

public class LocationMapper implements RowMapper<Location> {

    @Override
    public Location mapRow(ResultSet rs, int i) throws SQLException {
        Location location = new Location();
        location.setId(rs.getLong(LOCATION_ID));
        location.setName(rs.getString(LOCATION_NAME));
        location.setLocation(rs.getString(LOCATION_LOCATION));
        location.setImageURL(rs.getString(LOCATION_IMAGE_URL));
        location.setLatitude(rs.getDouble(LOCATION_LATITUDE));
        location.setLongatude(rs.getDouble(LOCATION_LONGATUDE));
        location.setDisabled(rs.getBoolean(IS_DISABLED));
        location.setCreatedAt(rs.getTimestamp(CREATED_AT));
        location.setModifiedAt(rs.getTimestamp(MODIFIED_AT));
        return location;
    }
}
