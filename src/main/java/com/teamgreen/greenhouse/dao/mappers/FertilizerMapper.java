package com.teamgreen.greenhouse.dao.mappers;

import com.teamgreen.greenhouse.dao.Fertilizer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.teamgreen.greenhouse.constants.Constants.MODIFIED_AT;
import static com.teamgreen.greenhouse.constants.Constants.IS_DISABLED;
import static com.teamgreen.greenhouse.constants.Constants.CREATED_AT;
import static com.teamgreen.greenhouse.fertilizers.Constants.FERTILIZER_ID;
import static com.teamgreen.greenhouse.fertilizers.Constants.FERTILIZER_PLANT_ID;
import static com.teamgreen.greenhouse.fertilizers.Constants.FERTILIZER_STAGE;
import static com.teamgreen.greenhouse.fertilizers.Constants.FERTILIZER_NAME;
import static com.teamgreen.greenhouse.fertilizers.Constants.FERTILIZER_DESCRIPTION;
import static com.teamgreen.greenhouse.fertilizers.Constants.FERTILIZER_MEDIUM;
import static com.teamgreen.greenhouse.fertilizers.Constants.FERTILIZER_QUANTITY;
import static com.teamgreen.greenhouse.fertilizers.Constants.FERTILIZER_FREQUENCY;

public class FertilizerMapper  implements RowMapper<Fertilizer> {

    @Override
    public Fertilizer mapRow(ResultSet rs, int i) throws SQLException {
        Fertilizer fertilizer = new Fertilizer();
        fertilizer.setId(rs.getLong(FERTILIZER_ID));
        fertilizer.setName(rs.getString(FERTILIZER_NAME));
        fertilizer.setDescription(rs.getString(FERTILIZER_DESCRIPTION));
        fertilizer.setPlantId(rs.getLong(FERTILIZER_PLANT_ID));
        fertilizer.setStage(rs.getInt(FERTILIZER_STAGE));
        fertilizer.setMedium(rs.getString(FERTILIZER_MEDIUM));
        fertilizer.setQuantity(rs.getDouble(FERTILIZER_QUANTITY));
        fertilizer.setFrequency(rs.getString(FERTILIZER_FREQUENCY));
        fertilizer.setDisabled(rs.getBoolean(IS_DISABLED));
        fertilizer.setCreatedAt(rs.getTimestamp(CREATED_AT));
        fertilizer.setModifiedAt(rs.getTimestamp(MODIFIED_AT));
        return fertilizer;
    }
}
