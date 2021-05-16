package com.teamgreen.greenhouse.dao.search.dao;

import com.teamgreen.greenhouse.dao.SearchCondition;
import com.teamgreen.greenhouse.exceptions.CustomException;

import java.util.HashMap;
import java.util.Map;

import static com.teamgreen.greenhouse.dao.search.dao.SearchDaoUtils.*;
import static com.teamgreen.greenhouse.greenhouses.Constants.*;
import static com.teamgreen.greenhouse.utils.Util.isNotEmpty;
import static com.teamgreen.greenhouse.utils.Util.isValidInteger;

public class GreenhouseSearchDao implements SearchDao{

    public static final String GREENHOUSE_LOCATIONS_ID_QUERY_NAME = "locationId";
    public static final String GREENHOUSE_NAME_QUERY_NAME = "name";
    public static final String GREENHOUSE_LOCATION_QUERY_NAME = "location";

    private SearchCondition name;
    private SearchCondition location;
    private SearchCondition locationId;
    private int size;
    private int from;
    private Map<String, Object> map = new HashMap<>();


    public String query(boolean applySizeFilters) throws CustomException {
        String query = "SELECT * FROM " + GREENHOUSES_TABLE + " WHERE ";

        if (name != null && isNotEmpty(name.getCondition()) && isNotEmpty(name.getValue())) {
            query += addIntegerFilter(name, GREENHOUSE_NAME, GREENHOUSE_NAME_QUERY_NAME, map);
        }

        if (location != null && isNotEmpty(location.getCondition()) && isNotEmpty(location.getValue())) {
            query += addStringFilter(name, GREENHOUSE_LOCATION, GREENHOUSE_LOCATION_QUERY_NAME, map);
        }

        if (locationId != null && isNotEmpty(locationId.getCondition()) && isValidInteger(locationId.getValue())) {
            query += addIntegerFilter(locationId, GREENHOUSE_LOCATIONS_ID, GREENHOUSE_LOCATIONS_ID_QUERY_NAME, map);
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

    public SearchCondition getLocationId() { return locationId; }

    public void setLocationId(SearchCondition locationId) { this.locationId = locationId; }

    @Override
    public int getSize() { return size; }

    @Override
    public void setSize(int size) { this.size = size; }

    @Override
    public int getFrom() { return from; }

    @Override
    public void setFrom(int from) { this.from = from; }
}
