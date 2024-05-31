package com.bookservice.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.S3Object;
import com.bookservice.exception.S3Exception;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class S3Service {

    Logger log = LoggerFactory.getLogger(S3Service.class);
    private final AmazonS3 s3Client;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    public S3Service(AmazonS3 s3Client) {
        this.s3Client = s3Client;
    }

    public InputStreamResource saveImage(UUID id, MultipartFile file) {
        try {
            uploadFile(id.toString() + ".png", file);
        } catch (Exception e) {
            throw new S3Exception("An error occurred while uploading file to S3", HttpStatus.BAD_REQUEST);
        }
        return viewImage(id);
    }

    public InputStreamResource viewImage(UUID id) {
        var s3Object = getFile(id.toString() + ".png");
        if (s3Object == null) {
            throw new S3Exception("Image not found in S3", HttpStatus.NOT_FOUND);
        }
        var content = s3Object.getObjectContent();
        return new InputStreamResource(content);
    }

    public void deleteImage(UUID id) {
        if (id == null) {
            throw new S3Exception("Id cannot be null", HttpStatus.BAD_REQUEST);
        }
        if (getFile(id + ".png") != null) {
            deleteFile(id + ".png");
        }
    }
    private void uploadFile(String key, MultipartFile file) throws IOException {
        var putObjectResult = s3Client.putObject(bucketName, key, file.getInputStream(), null);
        log.info(String.valueOf(putObjectResult.getMetadata()));
    }
    private S3Object getFile(String key) {
        try {
            return s3Client.getObject(bucketName, key);
        } catch (AmazonS3Exception e) {
            log.error("An error occurred while getting file from S3 bucket with key: " + key);
            return null;
        }
    }


    private void deleteFile(String key)  {
        s3Client.deleteObject(bucketName, key);
        log.info("File deleted from S3");
    }
}
