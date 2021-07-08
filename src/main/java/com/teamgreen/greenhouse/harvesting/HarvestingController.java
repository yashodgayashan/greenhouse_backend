package com.teamgreen.greenhouse.harvesting;

import com.teamgreen.greenhouse.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.IOException;

@RestController
@RequestMapping("/harvest")
public class HarvestingController {

    @Autowired
    JdbcTemplate jdbc;
    @Autowired
    NamedParameterJdbcTemplate namedParamJdbc;
    @Autowired
    RestTemplate restTemplate;

    @Value("${remote.harvest.url}")
    private String remoteUrl;

    private static final Logger logger = LoggerFactory.getLogger(HarvestingController.class);
    FileUtils fileUtils;

    @PostMapping
    public ResponseEntity harvest(@RequestParam("file") MultipartFile file) throws IOException {
        fileUtils = new FileUtils();
        HttpEntity<MultiValueMap<String, Object>> requestEntity = fileUtils.constructHarvestRequestEntity(file);
        restTemplate.getMessageConverters().add(new ByteArrayHttpMessageConverter());
        ResponseEntity<byte[]> response = this.restTemplate.exchange(remoteUrl , HttpMethod.POST, requestEntity, byte[].class);
        return response;
    }

    @PostMapping("/identify")
    public ResponseEntity identify(@RequestParam("file") MultipartFile file, @RequestParam("distance") Integer distance) throws IOException {
        System.out.println("harvest identification route");
        System.out.println(distance);
        fileUtils = new FileUtils();
        HttpEntity<MultiValueMap<String, Object>> requestEntity = fileUtils.constructIdentificationRequestEntity(file, distance);
        restTemplate.getMessageConverters().add(new ByteArrayHttpMessageConverter());
        ResponseEntity<byte[]> response = this.restTemplate.exchange(remoteUrl + "/identify", HttpMethod.POST, requestEntity, byte[].class);
        return response;
    }

    @PostMapping("/calculateLength")
    public ResponseEntity calculateLength(@RequestParam("file") MultipartFile file, @RequestParam("distance") Integer distance) throws IOException {
        System.out.println("calculate length route");
        System.out.println(distance);
        fileUtils = new FileUtils();
        HttpEntity<MultiValueMap<String, Object>> requestEntity = fileUtils.calculateLength(file, distance);
        restTemplate.getMessageConverters().add(new ByteArrayHttpMessageConverter());
        ResponseEntity<byte[]> response = this.restTemplate.exchange(remoteUrl + "/calculateLength", HttpMethod.POST, requestEntity, byte[].class);
        return response;
    }
}
