package com.teamgreen.greenhouse.nodeSensors;

import com.teamgreen.greenhouse.dao.NodeSensor;
import com.teamgreen.greenhouse.dao.search.dao.NodeSensorSearchDao;
import com.teamgreen.greenhouse.exceptions.CustomException;
import com.teamgreen.greenhouse.exceptions.MysqlHandlerException;
import com.teamgreen.greenhouse.utils.DbUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

import java.util.List;

import static com.teamgreen.greenhouse.constants.Constants.*;
import static com.teamgreen.greenhouse.constants.Constants.INTERNAL_SERVER_ERROR_MSG;
import static com.teamgreen.greenhouse.nodeSensors.Constants.NODE_SENSORS_TABLE;

@RestController
@RequestMapping("/node-sensors")
public class NodeSensorController {

    @Autowired
    JdbcTemplate jdbc;
    @Autowired
    NamedParameterJdbcTemplate namedParamJdbc;

    private static final Logger logger = LoggerFactory.getLogger(NodeSensorController.class);
    private NodeSensorDbHandler handler;

    @PostConstruct
    void setJdbcHandlers() {
        handler = new NodeSensorDbHandler(this.jdbc, this.namedParamJdbc);
    }

    @GetMapping("")
    public ResponseEntity getNodeSensors() {
        return new ResponseEntity(handler.getNodeSensors(), HttpStatus.OK);
    }

    @GetMapping("/{node-sensor-id}")
    public ResponseEntity getNodeSensor(@PathVariable("node-sensor-id") long nodeSensorId) {
        try {
            return new ResponseEntity(handler.getNodeSensor(nodeSensorId), HttpStatus.OK);
        } catch (CustomException e) {
            logger.error(e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity createNodeSensor(@RequestBody NodeSensor nodeSensor) throws MysqlHandlerException {
        int status = handler.addNodeSensor(nodeSensor);
        if (status > 0) {
            DbUtils dbUtils = new DbUtils(this.jdbc);
            long id = dbUtils.getLastIdFromTable(NODE_SENSORS_TABLE);
            return new ResponseEntity<>(id, HttpStatus.OK);
        } else {
            logger.error("inserted nodeSensor failed, {}", nodeSensor);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{node-sensor-id}")
    public ResponseEntity updateNodeSensor(@PathVariable("node-sensor-id") long nodeSensorId, @RequestBody NodeSensor nodeSensor) {
        int status = handler.updateNodeSensor(nodeSensorId, nodeSensor);
        if (status > 0) {
            return new ResponseEntity<>(SUCCESSFULLY_UPDATED, HttpStatus.OK);
        } else {
            logger.error("updating nodeSensor failed, {}", nodeSensor);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{node-sensor-id}")
    public ResponseEntity deleteNodeSensor(@PathVariable("node-sensor-id") long nodeSensorId) {
        int status = handler.deleteNodeSensor(nodeSensorId);
        if (status > 0) {
            return new ResponseEntity<>(SUCCESSFULLY_REMOVED, HttpStatus.OK);
        } else {
            logger.error("updating nodeSensor failed, {}", nodeSensorId);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/search")
    public ResponseEntity searchNodeSensors(@RequestBody NodeSensorSearchDao searchDao) {
        List<NodeSensor> nodeSensors;
        try {
            nodeSensors = handler.searchNodeSensors(searchDao);
        } catch (CustomException e) {
            logger.error("error occurred while searching splits\n" + e.getMessage(), e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(nodeSensors, HttpStatus.OK);
    }
}
