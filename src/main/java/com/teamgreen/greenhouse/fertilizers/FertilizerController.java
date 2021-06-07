package com.teamgreen.greenhouse.fertilizers;


import com.teamgreen.greenhouse.dao.Fertilizer;
import com.teamgreen.greenhouse.dao.search.dao.FertilizerSearchDao;
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

import static com.teamgreen.greenhouse.constants.Constants.SUCCESSFULLY_REMOVED;
import static com.teamgreen.greenhouse.constants.Constants.SUCCESSFULLY_UPDATED;
import static com.teamgreen.greenhouse.constants.Constants.INTERNAL_SERVER_ERROR_MSG;
import static com.teamgreen.greenhouse.fertilizers.Constants.FERTILIZER_TABLE;

@RestController
@RequestMapping("/fertilizers")
public class FertilizerController {

    @Autowired
    JdbcTemplate jdbc;
    @Autowired
    NamedParameterJdbcTemplate namedParamJdbc;

    private static final Logger logger = LoggerFactory.getLogger(FertilizerController.class);
    private FertilizerDbHandler handler;

    @PostConstruct
    void setJdbcHandlers() {
        handler = new FertilizerDbHandler(this.jdbc, this.namedParamJdbc);
    }

    @GetMapping("")
    public ResponseEntity getFertilizers() {
        return new ResponseEntity(handler.getFertilizers(), HttpStatus.OK);
    }


    @GetMapping("/{fertilizer-id}")
    public ResponseEntity getFertilizer(@PathVariable("fertilizer-id") long fertilizerId) {
        try {
            return new ResponseEntity(handler.getFertilizer(fertilizerId), HttpStatus.OK);
        } catch (CustomException e) {
            logger.error(e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity createFertilizer(@RequestBody Fertilizer fertilizer) throws MysqlHandlerException {
        int status = handler.addFertilizer(fertilizer);
        if (status > 0) {
            DbUtils dbUtils = new DbUtils(this.jdbc);
            long id = dbUtils.getLastIdFromTable(FERTILIZER_TABLE);
            return new ResponseEntity<>(id, HttpStatus.OK);
        } else {
            logger.error("inserted fertilizer failed, {}", fertilizer);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{fertilizer-id}")
    public ResponseEntity updateFertilizer(@PathVariable("fertilizer-id") long fertilizerId,
                                           @RequestBody Fertilizer fertilizer) {
        int status = handler.updateFertilizer(fertilizerId, fertilizer);
        if (status > 0) {
            return new ResponseEntity<>(SUCCESSFULLY_UPDATED, HttpStatus.OK);
        } else {
            logger.error("updating fertilizer failed, {}", fertilizer);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{fertilizer-id}")
    public ResponseEntity deleteFertilizer(@PathVariable("fertilizer-id") long fertilizerId) {
        int status = handler.deleteFertilizer(fertilizerId);
        if (status > 0) {
            return new ResponseEntity<>(SUCCESSFULLY_REMOVED, HttpStatus.OK);
        } else {
            logger.error("updating fertilizer failed, {}", fertilizerId);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/search")
    public ResponseEntity searchFertilizers(@RequestBody FertilizerSearchDao searchDao) {
        List<Fertilizer> fertilizers;
        try {
            fertilizers = handler.searchFertilizer(searchDao);
        } catch (CustomException e) {
            logger.error("error occurred while searching fertilizers\n" + e.getMessage(), e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(fertilizers, HttpStatus.OK);
    }

}
