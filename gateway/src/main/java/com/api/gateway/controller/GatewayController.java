package com.api.gateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GatewayController {
    @GetMapping("/fallback/employee")
    public ResponseEntity<String>  employeeServiceFallback() {
        return new ResponseEntity<>("Employee Service is unavailable. Please try again later."
                , HttpStatus.SERVICE_UNAVAILABLE);
    }

    @GetMapping("/fallback/project")
    public ResponseEntity<String>  projectServiceFallback() {
        return new ResponseEntity<>("Project Service is unavailable. Please try again later."
                , HttpStatus.SERVICE_UNAVAILABLE);
    }

}


