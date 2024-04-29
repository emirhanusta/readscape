package com.bookservice.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.S3Object;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@Log4j2
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3 s3Client;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    public InputStreamResource saveImage(UUID id, MultipartFile file) {
        try {
            uploadFile(id.toString() + ".png", file);
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload file to S3", e);
        }
        return viewImage(id);
    }

    public InputStreamResource viewImage(UUID id) {
        var s3Object = getFile(id.toString() + ".png");
        if (s3Object == null) {
            throw new RuntimeException("File not found in S3");
        }
        var content = s3Object.getObjectContent();
        return new InputStreamResource(content);
    }

    public void deleteImage(UUID id) {
        if (id == null) {
            throw new RuntimeException("Id cannot be null");
        }
        if (getFile(id + ".png") != null) {
            deleteFile(id + ".png");
        }
        log.info("File not found in S3");
    }
    private void uploadFile(String key, MultipartFile file) throws IOException {
        var putObjectResult = s3Client.putObject(bucketName, key, file.getInputStream(), null);
        log.info(putObjectResult.getMetadata());
    }
    private S3Object getFile(String key) {
        try {
            return s3Client.getObject(bucketName, key);
        } catch (AmazonS3Exception e) {
            log.error("File with key " + key + " not found in S3 bucket");
            return null;
        }
    }


    private void deleteFile(String key)  {
        s3Client.deleteObject(bucketName, key);
        log.info("File deleted from S3");
    }
}
