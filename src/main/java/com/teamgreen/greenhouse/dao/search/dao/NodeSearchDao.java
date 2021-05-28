package com.teamgreen.greenhouse.dao.search.dao;

import com.teamgreen.greenhouse.dao.SearchCondition;
import com.teamgreen.greenhouse.exceptions.CustomException;

import java.util.HashMap;
import java.util.Map;

import static com.teamgreen.greenhouse.dao.search.dao.SearchDaoUtils.*;
import static com.teamgreen.greenhouse.nodes.Constants.*;
import static com.teamgreen.greenhouse.utils.Util.isNotEmpty;
import static com.teamgreen.greenhouse.utils.Util.isValidInteger;

public class NodeSearchDao  implements SearchDao {

    public static final String NODE_GREENHOUSE_ID_QUERY_NAME = "greenhouseId";
    public static final String NODE_ID_QUERY_NAME = "id";

    private SearchCondition id;
    private SearchCondition greenhouseId;
    private int size;
    private int from;
    private Map<String, Object> map = new HashMap<>();


    public String query(boolean applySizeFilters) throws CustomException {
        String query = "SELECT * FROM " + NODES_TABLE + " WHERE ";

        if (id != null && isNotEmpty(id.getCondition()) && isValidInteger(id.getValue())) {
            query += addIntegerFilter(id, NODE_ID, NODE_ID_QUERY_NAME, map);
        }

        if (greenhouseId != null && isNotEmpty(greenhouseId.getCondition()) && isValidInteger(greenhouseId.getValue())) {
            query += addIntegerFilter(greenhouseId, NODE_GREENHOUSE_ID, NODE_GREENHOUSE_ID_QUERY_NAME, map);
        }

        return addPostQueryClause(query, this, applySizeFilters);
    }

    public Map<String, Object> namedParameterMap() {
        return map;
    }

    public SearchCondition getId() { return id; }

    public void setId(SearchCondition id) { this.id = id; }

    public SearchCondition getGreenhouseId() { return greenhouseId; }

    public void setGreenhouseId(SearchCondition greenhouseId) { this.greenhouseId = greenhouseId; }

    @Override
    public int getSize() { return size; }

    @Override
    public void setSize(int size) { this.size = size; }

    @Override
    public int getFrom() { return from; }

    @Override
    public void setFrom(int from) { this.from = from; }
}
