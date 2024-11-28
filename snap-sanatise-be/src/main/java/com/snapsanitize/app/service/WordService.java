package com.snapsanitize.app.service;

import com.snapsanitize.app.model.Word;
import com.snapsanitize.app.repository.WordRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class WordService {
    private final WordRepository wordRepository;

    public WordService(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    public Word getWordById(Long id) {
        return wordRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Word not found"));
    }

    public List<Word> getAllWords() {
        return wordRepository.findAll();
    }

    public Word addWord(Word word) {
        if (wordRepository.findByWord(word.getWord()).isPresent()) {
            throw new IllegalArgumentException("That word already exists!");
        }
        return wordRepository.save(word);
    }

    public Word updateWord(Long id, Word word) {
        Word existingWord = wordRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Word not found"));

        existingWord.setWord(word.getWord());
        return wordRepository.save(existingWord);
    }

    public void deleteWord(Long id) {
        if (!wordRepository.existsById(id)) {
            throw new IllegalArgumentException("Word not found");
        }
        wordRepository.deleteById(id);
    }
}
