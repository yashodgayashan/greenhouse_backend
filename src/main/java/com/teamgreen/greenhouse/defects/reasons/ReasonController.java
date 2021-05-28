package com.teamgreen.greenhouse.defects.reasons;


import com.teamgreen.greenhouse.dao.DiseaseReason;
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
import static com.teamgreen.greenhouse.defects.Constants.DISEASE_REASONS_TABLE;

@RestController
@RequestMapping("/defect-reason")
public class ReasonController {

    @Autowired
    JdbcTemplate jdbc;
    @Autowired
    NamedParameterJdbcTemplate namedParamJdbc;

    private static final Logger logger = LoggerFactory.getLogger(ReasonController.class);
    private ReasonDbHandler handler;

    @PostConstruct
    void setJdbcHandlers() {
        handler = new ReasonDbHandler(this.jdbc, this.namedParamJdbc);
    }

    @GetMapping("")
    public ResponseEntity getDefectReasons(@RequestParam Optional<Long> diseaseId) {
        if (diseaseId.isPresent()) {
            return new ResponseEntity(handler.getDiseaseReasons(diseaseId.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity(handler.getDiseaseReasons(), HttpStatus.OK);
        }
    }

    @GetMapping("/{reason-id}")
    public ResponseEntity getDefectReason(@PathVariable("reason-id") long defectReasonId) {
        try {
            return new ResponseEntity(handler.getDiseaseReason(defectReasonId), HttpStatus.OK);
        } catch (CustomException e) {
            logger.error(e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity createDefectReason(@RequestBody DiseaseReason diseaseReason) throws MysqlHandlerException {
        int status = handler.addDiseaseReason(diseaseReason);
        if (status > 0) {
            DbUtils dbUtils = new DbUtils(this.jdbc);
            long id = dbUtils.getLastIdFromTable(DISEASE_REASONS_TABLE);
            return new ResponseEntity<>(id, HttpStatus.OK);
        } else {
            logger.error("inserted disease reason failed, {}", diseaseReason);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{reason-id}")
    public ResponseEntity updateDefectReason(@PathVariable("reason-id") long defectReasonId,
                                               @RequestBody DiseaseReason diseaseReason) {
        int status = handler.updateDiseaseReason(defectReasonId, diseaseReason);
        if (status > 0) {
            return new ResponseEntity<>(SUCCESSFULLY_UPDATED, HttpStatus.OK);
        } else {
            logger.error("updating disease reason failed, {}", diseaseReason);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{reason-id}")
    public ResponseEntity deleteDefectReason(@PathVariable("reason-id") long defectReasonId) {
        int status = handler.deleteDiseaseReason(defectReasonId);
        if (status > 0) {
            return new ResponseEntity<>(SUCCESSFULLY_REMOVED, HttpStatus.OK);
        } else {
            logger.error("deleting reason solution failed, {}", defectReasonId);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
