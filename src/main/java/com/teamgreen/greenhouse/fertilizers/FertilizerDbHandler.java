package com.teamgreen.greenhouse.fertilizers;

import com.teamgreen.greenhouse.dao.Fertilizer;
import com.teamgreen.greenhouse.dao.mappers.FertilizerMapper;
import com.teamgreen.greenhouse.dao.search.dao.FertilizerSearchDao;
import com.teamgreen.greenhouse.exceptions.CustomException;
import com.teamgreen.greenhouse.store.DbHandler;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

import static com.teamgreen.greenhouse.constants.Constants.IS_DISABLED;
import static com.teamgreen.greenhouse.fertilizers.Constants.FERTILIZER_ID;
import static com.teamgreen.greenhouse.fertilizers.Constants.FERTILIZER_PLANT_ID;
import static com.teamgreen.greenhouse.fertilizers.Constants.FERTILIZER_STAGE;
import static com.teamgreen.greenhouse.fertilizers.Constants.FERTILIZER_NAME;
import static com.teamgreen.greenhouse.fertilizers.Constants.FERTILIZER_DESCRIPTION;
import static com.teamgreen.greenhouse.fertilizers.Constants.FERTILIZER_MEDIUM;
import static com.teamgreen.greenhouse.fertilizers.Constants.FERTILIZER_QUANTITY;
import static com.teamgreen.greenhouse.fertilizers.Constants.FERTILIZER_FREQUENCY;
import static com.teamgreen.greenhouse.fertilizers.Constants.FERTILIZER_TABLE;
import static com.teamgreen.greenhouse.greenhouses.Constants.*;
import static com.teamgreen.greenhouse.utils.MiscellaneousUtils.*;
import static com.teamgreen.greenhouse.utils.MiscellaneousUtils.getUpdateSyntaxFinal;

public class FertilizerDbHandler  extends DbHandler {

    public FertilizerDbHandler(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }

    List<Fertilizer> getFertilizers() {
        final String query = "SELECT * FROM " + FERTILIZER_TABLE + " WHERE " + IS_DISABLED + " = 0 ORDER BY "
                + GREENHOUSE_ID + " DESC";
        return this.jdbcTemplate().query(query, new FertilizerMapper());
    }

    Fertilizer getFertilizer(long id) throws CustomException {
        try {
            final String query = "SELECT * FROM " + FERTILIZER_TABLE + " WHERE " + FERTILIZER_ID + " = ? AND "
                    + IS_DISABLED + " = 0";
            return this.jdbcTemplate().queryForObject(query, new FertilizerMapper(), id);
        } catch (Exception e) {
            throw new CustomException("There is no fertilizer found with id: " + id);
        }
    }

    int addFertilizer(Fertilizer fertilizer)  {
        final String insertQuery =
                "INSERT INTO " + FERTILIZER_TABLE + " ("  + withComma(FERTILIZER_NAME) + withComma(FERTILIZER_DESCRIPTION)
                        + withComma(FERTILIZER_PLANT_ID) + withComma(FERTILIZER_STAGE) + withComma(FERTILIZER_MEDIUM)
                        + withComma(FERTILIZER_QUANTITY) + encapFieldWithBackTick(FERTILIZER_FREQUENCY) + ") VALUES "
                        + getStatementParams(7);

        return this.jdbcTemplate().update(insertQuery, fertilizer.getName(), fertilizer.getDescription(),
                fertilizer.getPlantId(), fertilizer.getStage(), fertilizer.getMedium(), fertilizer.getQuantity(),
                fertilizer.getFrequency());
    }

    int updateFertilizer(long id, Fertilizer fertilizer)  {
        final String updateQuery =
                "UPDATE " + FERTILIZER_TABLE + " SET " + getUpdateSyntax(FERTILIZER_NAME) + getUpdateSyntax(FERTILIZER_DESCRIPTION)
                        + getUpdateSyntax(FERTILIZER_PLANT_ID) +  getUpdateSyntax(FERTILIZER_STAGE)
                        + getUpdateSyntax(FERTILIZER_MEDIUM) + getUpdateSyntax(FERTILIZER_QUANTITY)
                        + getUpdateSyntaxFinal(FERTILIZER_FREQUENCY) + " WHERE id = ?";
        return this.jdbcTemplate().update(updateQuery, fertilizer.getName(), fertilizer.getDescription(),
                fertilizer.getPlantId(), fertilizer.getStage(), fertilizer.getMedium(), fertilizer.getQuantity(),
                fertilizer.getFrequency(), id);
    }

    int deleteFertilizer(long id) {
        final String deleteQuery =
                "UPDATE " + FERTILIZER_TABLE + " SET " + getUpdateSyntaxFinal(IS_DISABLED) + " WHERE id = ?";
        return this.jdbcTemplate().update(deleteQuery, 1, id);
    }

    List<Fertilizer> searchFertilizer(FertilizerSearchDao searchDao) throws CustomException {
        return this.namedJdbcTemplate().query(
                searchDao.query(true), searchDao.namedParameterMap(), new FertilizerMapper());
    }
}
