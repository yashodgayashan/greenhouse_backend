package com.teamgreen.greenhouse.nodeSensors;

import com.teamgreen.greenhouse.dao.NodeSensor;
import com.teamgreen.greenhouse.dao.mappers.NodeSensorMapper;
import com.teamgreen.greenhouse.dao.search.dao.NodeSensorSearchDao;
import com.teamgreen.greenhouse.exceptions.CustomException;
import com.teamgreen.greenhouse.store.DbHandler;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

import static com.teamgreen.greenhouse.constants.Constants.IS_DISABLED;

import static com.teamgreen.greenhouse.nodeSensors.Constants.*;
import static com.teamgreen.greenhouse.utils.MiscellaneousUtils.*;
import static com.teamgreen.greenhouse.utils.MiscellaneousUtils.getUpdateSyntaxFinal;

public class NodeSensorDbHandler extends DbHandler {

    public NodeSensorDbHandler(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }

    List<NodeSensor> getNodeSensors() {
        final String query = "SELECT * FROM " + NODE_SENSORS_TABLE + " ORDER BY " + NODE_SENSOR_ID + " DESC";
        return this.jdbcTemplate().query(query, new NodeSensorMapper());
    }

    NodeSensor getNodeSensor(long id) throws CustomException {
        try {
            final String query = "SELECT * FROM " + NODE_SENSORS_TABLE + " WHERE " + NODE_SENSOR_ID + " = ?";
            return this.jdbcTemplate().queryForObject(query, new NodeSensorMapper(), id);
        } catch (Exception e) {
            throw new CustomException("There is no node found with id: " + id);
        }
    }

    int addNodeSensor(NodeSensor nodeSensor)  {
        final String insertQuery =
                "INSERT INTO " + NODE_SENSORS_TABLE + " ("  + withComma(NODE_SENSOR_NODE_ID) + withComma(NODE_SENSOR_SENSOR_ID)
                        + withComma(NODE_SENSOR_MIN_VALUE) + encapFieldWithBackTick(NODE_SENSOR_MAX_VALUE) + ") VALUES "
                        + getStatementParams(4);

        return this.jdbcTemplate().update(
                insertQuery,
                nodeSensor.getNodeId(),
                nodeSensor.getSensorId(),
                nodeSensor.getMinValue(),
                nodeSensor.getMaxValue()
        );
    }

    int updateNodeSensor(long id, NodeSensor nodeSensor)  {
        final String updateQuery =
                "UPDATE " + NODE_SENSORS_TABLE + " SET " + getUpdateSyntax(NODE_SENSOR_NODE_ID)
                        + getUpdateSyntax(NODE_SENSOR_SENSOR_ID) + getUpdateSyntax(NODE_SENSOR_MIN_VALUE)
                        + getUpdateSyntaxFinal(NODE_SENSOR_MAX_VALUE) + " WHERE id = ?";
        return this.jdbcTemplate().update(
                updateQuery,
                nodeSensor.getNodeId(),
                nodeSensor.getSensorId(),
                nodeSensor.getMinValue(),
                nodeSensor.getMaxValue(),
                id
        );
    }

    int deleteNodeSensor(long id) {
        final String deleteQuery =
                "UPDATE " + NODE_SENSORS_TABLE + " SET " + getUpdateSyntaxFinal(IS_DISABLED) + " WHERE id = ?";
        return this.jdbcTemplate().update(deleteQuery, 1, id);
    }

    List<NodeSensor> searchNodeSensors(NodeSensorSearchDao searchDao) throws CustomException {
        return this.namedJdbcTemplate().query(
                searchDao.query(true), searchDao.namedParameterMap(), new NodeSensorMapper());
    }
}
