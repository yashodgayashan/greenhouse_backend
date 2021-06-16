package com.teamgreen.greenhouse.greenhousewaterschedule;

import com.teamgreen.greenhouse.dao.WaterSchedule;
import com.teamgreen.greenhouse.dao.mappers.WaterScheduleMapper;
import com.teamgreen.greenhouse.dao.search.dao.WaterScheduleSearchDao;
import com.teamgreen.greenhouse.exceptions.CustomException;
import com.teamgreen.greenhouse.store.DbHandler;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

import static com.teamgreen.greenhouse.greenhousewaterschedule.Constants.*;
import static com.teamgreen.greenhouse.utils.MiscellaneousUtils.*;

public class GreenhouseWaterFlowDbHandler extends DbHandler {

    public GreenhouseWaterFlowDbHandler(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate
            namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }

    List<WaterSchedule> getWaterSchedules() {
        final String query = "SELECT * FROM " + WATER_SCHEDULE_TABLE + " WHERE "
                + SCHEDULE_IS_DELETED + " = 0 ORDER BY " + SCHEDULE_ID + " DESC";
        return this.jdbcTemplate().query(query, new WaterScheduleMapper());
    }

    WaterSchedule getWaterSchedule(long id) throws CustomException {
        try {
            final String query = "SELECT * FROM " + WATER_SCHEDULE_TABLE + " WHERE "
                    + SCHEDULE_ID + " = ? AND " + SCHEDULE_IS_DELETED + " = 0";
            return this.jdbcTemplate().queryForObject(query, new WaterScheduleMapper(), id);
        } catch(Exception e) {
            throw new CustomException("There is no schedule found with id: " + id);
        }
    }

    int addSchedule(WaterSchedule waterSchedule) {
        final String insertQuery =
                "INSERT INTO " + WATER_SCHEDULE_TABLE + "(" + withComma(SCHEDULE_GREENHOUSE_ID)
                        + withComma(SCHEDULE_START_DATE) + withComma(SCHEDULE_END_DATE)
                        + withComma(SCHEDULE_WATERING_TIME) + withComma(SCHEDULE_IS_ENABLED)
                        + encapFieldWithBackTick(SCHEDULE_IS_DELETED) + ") VALUES "
                        + getStatementParams(6);

        return this.jdbcTemplate().update(insertQuery, waterSchedule.getGreenhouseId(),
                waterSchedule.getStartDate(), waterSchedule.getEndDate(), waterSchedule.getWateringTime(),
                waterSchedule.isEnabled(), waterSchedule.isDeleted());
    }

    int updateSchedule(long id, WaterSchedule waterSchedule) {
        final String updateQuery =
                "UPDATE " + WATER_SCHEDULE_TABLE + " SET " + getUpdateSyntax(SCHEDULE_GREENHOUSE_ID)
                        + getUpdateSyntax(SCHEDULE_START_DATE) + getUpdateSyntax(SCHEDULE_END_DATE)
                        + getUpdateSyntax(SCHEDULE_WATERING_TIME) + getUpdateSyntaxFinal(SCHEDULE_IS_ENABLED)
                        + " WHERE id = ?";

        return this.jdbcTemplate().update(updateQuery, waterSchedule.getGreenhouseId(),
                waterSchedule.getStartDate(), waterSchedule.getEndDate(), waterSchedule.getWateringTime(),
                waterSchedule.isEnabled(), id);
    }

    int deleteSchedule(long id) {
        final String deleteQuery = "UPDATE " + WATER_SCHEDULE_TABLE + " SET "
                + getUpdateSyntaxFinal(SCHEDULE_IS_DELETED) + " WHERE id = ?";
        return this.jdbcTemplate().update(deleteQuery, 1, id);
    }

    List<WaterSchedule> searchSchedules(WaterScheduleSearchDao searchDao) throws CustomException {
        return this.namedJdbcTemplate().query(
                searchDao.query(true), searchDao.namedParameterMap(), new WaterScheduleMapper()
        );
    }

    List<WaterSchedule> getSchedulesByGreenhouse(long id) {
        final String query = "SELECT * FROM " + WATER_SCHEDULE_TABLE + " WHERE " + SCHEDULE_GREENHOUSE_ID
                + " = ? AND " + SCHEDULE_IS_DELETED + " = 0 ORDER BY " + SCHEDULE_IS_ENABLED + " DESC";
        return this.jdbcTemplate().query(query, new WaterScheduleMapper(), id);
    }

    int changeStateOfASchedule(long id, boolean state) {
        if (state == true) {
            final String enableQuery = "UPDATE " + WATER_SCHEDULE_TABLE + " SET "
                    + getUpdateSyntaxFinal(SCHEDULE_IS_ENABLED) + " WHERE id = ?";
            return this.jdbcTemplate().update(enableQuery, 1, id);
        } else {
            final String enableQuery = "UPDATE " + WATER_SCHEDULE_TABLE + " SET "
                    + getUpdateSyntaxFinal(SCHEDULE_IS_ENABLED) + " WHERE id = ?";
            return this.jdbcTemplate().update(enableQuery, 0, id);
        }
    }
}
