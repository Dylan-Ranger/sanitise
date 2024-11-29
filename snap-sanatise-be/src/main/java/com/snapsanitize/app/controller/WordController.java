package com.snapsanitize.app.controller;

import com.snapsanitize.app.common.ApiResponse;
import com.snapsanitize.app.model.Word;
import com.snapsanitize.app.service.WordService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Word>> getWordById(@PathVariable Long id) {
        Word word = wordService.getWordById(id);
        return ResponseEntity.ok(new ApiResponse<>("Word found", word));
    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<Word>>> getAllWords() {
        List<Word> words = wordService.getAllWords();
        return ResponseEntity.ok(new ApiResponse<>("Words retrieved successfully", words));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Word>> addWord(@RequestBody Word word) {
        Word savedWord = wordService.addWord(word);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("Word added successfully", savedWord));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Word>> updateWord(@PathVariable Long id,@RequestBody Word word) {
        Word updatedWord = wordService.updateWord(id, word);
        return ResponseEntity.ok(new ApiResponse<>("Word updated successfully", updatedWord));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteWord(@PathVariable Long id) {
        wordService.deleteWord(id);
        return ResponseEntity.ok(new ApiResponse<>("Word deleted successfully", null));
    }
}

