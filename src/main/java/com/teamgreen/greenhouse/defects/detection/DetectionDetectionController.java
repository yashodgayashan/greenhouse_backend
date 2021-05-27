package com.teamgreen.greenhouse.defects.detection;

import com.teamgreen.greenhouse.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@RestController
@RequestMapping("/defect-detection")
public class DetectionDetectionController {

    @Autowired
    JdbcTemplate jdbc;
    @Autowired
    NamedParameterJdbcTemplate namedParamJdbc;
    @Autowired
    RestTemplate restTemplate;

    @Value("${remote.defect.url}")
    private String remoteUrl;

    private static final Logger logger = LoggerFactory.getLogger(DetectionDetectionController.class);
    FileUtils fileUtils;

    @PostMapping
    public ResponseEntity defectDetection(@RequestParam("file") MultipartFile file) throws IOException {
        fileUtils = new FileUtils();
        HttpEntity<MultiValueMap<String, Object>> requestEntity = fileUtils.constructDefectDetectionRequestEntity(file);
        ResponseEntity<String> response = this.restTemplate.exchange(remoteUrl + "/predict", HttpMethod.POST,
                requestEntity, String.class);
        return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
    }
}
