package com.teamgreen.greenhouse.greenhouses;

import com.teamgreen.greenhouse.dao.Greenhouse;
import com.teamgreen.greenhouse.dao.Location;
import com.teamgreen.greenhouse.dao.search.dao.GreenhouseSearchDao;
import com.teamgreen.greenhouse.dao.search.dao.LocationSearchDao;
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
import static com.teamgreen.greenhouse.greenhouses.Constants.GREENHOUSES_TABLE;
import static com.teamgreen.greenhouse.locations.Constants.LOCATIONS_TABLE;

@RestController
@RequestMapping("/greenhouses")
public class GreenhouseController {

    @Autowired
    JdbcTemplate jdbc;
    @Autowired
    NamedParameterJdbcTemplate namedParamJdbc;

    private static final Logger logger = LoggerFactory.getLogger(GreenhouseController.class);
    private GreenhousesDbHandler handler;

    @PostConstruct
    void setJdbcHandlers() {
        handler = new GreenhousesDbHandler(this.jdbc, this.namedParamJdbc);
    }

    @GetMapping("")
    public ResponseEntity getGreenhouses() {
        return new ResponseEntity(handler.getGreenhouses(), HttpStatus.OK);
    }


    @GetMapping("/id")
    public ResponseEntity getGreenhouseId() throws MysqlHandlerException {
        DbUtils dbUtils = new DbUtils(this.jdbc);
        long id = dbUtils.getLastIdFromTable(GREENHOUSES_TABLE) + 1;
        return new ResponseEntity(id, HttpStatus.OK);
    }

    @GetMapping("/{greenhouse-id}")
    public ResponseEntity getGreenhouse(@PathVariable("greenhouse-id") long greenhouseId) {
        try {
            return new ResponseEntity(handler.getGreenhouse(greenhouseId), HttpStatus.OK);
        } catch (CustomException e) {
            logger.error(e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity createGreenhouse(@RequestBody Greenhouse greenhouse) throws MysqlHandlerException {
        int status = handler.addGreenhouse(greenhouse);
            if (status > 0) {
            DbUtils dbUtils = new DbUtils(this.jdbc);
            long id = dbUtils.getLastIdFromTable(GREENHOUSES_TABLE);
            return new ResponseEntity<>(id, HttpStatus.OK);
        } else {
            logger.error("inserted greenhouse failed, {}", greenhouse);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{greenhouse-id}")
    public ResponseEntity updateLocation(@PathVariable("greenhouse-id") long greenhouseId, @RequestBody Greenhouse greenhouse) {
        int status = handler.updateGreenhouse(greenhouseId, greenhouse);
        if (status > 0) {
            return new ResponseEntity<>(SUCCESSFULLY_UPDATED, HttpStatus.OK);
        } else {
            logger.error("updating greenhouse failed, {}", greenhouse);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{greenhouse-id}")
    public ResponseEntity deleteLocation(@PathVariable("greenhouse-id") long greenhouseId) {
        int status = handler.deleteGreenhouse(greenhouseId);
        if (status > 0) {
            return new ResponseEntity<>(SUCCESSFULLY_REMOVED, HttpStatus.OK);
        } else {
            logger.error("updating greenhouse failed, {}", greenhouseId);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/search")
    public ResponseEntity searchLocations(@RequestBody GreenhouseSearchDao searchDao) {
        List<Greenhouse> greenhouses;
        try {
            greenhouses = handler.searchGreenhouses(searchDao);
        } catch (CustomException e) {
            logger.error("error occurred while searching splits\n" + e.getMessage(), e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(greenhouses, HttpStatus.OK);

    }
}
