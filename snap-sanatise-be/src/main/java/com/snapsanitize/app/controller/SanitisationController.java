package com.snapsanitize.app.controller;

import com.snapsanitize.app.service.SanitisationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
@RestController
@RequestMapping("/sanitise")
public class SanitisationController {
    private SanitisationService service;

    public SanitisationController(SanitisationService service) {
        this.service = service;
    }


    @PostMapping
    public ResponseEntity<Map<String, Object>> addWord(@RequestBody String textToSanitise) {
        String result = service.sanitise(textToSanitise);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "String sanitised successfully");
        response.put("result", result);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
