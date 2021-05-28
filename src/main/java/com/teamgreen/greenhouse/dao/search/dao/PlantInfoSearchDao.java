package com.teamgreen.greenhouse.dao.search.dao;

import com.teamgreen.greenhouse.dao.SearchCondition;
import com.teamgreen.greenhouse.exceptions.CustomException;

import java.util.HashMap;
import java.util.Map;

import static com.teamgreen.greenhouse.dao.search.dao.SearchDaoUtils.*;
import static com.teamgreen.greenhouse.plant.Constants.*;
import static com.teamgreen.greenhouse.utils.Util.isNotEmpty;

public class PlantInfoSearchDao implements SearchDao  {

    public static final String PLANT_INFO_NAME_QUERY_NAME = "name";
    public static final String PLANT_INFO_DURATION_QUERY_NAME = "plant_duration";

    private SearchCondition name;
    private SearchCondition duration;
    private int size;
    private int from;
    private Map<String, Object> map = new HashMap<>();

    public String query(boolean applySizeFilters) throws CustomException {
        String query = "SELECT * FROM " + PLANT_INFO_TABLE + " WHERE ";

        if (name != null && isNotEmpty(name.getCondition()) && isNotEmpty(name.getValue())) {
            query += addStringFilter(name, PLANT_INFO_NAME, PLANT_INFO_NAME_QUERY_NAME, map);
        }

        if (duration != null && isNotEmpty(duration.getCondition()) && isNotEmpty(duration.getValue())) {
            query += addStringFilter(duration, PLANT_INFO_DURATION, PLANT_INFO_DURATION_QUERY_NAME, map);
        }

        return addPostQueryClause(query, this, applySizeFilters);
    }

    public Map<String, Object> namedParameterMap() {
        return map;
    }

    public SearchCondition getName() { return name; }

    public void setName(SearchCondition name) { this.name = name; }

    public SearchCondition getDuration() { return duration; }

    public void setDuration(SearchCondition duration) { this.duration = duration; }

    @Override
    public int getSize() { return size; }

    @Override
    public void setSize(int size) { this.size = size; }

    @Override
    public int getFrom() { return from; }

    @Override
    public void setFrom(int from) { this.from = from; }
}
