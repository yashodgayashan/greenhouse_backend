package com.teamgreen.greenhouse.sensors;

import com.teamgreen.greenhouse.dao.Sensor;
import com.teamgreen.greenhouse.dao.search.dao.SensorSearchDao;
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
import static com.teamgreen.greenhouse.sensors.Constants.SENSORS_TABLE;

@RestController
@RequestMapping("/sensors")
public class SensorController {

    @Autowired
    JdbcTemplate jdbc;
    @Autowired
    NamedParameterJdbcTemplate namedParamJdbc;

    private static final Logger logger = LoggerFactory.getLogger(SensorController.class);
    private SensorDbHandler handler;

    @PostConstruct
    void setJdbcHandlers() {
        handler = new SensorDbHandler(this.jdbc, this.namedParamJdbc);
    }

    @GetMapping("")
    public ResponseEntity getSensors() {
        return new ResponseEntity(handler.getSensors(), HttpStatus.OK);
    }

    @GetMapping("/{sensor-id}")
    public ResponseEntity getSensor(@PathVariable("sensor-id") long sensorId) {
        try {
            return new ResponseEntity(handler.getSensor(sensorId), HttpStatus.OK);
        } catch (CustomException e) {
            logger.error(e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity createSensor(@RequestBody Sensor sensor) throws MysqlHandlerException {
        int status = handler.addSensor(sensor);
        if (status > 0) {
            DbUtils dbUtils = new DbUtils(this.jdbc);
            long id = dbUtils.getLastIdFromTable(SENSORS_TABLE);
            return new ResponseEntity<>(id, HttpStatus.OK);
        } else {
            logger.error("inserted sensor failed, {}", sensor);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{sensor-id}")
    public ResponseEntity updateSensor(@PathVariable("sensor-id") long sensorId, @RequestBody Sensor sensor) {
        int status = handler.updateSensor(sensorId, sensor);
        if (status > 0) {
            return new ResponseEntity<>(SUCCESSFULLY_UPDATED, HttpStatus.OK);
        } else {
            logger.error("updating sensor failed, {}", sensor);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{sensor-id}")
    public ResponseEntity deleteSensor(@PathVariable("sensor-id") long sensorId) {
        int status = handler.deleteSensor(sensorId);
        if (status > 0) {
            return new ResponseEntity<>(SUCCESSFULLY_REMOVED, HttpStatus.OK);
        } else {
            logger.error("updating sensor failed, {}", sensorId);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/search")
    public ResponseEntity searchSensors(@RequestBody SensorSearchDao searchDao) {
        List<Sensor> sensors;
        try {
            sensors = handler.searchSensors(searchDao);
        } catch (CustomException e) {
            logger.error("error occurred while searching splits\n" + e.getMessage(), e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(sensors, HttpStatus.OK);
    }
}
