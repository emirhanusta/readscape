package com.bookservice.controller;

import com.bookservice.service.S3Service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/images")
@RequiredArgsConstructor
public class ImageController {

    private final S3Service s3Service;

    @PostMapping("/{id}")
    public ResponseEntity<InputStreamResource> saveImage(@PathVariable UUID id, @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .header("Content-Disposition", "attachment; filename=" + id.toString() + ".png")
                .body(s3Service.saveImage(id, file));
    }

    @GetMapping("/{id}")
    public ResponseEntity<InputStreamResource> getImage(@PathVariable UUID id) {
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .header("Content-Disposition", "attachment; filename=" + id.toString() + ".png")
                .body(s3Service.viewImage(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable UUID id) {
        s3Service.deleteImage(id);
        return ResponseEntity.noContent().build();
    }
}
