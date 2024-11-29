package com.snapsanitize.app.controller;

import com.snapsanitize.app.service.SanitisationService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(
            summary = "Sanitise a string",
            description = "Sanitises the input string by removing unwanted words. Words can be managed via the /word end point."
    )
    @PostMapping
    public ResponseEntity<Map<String, Object>> sanitiseString(@RequestBody String textToSanitise) {
        if (textToSanitise == null || textToSanitise.trim().isEmpty() || !textToSanitise.matches(".*\\w.*")) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Invalid input");
            errorResponse.put("message", "Input must not be empty and must contain at least one word.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        String result = service.sanitise(textToSanitise);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "String sanitised successfully");
        response.put("result", result);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
