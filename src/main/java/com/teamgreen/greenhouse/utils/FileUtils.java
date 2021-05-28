package com.teamgreen.greenhouse.utils;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtils {

    HttpHeaders headers = new HttpHeaders();
    MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

    public HttpEntity constructDefectDetectionRequestEntity(MultipartFile multipartFile) throws IOException {
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        body.add("file", convertMultipartFileToFileSystemResource(multipartFile));
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        return requestEntity;
    }

    public HttpEntity constructHarvestRequestEntity(MultipartFile multipartFile) throws IOException {
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        body.add("image", convertMultipartFileToFileSystemResource(multipartFile));
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        return requestEntity;
    }

    private File convertMultiPartToFile(MultipartFile file ) throws IOException
    {
        File convFile = new File( file.getOriginalFilename() );
        FileOutputStream fos = new FileOutputStream( convFile );
        fos.write( file.getBytes() );
        fos.close();
        return convFile;
    }

    private FileSystemResource convertMultipartFileToFileSystemResource(MultipartFile file) throws IOException {
        return new FileSystemResource(convertMultiPartToFile(file));
    }
}
