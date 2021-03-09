package com.teamgreen.greenhouse.dao.search.dao;

import com.teamgreen.greenhouse.dao.SearchCondition;
import com.teamgreen.greenhouse.exceptions.CustomException;

import java.util.HashMap;
import java.util.Map;

import static com.teamgreen.greenhouse.dao.search.dao.SearchDaoUtils.addPostQueryClause;
import static com.teamgreen.greenhouse.locations.Constants.*;
import static com.teamgreen.greenhouse.utils.Util.isNotEmpty;
import static com.teamgreen.greenhouse.dao.search.dao.SearchDaoUtils.addStringFilter;

public class LocationSearchDao  implements SearchDao{

    private static final String LOCATIONS_NAME = "name";
    private static final String LOCATIONS_LOCATION = "location";

    private SearchCondition name;
    private SearchCondition location;
    private int size;
    private int from;

    private Map<String, Object> map = new HashMap<>();

    public String query(boolean applySizeFilters) throws CustomException {
        String query = "SELECT * FROM " + LOCATIONS_TABLE + " WHERE ";

        if (name != null && isNotEmpty(name.getCondition()) && isNotEmpty(name.getValue())) {
            query += addStringFilter(name, LOCATION_NAME, LOCATIONS_NAME, map);
        }

        if (location != null && isNotEmpty(location.getCondition()) && isNotEmpty(location.getValue())) {
            query += addStringFilter(location, LOCATION_LOCATION, LOCATIONS_LOCATION, map);
        }

        return addPostQueryClause(query, this, applySizeFilters);
    }

    public Map<String, Object> namedParameterMap() {
        return map;
    }

    public SearchCondition getName() { return name; }

    public void setName(SearchCondition name) { this.name = name; }

    public SearchCondition getLocation() { return location; }

    public void setLocation(SearchCondition location) { this.location = location; }

    @Override
    public int getSize() { return size; }

    @Override
    public void setSize(int size) { this.size = size; }

    @Override
    public int getFrom() { return from; }

    @Override
    public void setFrom(int from) { this.from = from; }
}
