package com.teamgreen.greenhouse.greenhousewaterschedule;

import com.teamgreen.greenhouse.dao.Data;
import com.teamgreen.greenhouse.dao.WaterSchedule;
import com.teamgreen.greenhouse.dao.search.dao.WaterScheduleSearchDao;
import com.teamgreen.greenhouse.exceptions.CustomException;
import com.teamgreen.greenhouse.exceptions.MysqlHandlerException;
import com.teamgreen.greenhouse.utils.DbUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import static com.teamgreen.greenhouse.constants.Constants.*;
import static com.teamgreen.greenhouse.greenhousewaterschedule.Constants.*;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
@RequestMapping("/schedules")
public class GreenhouseWaterFlowController {

    @Autowired
    JdbcTemplate jdbc;
    @Autowired
    NamedParameterJdbcTemplate namedParamJdbc;
    @Autowired
    RestTemplate restTemplate;

    @Value("${weather.url}")
    private String remoteUrl;

    @Value("${api.key}")
    private String apiKey;

    private static final Logger logger = LoggerFactory.getLogger(GreenhouseWaterFlowController.class);
    private GreenhouseWaterFlowDbHandler handler;

    @PostConstruct
    void setJdbcHandlers() {
        handler = new GreenhouseWaterFlowDbHandler(this.jdbc, this.namedParamJdbc);
    }

    @GetMapping
    public ResponseEntity getWaterSchedules() { return new ResponseEntity(handler.getWaterSchedules(), HttpStatus.OK); }

    @GetMapping("/id")
    public ResponseEntity getScheduleId() throws MysqlHandlerException{
        DbUtils dbUtils = new DbUtils(this.jdbc);
        long id = dbUtils.getLastIdFromTable(WATER_SCHEDULE_TABLE) + 1;
        return new ResponseEntity(id, HttpStatus.OK);
    }

    @GetMapping("/{schedule-id}")
    public ResponseEntity getSchedule(@PathVariable("schedule-id") long scheduleId) {
        try {
            return new ResponseEntity(handler.getWaterSchedule(scheduleId), HttpStatus.OK);
        } catch (CustomException e) {
            logger.error(e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity createWaterSchedule(@RequestParam boolean isEnabled,
                                              @RequestBody WaterSchedule waterSchedule) throws MysqlHandlerException {
        waterSchedule.setEnabled(isEnabled);

        int status = handler.addSchedule(waterSchedule);
            if (status > 0) {
                DbUtils dbUtils = new DbUtils(this.jdbc);
                long id = dbUtils.getLastIdFromTable(WATER_SCHEDULE_TABLE);
                return new ResponseEntity<>(id, HttpStatus.OK);
            } else {
                logger.error("insert water schedule failed, {}", waterSchedule);
                return new ResponseEntity<>(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
            }
    }

    @PutMapping("/{schedule-id}")
    public ResponseEntity updateSchedule(@PathVariable("schedule-id") long scheduleId,
                                         @RequestBody WaterSchedule waterSchedule) {
        int status = handler.updateSchedule(scheduleId, waterSchedule);
        if (status > 0) {
            return new ResponseEntity<>(SUCCESSFULLY_UPDATED, HttpStatus.OK);
        } else {
            logger.error("updating schedule failed, {}", waterSchedule);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{schedule-id}")
    public ResponseEntity deleteSchedule(@PathVariable("schedule-id") long scheduleId) {
        int status = handler.deleteSchedule(scheduleId);
        if (status > 0) {
            return new ResponseEntity<>(SUCCESSFULLY_REMOVED, HttpStatus.OK);
        } else {
            logger.error("deleting schedule failed, {}", scheduleId);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/search")
    public ResponseEntity searchSchedule(@RequestBody WaterScheduleSearchDao searchDao) {
        List<WaterSchedule> waterSchedules;
        try {
            waterSchedules = handler.searchSchedules(searchDao);
        } catch (CustomException e) {
            logger.error("error occurred while searching splits\n" + e.getMessage(), e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(waterSchedules, HttpStatus.OK);
    }

    @GetMapping("/greenhouse/{greenhouse-id}")
    public ResponseEntity getSchedulesByGreenhouse(@PathVariable("greenhouse-id") long greenhouseId) {
        List<WaterSchedule> waterSchedules;
        try {
            waterSchedules = handler.getSchedulesByGreenhouse(greenhouseId);
        } catch (Exception e) {
            logger.error("error occured while getting schedules\n" + e.getMessage(), e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(waterSchedules, HttpStatus.OK);
    }

    @PutMapping("/state/{state}/{schedule-id}")
    public ResponseEntity changeStateOfASchedule(@PathVariable("state") boolean state,
                                                 @PathVariable("schedule-id") long scheduleId) {
        int status = handler.changeStateOfASchedule(scheduleId, state);
        if (status > 0) {
            return new ResponseEntity<>(SUCCESSFULLY_UPDATED, HttpStatus.OK);
        } else {
            logger.error("updating schedule failed, {}", scheduleId);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
