package com.snapsanitize.app.controller;

import com.snapsanitize.app.common.SanitiseApiResponse;
import com.snapsanitize.app.model.Word;
import com.snapsanitize.app.service.WordService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/word")
public class WordController {
    private final WordService wordService;

    public WordController(WordService wordService) {
        this.wordService = wordService;
    }

    @Operation(summary = "Get word by ID", description = "Fetches a word based on id, if it exists")
    @GetMapping("/{id}")
    public ResponseEntity<SanitiseApiResponse<Word>> getWordById(@PathVariable Long id) {
        Word word = wordService.getWordById(id);
        return ResponseEntity.ok(new SanitiseApiResponse<>("Word found", word));
    }

    @Operation(summary = "Get all words", description = "Returns a list of all words")
    @GetMapping("/list")
    public ResponseEntity<SanitiseApiResponse<List<Word>>> getAllWords() {
        List<Word> words = wordService.getAllWords();
        return ResponseEntity.ok(new SanitiseApiResponse<>("Words retrieved successfully", words));
    }

    @Operation(summary = "Add word", description = "Create a new word")
    @PostMapping
    public ResponseEntity<SanitiseApiResponse<Word>> addWord(@RequestBody Word word) {
        Word savedWord = wordService.addWord(word);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new SanitiseApiResponse<>("Word added successfully", savedWord));
    }

    @Operation(summary = "Update", description = "Update an existing word")
    @PutMapping("/{id}")
    public ResponseEntity<SanitiseApiResponse<Word>> updateWord(@PathVariable Long id, @RequestBody Word word) {
        Word updatedWord = wordService.updateWord(id, word);
        return ResponseEntity.ok(new SanitiseApiResponse<>("Word updated successfully", updatedWord));
    }

    @Operation(summary = "Delete a word", description = "Delete an existing word")
    @DeleteMapping("/{id}")
    public ResponseEntity<SanitiseApiResponse<Void>> deleteWord(@PathVariable Long id) {
        wordService.deleteWord(id);
        return ResponseEntity.ok(new SanitiseApiResponse<>("Word deleted successfully", null));
    }
}

