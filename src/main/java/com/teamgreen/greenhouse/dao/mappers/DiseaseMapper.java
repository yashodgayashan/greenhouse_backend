package com.teamgreen.greenhouse.dao.mappers;

import com.teamgreen.greenhouse.dao.Disease;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.teamgreen.greenhouse.constants.Constants.MODIFIED_AT;
import static com.teamgreen.greenhouse.constants.Constants.IS_DISABLED;
import static com.teamgreen.greenhouse.constants.Constants.CREATED_AT;
import static com.teamgreen.greenhouse.defects.Constants.ID;
import static com.teamgreen.greenhouse.defects.Constants.PLANT_ID;
import static com.teamgreen.greenhouse.defects.Constants.NAME;
import static com.teamgreen.greenhouse.defects.Constants.DESCRIPTION;
import static com.teamgreen.greenhouse.defects.Constants.LEVEL;

public class DiseaseMapper implements RowMapper<Disease> {

    @Override
    public Disease mapRow(ResultSet rs, int i) throws SQLException {
        Disease disease = new Disease();
        disease.setId(rs.getLong(ID));
        disease.setName(rs.getString(NAME));
        disease.setDescription(rs.getString(DESCRIPTION));
        disease.setLevel(rs.getString(LEVEL));
        disease.setPlantId(rs.getLong(PLANT_ID));
        disease.setDisabled(rs.getBoolean(IS_DISABLED));
        disease.setCreatedAt(rs.getTimestamp(CREATED_AT));
        disease.setModifiedAt(rs.getTimestamp(MODIFIED_AT));
        return disease;
    }
}
