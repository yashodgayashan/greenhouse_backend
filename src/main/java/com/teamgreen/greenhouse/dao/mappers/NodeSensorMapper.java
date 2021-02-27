package com.teamgreen.greenhouse.dao.mappers;

import com.teamgreen.greenhouse.dao.NodeSensor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.teamgreen.greenhouse.constants.Constants.*;
import static com.teamgreen.greenhouse.nodeSensors.Constants.*;

public class NodeSensorMapper implements RowMapper<NodeSensor> {

    @Override
    public NodeSensor mapRow(ResultSet rs, int i) throws SQLException {
        NodeSensor nodeSensor = new NodeSensor();
        nodeSensor.setId(rs.getLong(NODE_SENSOR_ID));
        nodeSensor.setNodeId(rs.getLong(NODE_SENSOR_NODE_ID));
        nodeSensor.setSensorId(rs.getLong(NODE_SENSOR_SENSOR_ID));
        nodeSensor.setMinValue(rs.getDouble(NODE_SENSOR_MIN_VALUE));
        nodeSensor.setMaxValue(rs.getDouble(NODE_SENSOR_MAX_VALUE));
        nodeSensor.setDisabled(rs.getBoolean(IS_DISABLED));
        nodeSensor.setCreatedAt(rs.getTimestamp(CREATED_AT));
        nodeSensor.setModifiedAt(rs.getTimestamp(MODIFIED_AT));
        return nodeSensor;
    }

}
