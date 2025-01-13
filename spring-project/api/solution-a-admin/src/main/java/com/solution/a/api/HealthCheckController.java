package com.solution.a.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {
    @GetMapping("/health-check")
    public ResponseEntity<String> healthCheck(){
        String resultMessage = "OK";
        return new ResponseEntity<>(resultMessage, HttpStatus.OK);
    }
}
