package com.bookservice.client;

import com.bookservice.dto.AuthorResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "author-service", path = "/api/v1/authors")
public interface AuthorServiceClient {

    @GetMapping("/{id}")
    ResponseEntity<AuthorResponse> getAuthorById(@PathVariable UUID id);
}
