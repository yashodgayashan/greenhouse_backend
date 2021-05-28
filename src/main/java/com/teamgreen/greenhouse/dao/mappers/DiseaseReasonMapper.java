package com.teamgreen.greenhouse.dao.mappers;

import com.teamgreen.greenhouse.dao.DiseaseReason;
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

public class DiseaseReasonMapper implements RowMapper<DiseaseReason> {

    @Override
    public DiseaseReason mapRow(ResultSet rs, int i) throws SQLException {
        DiseaseReason diseaseReason = new DiseaseReason();
        diseaseReason.setId(rs.getLong(ID));
        diseaseReason.setName(rs.getString(NAME));
        diseaseReason.setDescription(rs.getString(DESCRIPTION));
        diseaseReason.setDiseaseId(rs.getLong(DISEASE_ID));
        diseaseReason.setDisabled(rs.getBoolean(IS_DISABLED));
        diseaseReason.setCreatedAt(rs.getTimestamp(CREATED_AT));
        diseaseReason.setModifiedAt(rs.getTimestamp(MODIFIED_AT));
        return diseaseReason;
    }
}