package com.teamgreen.greenhouse.dao.search.dao;

import com.teamgreen.greenhouse.dao.SearchCondition;
import com.teamgreen.greenhouse.exceptions.CustomException;

import java.util.HashMap;
import java.util.Map;

import static com.teamgreen.greenhouse.dao.search.dao.SearchDaoUtils.*;
import static com.teamgreen.greenhouse.nodeSensors.Constants.*;
import static com.teamgreen.greenhouse.utils.Util.isNotEmpty;
import static com.teamgreen.greenhouse.utils.Util.isValidInteger;

public class NodeSensorSearchDao implements SearchDao {

    public static final String NODESENSOR_SENSOR_ID_QUERY_NAME = "sensorId";
    public static final String NODESENSOR_NODE_ID_QUERY_NAME = "nodeId";

    private SearchCondition sensorId;
    private SearchCondition nodeId;
    private int size;
    private int from;
    private Map<String, Object> map = new HashMap<>();

    public String query(boolean applySizeFilters) throws CustomException {
        String query = "SELECT * FROM " + NODE_SENSORS_TABLE + " WHERE ";

        if (sensorId != null && isNotEmpty(sensorId.getCondition()) && isValidInteger(sensorId.getValue())) {
            query += addIntegerFilter(sensorId, NODE_SENSOR_SENSOR_ID, NODESENSOR_SENSOR_ID_QUERY_NAME, map);
        }

        if (nodeId != null && isNotEmpty(nodeId.getCondition()) && isValidInteger(nodeId.getValue())) {
            query += addIntegerFilter(nodeId, NODE_SENSOR_NODE_ID, NODESENSOR_NODE_ID_QUERY_NAME, map);
        }

        return addPostQueryClause(query, this, applySizeFilters);
    }

    public Map<String, Object> namedParameterMap() {
        return map;
    }

    public SearchCondition getSensorId() { return sensorId; }

    public void setSensorId(SearchCondition sensorId) { this.sensorId = sensorId; }

    public SearchCondition getNodeId() { return nodeId; }

    public void setNodeId(SearchCondition nodeId) { this.nodeId = nodeId; }

    @Override
    public int getSize() { return size; }

    @Override
    public void setSize(int size) { this.size = size; }

    @Override
    public int getFrom() { return from; }

    @Override
    public void setFrom(int from) { this.from = from; }
}
