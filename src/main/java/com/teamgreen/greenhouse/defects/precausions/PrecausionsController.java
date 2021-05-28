package com.teamgreen.greenhouse.defects.precausions;


import com.teamgreen.greenhouse.dao.DiseasePrecausions;
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
import java.util.Optional;

import static com.teamgreen.greenhouse.constants.Constants.*;
import static com.teamgreen.greenhouse.constants.Constants.INTERNAL_SERVER_ERROR_MSG;
import static com.teamgreen.greenhouse.defects.Constants.DISEASE_PRECAUSIONS_TABLE;

@RestController
@RequestMapping("/defect-precautions")
public class PrecausionsController {

    @Autowired
    JdbcTemplate jdbc;
    @Autowired
    NamedParameterJdbcTemplate namedParamJdbc;

    private static final Logger logger = LoggerFactory.getLogger(PrecausionsController.class);
    private PrecausionsDbHandler handler;

    @PostConstruct
    void setJdbcHandlers() {
        handler = new PrecausionsDbHandler(this.jdbc, this.namedParamJdbc);
    }

    @GetMapping("")
    public ResponseEntity getDefectPrecausions(@RequestParam Optional<Long> diseaseId) {
        if (diseaseId.isPresent()) {
            return new ResponseEntity(handler.getDiseasePrecausions(diseaseId.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity(handler.getDiseasePrecausions(), HttpStatus.OK);
        }
    }

    @GetMapping("/{precausion-id}")
    public ResponseEntity getDefectPrecausion(@PathVariable("precausion-id") long defectPrecausionId) {
        try {
            return new ResponseEntity(handler.getDiseasePrecausion(defectPrecausionId), HttpStatus.OK);
        } catch (CustomException e) {
            logger.error(e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity createDefectPrecausion(@RequestBody DiseasePrecausions diseasePrecausions) throws MysqlHandlerException {
        int status = handler.addDiseasePrecausion(diseasePrecausions);
        if (status > 0) {
            DbUtils dbUtils = new DbUtils(this.jdbc);
            long id = dbUtils.getLastIdFromTable(DISEASE_PRECAUSIONS_TABLE);
            return new ResponseEntity<>(id, HttpStatus.OK);
        } else {
            logger.error("inserted disease precausion failed, {}", diseasePrecausions);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{precausion-id}")
    public ResponseEntity updateDefectPrecausion(@PathVariable("precausion-id") long defectPrecausionId,
                                             @RequestBody DiseasePrecausions diseasePrecausions) {
        int status = handler.updateDiseasePrecausion(defectPrecausionId, diseasePrecausions);
        if (status > 0) {
            return new ResponseEntity<>(SUCCESSFULLY_UPDATED, HttpStatus.OK);
        } else {
            logger.error("updating disease precausion failed, {}", diseasePrecausions);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{precausion-id}")
    public ResponseEntity deleteDefectPrecausion(@PathVariable("precausion-id") long defectPrecausionId) {
        int status = handler.deleteDiseasePrecausion(defectPrecausionId);
        if (status > 0) {
            return new ResponseEntity<>(SUCCESSFULLY_REMOVED, HttpStatus.OK);
        } else {
            logger.error("deleting reason precausion failed, {}", defectPrecausionId);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
