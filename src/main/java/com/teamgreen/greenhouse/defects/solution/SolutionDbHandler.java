package com.teamgreen.greenhouse.defects.solution;

import com.teamgreen.greenhouse.dao.DiseaseSolution;
import com.teamgreen.greenhouse.dao.mappers.DiseaseSolutionMapper;
import com.teamgreen.greenhouse.exceptions.CustomException;
import com.teamgreen.greenhouse.store.DbHandler;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

import static com.teamgreen.greenhouse.constants.Constants.IS_DISABLED;
import static com.teamgreen.greenhouse.defects.Constants.*;
import static com.teamgreen.greenhouse.utils.MiscellaneousUtils.*;
import static com.teamgreen.greenhouse.utils.MiscellaneousUtils.getUpdateSyntaxFinal;

public class SolutionDbHandler  extends DbHandler {

    public SolutionDbHandler(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedJdbcTemplate) {
        super(jdbcTemplate, namedJdbcTemplate);
    }

    List<DiseaseSolution> getDiseaseSolutions() {
        final String query = "SELECT * FROM " + DISEASE_MEDICINES_TABLE + " WHERE " + IS_DISABLED + " = 0 ORDER BY "
                + ID + " DESC";
        return this.jdbcTemplate().query(query, new DiseaseSolutionMapper());
    }

    List<DiseaseSolution> getDiseaseSolutions(long diseaseId) {
        final String query = "SELECT * FROM " + DISEASE_MEDICINES_TABLE + " WHERE " + IS_DISABLED + " = 0 AND " + DISEASE_ID
                + " = ? ORDER BY " + ID + " DESC";
        return this.jdbcTemplate().query(query, new DiseaseSolutionMapper(), diseaseId);
    }

    DiseaseSolution getDiseaseSolution(long id) throws CustomException {
        try {
            final String query = "SELECT * FROM " + DISEASE_MEDICINES_TABLE + " WHERE " + ID + " = ?";
            return this.jdbcTemplate().queryForObject(query, new DiseaseSolutionMapper(), id);
        } catch (Exception e) {
            throw new CustomException("There is no disease solution found with id: " + id);
        }
    }

    DiseaseSolution getDiseaseSolution(long id, long diseaseId) throws CustomException {
        try {
            final String query = "SELECT * FROM " + DISEASE_MEDICINES_TABLE + " WHERE " + ID + " = ? AND "
                    + DISEASE_ID + " = ? ";
            return this.jdbcTemplate().queryForObject(query, new DiseaseSolutionMapper(), id, diseaseId);
        } catch (Exception e) {
            throw new CustomException("There is no disease solution found with id: " + id);
        }
    }

    int addDiseaseSolution(DiseaseSolution diseaseSolution)  {
        final String insertQuery =
                "INSERT INTO " + DISEASE_MEDICINES_TABLE + " ("  + withComma(NAME) + withComma(DESCRIPTION)
                        + encapFieldWithBackTick(DISEASE_ID) + ") VALUES "
                        + getStatementParams(3);

        return this.jdbcTemplate().update(insertQuery, diseaseSolution.getName(), diseaseSolution.getDescription(),
                diseaseSolution.getDiseaseId());
    }

    int updateDiseaseSolution(long id, DiseaseSolution diseaseSolution)  {
        final String updateQuery =
                "UPDATE " + DISEASE_MEDICINES_TABLE + " SET " + getUpdateSyntax(NAME) + getUpdateSyntax(DESCRIPTION)
                        + getUpdateSyntaxFinal(DISEASE_ID) + " WHERE id = ?";
        return this.jdbcTemplate().update(updateQuery, diseaseSolution.getName(), diseaseSolution.getDescription(),
                diseaseSolution.getDiseaseId(), id);
    }

    int deleteDiseaseSolution(long id) {
        final String deleteQuery =
                "UPDATE " + DISEASE_MEDICINES_TABLE + " SET " + getUpdateSyntaxFinal(IS_DISABLED) + " WHERE id = ?";
        return this.jdbcTemplate().update(deleteQuery, 1, id);
    }
}
