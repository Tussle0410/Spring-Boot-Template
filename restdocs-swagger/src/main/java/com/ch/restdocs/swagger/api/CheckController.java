package com.ch.restdocs.swagger.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class CheckController {
    @GetMapping("/health-check")
    public ResponseEntity<String> healthCheck(){
        return successResponse();
    }

    private ResponseEntity<String> successResponse() {
        String successMessage = "OK";
        return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }
}
