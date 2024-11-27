package com.snapsanitize.app.controller;

import com.snapsanitize.app.model.Word;
import com.snapsanitize.app.repository.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/word") // Base URL for all endpoints
public class WordController {


    private final WordRepository wordRepository;

    public WordController(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Word> getWordById(@PathVariable Long id) {
        Optional<Word> word = wordRepository.findById(id);
        return word.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .build());
    }

    @GetMapping("/list")
    public List<Word> getAllWords() {
        return wordRepository.findAll();
    }


    @PostMapping
    public ResponseEntity<Map<String, Object>> addWord(@RequestBody Word word) {
        Optional<Word> existingWord = wordRepository.findByWord(word.getWord());
        if (existingWord.isPresent()) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "That word already exists!");
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(errorResponse);
        }

        Word savedWord = wordRepository.save(word);

        
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Word added successfully");
        response.put("word", savedWord);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @PutMapping("/{id}")
    public ResponseEntity<String> updateWord(
            @PathVariable Long id, @RequestBody Word word) {
        Optional<Word> existingWord = wordRepository.findById(id);
        if (existingWord.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Word not found");
        }

        Word updatedWord = existingWord.get();
        updatedWord.setWord(word.getWord());
        wordRepository.save(updatedWord);

        return ResponseEntity.ok("Word updated successfully");
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteWord(@PathVariable Long id) {
        if (!wordRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Word not found");
        }

        wordRepository.deleteById(id);
        return ResponseEntity.ok("Word deleted successfully");
    }
}

