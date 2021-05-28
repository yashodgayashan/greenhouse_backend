package com.teamgreen.greenhouse.defects.reasons;

import com.teamgreen.greenhouse.dao.DiseaseReason;
import com.teamgreen.greenhouse.dao.mappers.DiseaseReasonMapper;
import com.teamgreen.greenhouse.exceptions.CustomException;
import com.teamgreen.greenhouse.store.DbHandler;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

import static com.teamgreen.greenhouse.constants.Constants.IS_DISABLED;
import static com.teamgreen.greenhouse.defects.Constants.*;
import static com.teamgreen.greenhouse.utils.MiscellaneousUtils.*;
import static com.teamgreen.greenhouse.utils.MiscellaneousUtils.getUpdateSyntaxFinal;

public class ReasonDbHandler extends DbHandler {

    public ReasonDbHandler(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedJdbcTemplate) {
        super(jdbcTemplate, namedJdbcTemplate);
    }

    List<DiseaseReason> getDiseaseReasons() {
        final String query = "SELECT * FROM " + DISEASE_REASONS_TABLE + " WHERE " + IS_DISABLED + " = 0 ORDER BY "
                + ID + " DESC";
        return this.jdbcTemplate().query(query, new DiseaseReasonMapper());
    }

    List<DiseaseReason> getDiseaseReasons(long diseaseId) {
        final String query = "SELECT * FROM " + DISEASE_REASONS_TABLE + " WHERE " + IS_DISABLED + " = 0 AND " + DISEASE_ID
                + " = ? ORDER BY " + ID + " DESC";
        return this.jdbcTemplate().query(query, new DiseaseReasonMapper(), diseaseId);
    }

    DiseaseReason getDiseaseReason(long id) throws CustomException {
        try {
            final String query = "SELECT * FROM " + DISEASE_REASONS_TABLE + " WHERE " + ID + " = ?";
            return this.jdbcTemplate().queryForObject(query, new DiseaseReasonMapper(), id);
        } catch (Exception e) {
            throw new CustomException("There is no disease reason found with id: " + id);
        }
    }

    DiseaseReason getDiseaseReason(long id, long diseaseId) throws CustomException {
        try {
            final String query = "SELECT * FROM " + DISEASE_REASONS_TABLE + " WHERE " + ID + " = ? AND "
                    + DISEASE_ID + " = ? ";
            return this.jdbcTemplate().queryForObject(query, new DiseaseReasonMapper(), id, diseaseId);
        } catch (Exception e) {
            throw new CustomException("There is no disease reason found with id: " + id);
        }
    }

    int addDiseaseReason(DiseaseReason diseaseReason)  {
        final String insertQuery =
                "INSERT INTO " + DISEASE_REASONS_TABLE + " ("  + withComma(NAME) + withComma(DESCRIPTION)
                        + encapFieldWithBackTick(DISEASE_ID) + ") VALUES "
                        + getStatementParams(3);

        return this.jdbcTemplate().update(insertQuery, diseaseReason.getName(), diseaseReason.getDescription(),
                diseaseReason.getDiseaseId());
    }

    int updateDiseaseReason(long id, DiseaseReason diseaseReason)  {
        final String updateQuery =
                "UPDATE " + DISEASE_REASONS_TABLE + " SET " + getUpdateSyntax(NAME) + getUpdateSyntax(DESCRIPTION)
                        + getUpdateSyntaxFinal(DISEASE_ID) + " WHERE id = ?";
        return this.jdbcTemplate().update(updateQuery, diseaseReason.getName(), diseaseReason.getDescription(),
                diseaseReason.getDiseaseId(), id);
    }

    int deleteDiseaseReason(long id) {
        final String deleteQuery =
                "UPDATE " + DISEASE_REASONS_TABLE + " SET " + getUpdateSyntaxFinal(IS_DISABLED) + " WHERE id = ?";
        return this.jdbcTemplate().update(deleteQuery, 1, id);
    }
}

