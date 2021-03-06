package com.teamgreen.greenhouse.greenhouses;

import com.teamgreen.greenhouse.dao.Greenhouse;
import com.teamgreen.greenhouse.dao.mappers.GreenhouseMapper;
import com.teamgreen.greenhouse.dao.search.dao.GreenhouseSearchDao;
import com.teamgreen.greenhouse.exceptions.CustomException;
import com.teamgreen.greenhouse.store.DbHandler;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

import static com.teamgreen.greenhouse.constants.Constants.IS_DISABLED;
import static com.teamgreen.greenhouse.greenhouses.Constants.*;
import static com.teamgreen.greenhouse.utils.MiscellaneousUtils.withComma;
import static com.teamgreen.greenhouse.utils.MiscellaneousUtils.getUpdateSyntaxFinal;
import static com.teamgreen.greenhouse.utils.MiscellaneousUtils.encapFieldWithBackTick;
import static com.teamgreen.greenhouse.utils.MiscellaneousUtils.getStatementParams;
import static com.teamgreen.greenhouse.utils.MiscellaneousUtils.getUpdateSyntax;

public class GreenhousesDbHandler  extends DbHandler {

    public GreenhousesDbHandler(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }

    List<Greenhouse> getGreenhouses() {
        final String query = "SELECT * FROM " + GREENHOUSES_TABLE + " WHERE " + IS_DISABLED + " = 0 ORDER BY "
                + GREENHOUSE_ID + " DESC";
        return this.jdbcTemplate().query(query, new GreenhouseMapper());
    }

    Greenhouse getGreenhouse(long id) throws CustomException {
        try {
            final String query = "SELECT * FROM " + GREENHOUSES_TABLE + " WHERE " + GREENHOUSE_ID + " = ? AND "
                    + IS_DISABLED + " = 0";
            return this.jdbcTemplate().queryForObject(query, new GreenhouseMapper(), id);
        } catch (Exception e) {
            throw new CustomException("There is no greenhouse found with id: " + id);
        }
    }

    int addGreenhouse(Greenhouse greenhouse)  {
        final String insertQuery =
                "INSERT INTO " + GREENHOUSES_TABLE + " ("  + withComma(GREENHOUSE_NAME) + withComma(GREENHOUSE_LOCATION)
                        + withComma(GREENHOUSE_LOCATIONS_ID) + withComma(GREENHOUSE_HEIGHT) + withComma(GREENHOUSE_WATER_FLOW) + withComma(GREENHOUSE_LENGTH)
                        + withComma(GREENHOUSE_WIDTH) + encapFieldWithBackTick(GREENHOUSE_IMAGE_URL) + ") VALUES "
                        + getStatementParams(8);

        return this.jdbcTemplate().update(insertQuery, greenhouse.getName(), greenhouse.getLocation(),
                greenhouse.getLocationId(), greenhouse.getHeight(), greenhouse.getLength(), greenhouse.getWidth(),
                greenhouse.getImageURL(), greenhouse.getWaterFlow());
    }

    int updateGreenhouse(long id, Greenhouse greenhouse)  {
        final String updateQuery =
                "UPDATE " + GREENHOUSES_TABLE + " SET " + getUpdateSyntax(GREENHOUSE_NAME) + getUpdateSyntax(GREENHOUSE_LOCATION)
                        + getUpdateSyntax(GREENHOUSE_LOCATIONS_ID) +  getUpdateSyntax(GREENHOUSE_HEIGHT)
                        + getUpdateSyntax(GREENHOUSE_WATER_FLOW)
                        + getUpdateSyntax(GREENHOUSE_LENGTH) + getUpdateSyntax(GREENHOUSE_WIDTH)
                        + getUpdateSyntaxFinal(GREENHOUSE_IMAGE_URL) + " WHERE id = ?";
        return this.jdbcTemplate().update(updateQuery, greenhouse.getName(), greenhouse.getLocation(), greenhouse.getLocationId(),
                greenhouse.getHeight(), greenhouse.getWaterFlow(), greenhouse.getLength(), greenhouse.getWidth(), greenhouse.getImageURL(), id);
    }

    int deleteGreenhouse(long id) {
        final String deleteQuery =
                "UPDATE " + GREENHOUSES_TABLE + " SET " + getUpdateSyntaxFinal(IS_DISABLED) + " WHERE id = ?";
        return this.jdbcTemplate().update(deleteQuery, 1, id);
    }

    List<Greenhouse> searchGreenhouses(GreenhouseSearchDao searchDao) throws CustomException {
        return this.namedJdbcTemplate().query(
                searchDao.query(true), searchDao.namedParameterMap(), new GreenhouseMapper());
    }
}
