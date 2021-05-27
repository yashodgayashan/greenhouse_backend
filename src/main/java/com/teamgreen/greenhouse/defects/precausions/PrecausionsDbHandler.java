package com.teamgreen.greenhouse.defects.precausions;

import com.teamgreen.greenhouse.dao.DiseasePrecausions;
import com.teamgreen.greenhouse.dao.DiseaseReason;
import com.teamgreen.greenhouse.dao.mappers.DiseasePrecausionsMapper;
import com.teamgreen.greenhouse.dao.mappers.DiseaseReasonMapper;
import com.teamgreen.greenhouse.exceptions.CustomException;
import com.teamgreen.greenhouse.store.DbHandler;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

import static com.teamgreen.greenhouse.constants.Constants.IS_DISABLED;
import static com.teamgreen.greenhouse.defects.Constants.*;
import static com.teamgreen.greenhouse.defects.Constants.DISEASE_REASONS_TABLE;
import static com.teamgreen.greenhouse.utils.MiscellaneousUtils.*;
import static com.teamgreen.greenhouse.utils.MiscellaneousUtils.getUpdateSyntaxFinal;

public class PrecausionsDbHandler extends DbHandler {

    public PrecausionsDbHandler(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedJdbcTemplate) {
        super(jdbcTemplate, namedJdbcTemplate);
    }

    List<DiseasePrecausions> getDiseasePrecausions() {
        final String query = "SELECT * FROM " + DISEASE_PRECAUSIONS_TABLE + " WHERE " + IS_DISABLED + " = 0 ORDER BY "
                + ID + " DESC";
        return this.jdbcTemplate().query(query, new DiseasePrecausionsMapper());
    }

    List<DiseasePrecausions> getDiseasePrecausions(long diseaseId) {
        final String query = "SELECT * FROM " + DISEASE_PRECAUSIONS_TABLE + " WHERE " + IS_DISABLED + " = 0 AND " + DISEASE_ID
                + " = ? ORDER BY " + ID + " DESC";
        return this.jdbcTemplate().query(query, new DiseasePrecausionsMapper(), diseaseId);
    }

    DiseasePrecausions getDiseasePrecausion(long id) throws CustomException {
        try {
            final String query = "SELECT * FROM " + DISEASE_PRECAUSIONS_TABLE + " WHERE " + ID + " = ?";
            return this.jdbcTemplate().queryForObject(query, new DiseasePrecausionsMapper(), id);
        } catch (Exception e) {
            throw new CustomException("There is no disease precausions found with id: " + id);
        }
    }

    DiseasePrecausions getDiseasePrecausion(long id, long diseaseId) throws CustomException {
        try {
            final String query = "SELECT * FROM " + DISEASE_PRECAUSIONS_TABLE + " WHERE " + ID + " = ? AND "
                    + DISEASE_ID + " = ? ";
            return this.jdbcTemplate().queryForObject(query, new DiseasePrecausionsMapper(), id, diseaseId);
        } catch (Exception e) {
            throw new CustomException("There is no disease precausions found with id: " + id);
        }
    }

    int addDiseasePrecausion(DiseasePrecausions diseasePrecausions)  {
        final String insertQuery =
                "INSERT INTO " + DISEASE_PRECAUSIONS_TABLE + " ("  + withComma(NAME) + withComma(DESCRIPTION)
                        + encapFieldWithBackTick(DISEASE_ID) + ") VALUES "
                        + getStatementParams(3);

        return this.jdbcTemplate().update(insertQuery, diseasePrecausions.getName(), diseasePrecausions.getDescription(),
                diseasePrecausions.getDiseaseId());
    }

    int updateDiseasePrecausion(long id, DiseasePrecausions diseasePrecausions)  {
        final String updateQuery =
                "UPDATE " + DISEASE_PRECAUSIONS_TABLE + " SET " + getUpdateSyntax(NAME) + getUpdateSyntax(DESCRIPTION)
                        + getUpdateSyntaxFinal(DISEASE_ID) + " WHERE id = ?";
        return this.jdbcTemplate().update(updateQuery, diseasePrecausions.getName(), diseasePrecausions.getDescription(),
                diseasePrecausions.getDiseaseId(), id);
    }

    int deleteDiseasePrecausion(long id) {
        final String deleteQuery =
                "UPDATE " + DISEASE_PRECAUSIONS_TABLE + " SET " + getUpdateSyntaxFinal(IS_DISABLED) + " WHERE id = ?";
        return this.jdbcTemplate().update(deleteQuery, 1, id);
    }
}
