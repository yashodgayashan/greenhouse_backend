package com.teamgreen.greenhouse.dao.mappers;

import com.teamgreen.greenhouse.dao.DiseasePrecausions;
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

public class DiseasePrecausionsMapper implements RowMapper<DiseasePrecausions> {

    @Override
    public DiseasePrecausions mapRow(ResultSet rs, int i) throws SQLException {
        DiseasePrecausions precausions = new DiseasePrecausions();
        precausions.setId(rs.getLong(ID));
        precausions.setName(rs.getString(NAME));
        precausions.setDescription(rs.getString(DESCRIPTION));
        precausions.setDiseaseId(rs.getLong(DISEASE_ID));
        precausions.setDisabled(rs.getBoolean(IS_DISABLED));
        precausions.setCreatedAt(rs.getTimestamp(CREATED_AT));
        precausions.setModifiedAt(rs.getTimestamp(MODIFIED_AT));
        return precausions;
    }
}
