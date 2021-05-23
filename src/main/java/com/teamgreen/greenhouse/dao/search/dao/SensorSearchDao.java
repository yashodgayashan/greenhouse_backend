package com.teamgreen.greenhouse.dao.search.dao;

import com.teamgreen.greenhouse.dao.SearchCondition;
import com.teamgreen.greenhouse.exceptions.CustomException;

import java.util.HashMap;
import java.util.Map;

import static com.teamgreen.greenhouse.dao.search.dao.SearchDaoUtils.*;
import static com.teamgreen.greenhouse.sensors.Constants.*;
import static com.teamgreen.greenhouse.utils.Util.isNotEmpty;

public class SensorSearchDao implements SearchDao {

    public static final String SENSOR_NAME_QUERY_NAME = "name";
    public static final String SENSOR_DESCRIPTION_QUERY_NAME = "description";

    private SearchCondition name;
    private SearchCondition description;
    private int size;
    private int from;
    private Map<String, Object> map = new HashMap<>();

    public String query(boolean applySizeFilters) throws CustomException {
        String query = "SELECT * FROM " + SENSORS_TABLE + " WHERE ";

        if (name != null && isNotEmpty(name.getCondition()) && isNotEmpty(name.getValue())) {
            query += addStringFilter(name, SENSOR_NAME, SENSOR_NAME_QUERY_NAME, map);
        }

        if (description != null && isNotEmpty(description.getCondition()) && isNotEmpty(description.getValue())) {
            query += addStringFilter(description, SENSOR_DESCRIPTION, SENSOR_DESCRIPTION_QUERY_NAME, map);
        }

        return addPostQueryClause(query, this, applySizeFilters);
    }

    public Map<String, Object> namedParameterMap() {
        return map;
    }

    public SearchCondition getName() { return name; }

    public void setName(SearchCondition name) { this.name = name; }

    public SearchCondition getDescription() { return description; }

    public void setDescription(SearchCondition description) { this.description = description; }

    @Override
    public int getSize() { return size; }

    @Override
    public void setSize(int size) { this.size = size; }

    @Override
    public int getFrom() { return from; }

    @Override
    public void setFrom(int from) { this.from = from; }
}
