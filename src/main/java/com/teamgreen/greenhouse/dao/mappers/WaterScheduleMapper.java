package com.teamgreen.greenhouse.dao.mappers;

import com.teamgreen.greenhouse.dao.WaterSchedule;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.teamgreen.greenhouse.constants.Constants.*;
import static com.teamgreen.greenhouse.greenhousewaterschedule.Constants.*;

public class WaterScheduleMapper implements RowMapper<WaterSchedule> {

    @Override
    public WaterSchedule mapRow(ResultSet rs, int i) throws SQLException {
        WaterSchedule schedule = new WaterSchedule();
        schedule.setId(rs.getLong(SCHEDULE_ID));
        schedule.setGreenhouseId(rs.getLong(SCHEDULE_GREENHOUSE_ID));
        schedule.setStartDate(rs.getDate(SCHEDULE_START_DATE));
        schedule.setEndDate(rs.getDate(SCHEDULE_END_DATE));
        schedule.setWateringTime(rs.getTimestamp(SCHEDULE_WATERING_TIME));
        schedule.setEnabled(rs.getBoolean(SCHEDULE_IS_ENABLED));
        schedule.setDeleted(rs.getBoolean(SCHEDULE_IS_DELETED));
        schedule.setModifiedAt(rs.getTimestamp(SCHEDULE_MODIFIED_AT));
        schedule.setCreatedAt(rs.getTimestamp(SCHEDULE_CREATED_AT));
        return schedule;
    }
}
