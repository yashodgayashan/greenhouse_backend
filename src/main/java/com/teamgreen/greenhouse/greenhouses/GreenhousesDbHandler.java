package com.teamgreen.greenhouse.greenhouses;

import com.teamgreen.greenhouse.dao.Greenhouse;
import com.teamgreen.greenhouse.dao.mappers.GreenhouseMapper;
import com.teamgreen.greenhouse.exceptions.CustomException;
import com.teamgreen.greenhouse.store.DbHandler;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static com.teamgreen.greenhouse.constants.Constants.IS_DISABLED;
import static com.teamgreen.greenhouse.greenhouses.Constants.GREENHOUSES_TABLE;
import static com.teamgreen.greenhouse.greenhouses.Constants.GREENHOUSE_ID;
import static com.teamgreen.greenhouse.greenhouses.Constants.GREENHOUSE_NAME;
import static com.teamgreen.greenhouse.greenhouses.Constants.GREENHOUSE_LOCATION;
import static com.teamgreen.greenhouse.greenhouses.Constants.GREENHOUSE_LOCATIONS_ID;
import static com.teamgreen.greenhouse.greenhouses.Constants.GREENHOUSE_IMAGE_URL;
import static com.teamgreen.greenhouse.utils.MiscellaneousUtils.withComma;
import static com.teamgreen.greenhouse.utils.MiscellaneousUtils.getUpdateSyntaxFinal;
import static com.teamgreen.greenhouse.utils.MiscellaneousUtils.encapFieldWithBackTick;
import static com.teamgreen.greenhouse.utils.MiscellaneousUtils.getStatementParams;
import static com.teamgreen.greenhouse.utils.MiscellaneousUtils.getUpdateSyntax;

public class GreenhousesDbHandler  extends DbHandler {

    public GreenhousesDbHandler(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    List<Greenhouse> getGreenhouses() {
        final String query = "SELECT * FROM " + GREENHOUSES_TABLE + " ORDER BY " + GREENHOUSE_ID + " DESC";
        return this.jdbcTemplate().query(query, new GreenhouseMapper());
    }

    Greenhouse getGreenhouse(long id) throws CustomException {
        try {
            final String query = "SELECT * FROM " + GREENHOUSES_TABLE + " WHERE " + GREENHOUSE_ID + " = ?";
            return this.jdbcTemplate().queryForObject(query, new GreenhouseMapper(), id);
        } catch (Exception e) {
            throw new CustomException("There is no greenhouse found with id: " + id);
        }
    }

    int addGreenhouse(Greenhouse greenhouse)  {
        final String insertQuery =
                "INSERT INTO " + GREENHOUSES_TABLE + " ("  + withComma(GREENHOUSE_NAME) + withComma(GREENHOUSE_LOCATION)
                        + withComma(GREENHOUSE_LOCATIONS_ID) + encapFieldWithBackTick(GREENHOUSE_IMAGE_URL) + ") VALUES "
                        + getStatementParams(4);

        return this.jdbcTemplate().update(insertQuery, greenhouse.getName(), greenhouse.getLocation(),
                greenhouse.getLocationId(), greenhouse.getImageURL());
    }

    int updateGreenhouse(long id, Greenhouse greenhouse)  {
        final String updateQuery =
                "UPDATE " + GREENHOUSES_TABLE + " SET " + getUpdateSyntax(GREENHOUSE_NAME) + getUpdateSyntax(GREENHOUSE_LOCATION)
                        + getUpdateSyntax(GREENHOUSE_LOCATIONS_ID) + getUpdateSyntaxFinal(GREENHOUSE_IMAGE_URL)
                        + " WHERE id = ?";
        return this.jdbcTemplate().update(updateQuery, greenhouse.getName(), greenhouse.getLocation(), greenhouse.getLocationId(),
                greenhouse.getImageURL(), id);
    }

    int deleteGreenhouse(long id) {
        final String deleteQuery =
                "UPDATE " + GREENHOUSES_TABLE + " SET " + getUpdateSyntaxFinal(IS_DISABLED) + " WHERE id = ?";
        return this.jdbcTemplate().update(deleteQuery, 1, id);
    }
}
