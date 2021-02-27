package com.teamgreen.greenhouse.locations;

import com.teamgreen.greenhouse.dao.Location;
import com.teamgreen.greenhouse.exceptions.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

import static com.teamgreen.greenhouse.constants.Constants.*;

@RestController
@RequestMapping("/locations")
public class LocationController {

    @Autowired
    JdbcTemplate jdbc;

    private static final Logger logger = LoggerFactory.getLogger(LocationController.class);
    private LocationDbHandler handler;

    @PostConstruct
    void setJdbcHandlers() {
        handler = new LocationDbHandler(this.jdbc);
    }

    @GetMapping("")
    public ResponseEntity getLocations() {
        return new ResponseEntity(handler.getLocations(), HttpStatus.OK);
    }

    @GetMapping("/{location-id}")
    public ResponseEntity getLocation(@PathVariable("location-id") long locationId) {
        try {
            return new ResponseEntity(handler.getLocation(locationId), HttpStatus.OK);
        } catch (CustomException e) {
            logger.error(e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity createLocation(@RequestBody Location location) {
        int status = handler.addLocation(location);
        if (status > 0) {
            return new ResponseEntity<>(SUCCESSFULLY_INSERTED, HttpStatus.OK);
        } else {
            logger.error("inserted location failed, {}", location);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{location-id}")
    public ResponseEntity updateLocation(@PathVariable("location-id") long locationId, @RequestBody Location location) {
        int status = handler.updateLocation(locationId, location);
        if (status > 0) {
            return new ResponseEntity<>(SUCCESSFULLY_UPDATED, HttpStatus.OK);
        } else {
            logger.error("updating location failed, {}", location);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{location-id}")
    public ResponseEntity deleteLocation(@PathVariable("location-id") long locationId) {
        int status = handler.deleteLocation(locationId);
        if (status > 0) {
            return new ResponseEntity<>(SUCCESSFULLY_REMOVED, HttpStatus.OK);
        } else {
            logger.error("updating location failed, {}", locationId);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
