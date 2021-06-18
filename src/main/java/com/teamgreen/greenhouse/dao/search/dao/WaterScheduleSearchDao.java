package com.teamgreen.greenhouse.dao.search.dao;

import com.teamgreen.greenhouse.dao.SearchCondition;
import com.teamgreen.greenhouse.exceptions.CustomException;

import java.util.HashMap;
import java.util.Map;

import static com.teamgreen.greenhouse.dao.search.dao.SearchDaoUtils.*;
import static com.teamgreen.greenhouse.greenhousewaterschedule.Constants.*;
import static com.teamgreen.greenhouse.utils.Util.isNotEmpty;
import static com.teamgreen.greenhouse.utils.Util.isValidInteger;

public class WaterScheduleSearchDao implements SearchDao{

    public static final String WATER_SCHEDULE_GREENHOUSE_ID_QUERY_NAME = "name";
    public static final String WATER_SCHEDULE_START_DATE_QUERY_NAME = "name";
    public static final String WATER_SCHEDULE_END_DATE_QUERY_NAME = "name";
    public static final String WATER_SCHEDULE_IS_ENABLED_QUERY_NAME = "name";

    private SearchCondition greenhouseId;
    private SearchCondition startDate;
    private SearchCondition endDate;
    private SearchCondition isEnabled;
    private int size;
    private int from;
    private Map<String, Object> map = new HashMap<>();

    public String query(boolean applySizeFilters) throws CustomException {
        String query = "SELECT * FROM " + WATER_SCHEDULE_TABLE + " WHERE ";

        if (greenhouseId != null && isNotEmpty(greenhouseId.getCondition()) && isValidInteger(greenhouseId.getValue())) {
            query += addStringFilter(greenhouseId, SCHEDULE_GREENHOUSE_ID, WATER_SCHEDULE_GREENHOUSE_ID_QUERY_NAME, map);
        }

        if (startDate != null && isNotEmpty(startDate.getCondition()) && isNotEmpty(startDate.getValue())) {
            query += addStringFilter(startDate, SCHEDULE_START_DATE, WATER_SCHEDULE_START_DATE_QUERY_NAME, map);
        }

        if (endDate != null && isNotEmpty(endDate.getCondition()) && isNotEmpty(endDate.getValue())) {
            query += addStringFilter(endDate, SCHEDULE_END_DATE, WATER_SCHEDULE_END_DATE_QUERY_NAME, map);
        }

        if (isEnabled != null && isNotEmpty(isEnabled.getCondition()) && isNotEmpty(isEnabled.getValue())) {
            query += addStringFilter(isEnabled, SCHEDULE_IS_ENABLED, WATER_SCHEDULE_IS_ENABLED_QUERY_NAME, map);
        }

        return addPostQueryClause(query, this, applySizeFilters);
    }

    public Map<String, Object> namedParameterMap() {
        return map;
    }

    public SearchCondition getGreenhouseId() { return greenhouseId; }

    public void setGreenhouseId(SearchCondition greenhouseId) { this.greenhouseId = greenhouseId; }

    public SearchCondition getStartDate() { return startDate; }

    public void setStartDate(SearchCondition startDate) { this.startDate = startDate; }

    public SearchCondition getEndDate() { return endDate; }

    public void setEndDate(SearchCondition endDate) { this.endDate = endDate; }

    public SearchCondition getIsEnabled() { return isEnabled; }

    public void setIsEnabled(SearchCondition isEnabled) { this.isEnabled = isEnabled; }

    @Override
    public int getSize() { return size; }

    @Override
    public void setSize(int size) { this.size = size; }

    @Override
    public int getFrom() { return from; }

    @Override
    public void setFrom(int from) { this.from = from; }
}
