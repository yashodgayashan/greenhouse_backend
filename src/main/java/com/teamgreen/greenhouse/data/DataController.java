package com.teamgreen.greenhouse.data;

import com.teamgreen.greenhouse.dao.Data;
import com.teamgreen.greenhouse.exceptions.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

import static com.teamgreen.greenhouse.constants.Constants.*;
import static com.teamgreen.greenhouse.constants.Constants.INTERNAL_SERVER_ERROR_MSG;

@RestController
@RequestMapping("/data")
public class DataController {

    @Autowired
    JdbcTemplate jdbc;
    @Autowired
    NamedParameterJdbcTemplate namedParamJdbc;

    private static final Logger logger = LoggerFactory.getLogger(DataController.class);
    private DataDbHandler handler;

    @PostConstruct
    void setJdbcHandlers() {
        handler = new DataDbHandler(this.jdbc, this.namedParamJdbc);
    }


    @GetMapping("")
    public ResponseEntity getData() {
        return new ResponseEntity(handler.getData(), HttpStatus.OK);
    }

    @GetMapping("/{data-id}")
    public ResponseEntity getData(@PathVariable("data-id") long data) {
        try {
            return new ResponseEntity(handler.getData(data), HttpStatus.OK);
        } catch (CustomException e) {
            logger.error(e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity createData(@RequestBody Data data) {
        int status = handler.addData(data);
        if (status > 0) {
            return new ResponseEntity<>(SUCCESSFULLY_INSERTED, HttpStatus.OK);
        } else {
            logger.error("inserted data failed, {}", data);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
