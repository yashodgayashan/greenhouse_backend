package com.teamgreen.greenhouse.locations;

import com.teamgreen.greenhouse.dao.Location;
import com.teamgreen.greenhouse.dao.mappers.LocationMapper;
import com.teamgreen.greenhouse.exceptions.CustomException;
import com.teamgreen.greenhouse.store.DbHandler;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static com.teamgreen.greenhouse.constants.Constants.IS_DISABLED;
import static com.teamgreen.greenhouse.locations.Constants.LOCATION_ID;
import static com.teamgreen.greenhouse.locations.Constants.LOCATIONS_TABLE;
import static com.teamgreen.greenhouse.locations.Constants.LOCATION_NAME;
import static com.teamgreen.greenhouse.locations.Constants.LOCATION_LOCATION;
import static com.teamgreen.greenhouse.locations.Constants.LOCATION_IMAGE_URL;
import static com.teamgreen.greenhouse.utils.MiscellaneousUtils.*;

public class LocationDbHandler extends DbHandler {

    public LocationDbHandler(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    List<Location> getLocations() {
        final String query = "SELECT * FROM " + LOCATIONS_TABLE + " ORDER BY " + LOCATION_ID + " DESC";
        return this.jdbcTemplate().query(query, new LocationMapper());
    }

    Location getLocation(long id) throws CustomException {
        try {
            final String query = "SELECT * FROM " + LOCATIONS_TABLE + " WHERE " + LOCATION_ID + " = ?";
            return this.jdbcTemplate().queryForObject(query, new LocationMapper(), id);
        } catch (Exception e) {
            throw new CustomException("There is no location found with id: " + id);
        }
    }

    int addLocation(Location location)  {
        final String insertQuery =
                "INSERT INTO " + LOCATIONS_TABLE + " ("  + withComma(LOCATION_NAME) + withComma(LOCATION_LOCATION)
                        + encapFieldWithBackTick(LOCATION_IMAGE_URL) + ") VALUES " + getStatementParams(3);

        return this.jdbcTemplate().update(insertQuery, location.getName(), location.getLocation(), location.getImageURL());
    }

    int updateLocation(long id, Location location)  {
        final String updateQuery =
                "UPDATE " + LOCATIONS_TABLE + " SET " + getUpdateSyntax(LOCATION_NAME) + getUpdateSyntax(LOCATION_LOCATION)
                        + getUpdateSyntaxFinal(LOCATION_IMAGE_URL) + " WHERE id = ?";
        return this.jdbcTemplate().update(updateQuery, location.getName(), location.getLocation(),
                location.getImageURL(), id);
    }

    int deleteLocation(long id) {
        final String deleteQuery =
                "UPDATE " + LOCATIONS_TABLE + " SET " + getUpdateSyntaxFinal(IS_DISABLED) + " WHERE id = ?";
        return this.jdbcTemplate().update(deleteQuery, 1, id);
    }
}
