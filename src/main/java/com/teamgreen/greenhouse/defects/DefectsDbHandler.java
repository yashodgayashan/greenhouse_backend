package com.teamgreen.greenhouse.defects;

import com.teamgreen.greenhouse.dao.Disease;
import com.teamgreen.greenhouse.dao.mappers.DiseaseMapper;
import com.teamgreen.greenhouse.dao.search.dao.DiseaseSearchDao;
import com.teamgreen.greenhouse.exceptions.CustomException;
import com.teamgreen.greenhouse.store.DbHandler;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

import static com.teamgreen.greenhouse.constants.Constants.IS_DISABLED;
import static com.teamgreen.greenhouse.defects.Constants.NAME;
import static com.teamgreen.greenhouse.defects.Constants.DISEASE_TABLE;
import static com.teamgreen.greenhouse.defects.Constants.ID;
import static com.teamgreen.greenhouse.defects.Constants.DESCRIPTION;
import static com.teamgreen.greenhouse.defects.Constants.LEVEL;
import static com.teamgreen.greenhouse.defects.Constants.PLANT_ID;
import static com.teamgreen.greenhouse.utils.MiscellaneousUtils.withComma;
import static com.teamgreen.greenhouse.utils.MiscellaneousUtils.getStatementParams;
import static com.teamgreen.greenhouse.utils.MiscellaneousUtils.getUpdateSyntax;
import static com.teamgreen.greenhouse.utils.MiscellaneousUtils.encapFieldWithBackTick;
import static com.teamgreen.greenhouse.utils.MiscellaneousUtils.getUpdateSyntaxFinal;

public class DefectsDbHandler extends DbHandler {

    public DefectsDbHandler(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedJdbcTemplate) {
        super(jdbcTemplate, namedJdbcTemplate);
    }

    List<Disease> getDiseases() {
        final String query = "SELECT * FROM " + DISEASE_TABLE + " WHERE " + IS_DISABLED + " = 0 ORDER BY "
                + ID + " DESC";
        return this.jdbcTemplate().query(query, new DiseaseMapper());
    }

    Disease getDisease(long id) throws CustomException {
        try {
            final String query = "SELECT * FROM " + DISEASE_TABLE + " WHERE " + ID + " = ?";
            return this.jdbcTemplate().queryForObject(query, new DiseaseMapper(), id);
        } catch (Exception e) {
            throw new CustomException("There is no disease found with id: " + id);
        }
    }

    int addDisease(Disease disease)  {
        final String insertQuery =
                "INSERT INTO " + DISEASE_TABLE + " ("  + withComma(NAME) + withComma(DESCRIPTION)
                        + withComma(LEVEL) + encapFieldWithBackTick(PLANT_ID) + ") VALUES "
                        + getStatementParams(4);

        return this.jdbcTemplate().update(insertQuery, disease.getName(), disease.getDescription(),
                disease.getLevel(), disease.getPlantId());
    }

    int updateDisease(long id, Disease disease)  {
        final String updateQuery =
                "UPDATE " + DISEASE_TABLE + " SET " + getUpdateSyntax(NAME) + getUpdateSyntax(DESCRIPTION)
                        + getUpdateSyntax(LEVEL) + getUpdateSyntaxFinal(PLANT_ID) + " WHERE id = ?";
        return this.jdbcTemplate().update(updateQuery, disease.getName(), disease.getDescription(),
                disease.getLevel(), disease.getPlantId(), id);
    }

    int deleteDisease(long id) {
        final String deleteQuery =
                "UPDATE " + DISEASE_TABLE + " SET " + getUpdateSyntaxFinal(IS_DISABLED) + " WHERE id = ?";
        return this.jdbcTemplate().update(deleteQuery, 1, id);
    }

    List<Disease> searchDiseases(DiseaseSearchDao searchDao) throws CustomException {
        return this.namedJdbcTemplate().query(
                searchDao.query(true), searchDao.namedParameterMap(), new DiseaseMapper());
    }
}
