package com.teamgreen.greenhouse.dao.search.dao;

import com.teamgreen.greenhouse.dao.SearchCondition;
import com.teamgreen.greenhouse.exceptions.CustomException;

import java.util.HashMap;
import java.util.Map;

import static com.teamgreen.greenhouse.dao.search.dao.SearchDaoUtils.addIntegerFilter;
import static com.teamgreen.greenhouse.dao.search.dao.SearchDaoUtils.addPostQueryClause;
import static com.teamgreen.greenhouse.dao.search.dao.SearchDaoUtils.addStringFilter;
import static com.teamgreen.greenhouse.fertilizers.Constants.FERTILIZER_TABLE;
import static com.teamgreen.greenhouse.fertilizers.Constants.FERTILIZER_PLANT_ID;
import static com.teamgreen.greenhouse.fertilizers.Constants.FERTILIZER_NAME;
import static com.teamgreen.greenhouse.utils.Util.isNotEmpty;
import static com.teamgreen.greenhouse.utils.Util.isValidInteger;

public class FertilizerSearchDao  implements SearchDao  {


    public static final String FERTILIZER_NAME_QUERY_NAME = "name";
    public static final String FERTILIZER_PLANT_ID_QUERY_NAME = "plantId";

    private SearchCondition name;
    private SearchCondition plantId;
    private int size;
    private int from;
    private Map<String, Object> map = new HashMap<>();

    public String query(boolean applySizeFilters) throws CustomException {
        String query = "SELECT * FROM " + FERTILIZER_TABLE + " WHERE ";

        if (name != null && isNotEmpty(name.getCondition()) && isNotEmpty(name.getValue())) {
            query += addStringFilter(name, FERTILIZER_NAME, FERTILIZER_NAME_QUERY_NAME, map);
        }

        if (plantId != null && isNotEmpty(plantId.getCondition()) && isValidInteger(plantId.getValue())) {
            query += addIntegerFilter(plantId, FERTILIZER_PLANT_ID, FERTILIZER_PLANT_ID_QUERY_NAME, map);
        }

        return addPostQueryClause(query, this, applySizeFilters);
    }

    public Map<String, Object> namedParameterMap() {
        return map;
    }

    public SearchCondition getName() { return name; }

    public void setName(SearchCondition name) { this.name = name; }

    public SearchCondition getPlantId() { return plantId; }

    public void setPlantId(SearchCondition plantId) { this.plantId = plantId; }

    @Override
    public int getSize() { return size; }

    @Override
    public void setSize(int size) { this.size = size; }

    @Override
    public int getFrom() { return from; }

    @Override
    public void setFrom(int from) { this.from = from; }
}
