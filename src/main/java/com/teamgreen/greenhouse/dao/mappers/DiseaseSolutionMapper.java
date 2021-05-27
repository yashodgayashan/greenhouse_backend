package com.teamgreen.greenhouse.dao.mappers;

import com.teamgreen.greenhouse.dao.DiseaseReason;
import com.teamgreen.greenhouse.dao.DiseaseSolution;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.teamgreen.greenhouse.constants.Constants.MODIFIED_AT;
import static com.teamgreen.greenhouse.constants.Constants.IS_DISABLED;
import static com.teamgreen.greenhouse.constants.Constants.CREATED_AT;
import static com.teamgreen.greenhouse.defects.Constants.ID;
import static com.teamgreen.greenhouse.defects.Constants.DISEASE_ID;
import static com.teamgreen.greenhouse.defects.Constants.NAME;
import static com.teamgreen.greenhouse.defects.Constants.DESCRIPTION;

public class DiseaseSolutionMapper implements RowMapper<DiseaseSolution> {

    @Override
    public DiseaseSolution mapRow(ResultSet rs, int i) throws SQLException {
        DiseaseSolution solution = new DiseaseSolution();
        solution.setId(rs.getLong(ID));
        solution.setName(rs.getString(NAME));
        solution.setDescription(rs.getString(DESCRIPTION));
        solution.setDiseaseId(rs.getLong(DISEASE_ID));
        solution.setDisabled(rs.getBoolean(IS_DISABLED));
        solution.setCreatedAt(rs.getTimestamp(CREATED_AT));
        solution.setModifiedAt(rs.getTimestamp(MODIFIED_AT));
        return solution;
    }
}
