package com.snapsanitize.app.service;

import com.snapsanitize.app.model.Word;
import com.snapsanitize.app.repository.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class SanitisationService {
    private final WordRepository repository;

    @Autowired
    public SanitisationService(WordRepository repository) {
        this.repository = repository;
    }

    public String sanitise(String userInput) {
        List<Word> words = repository.findAll();
        String sanitisedString = userInput;
        for (Word word : words) {
            String token= "*".repeat(word.getWord().length());
            String regex = "(?i)\\b\\Q" + word.getWord() + "\\E\\b(?=[.,?!\\s]*|$)(?!\\s*-|')";
            if (stringContainsSpecialCharacters(word.getWord())) {
                regex = word.getWord();
            }
            sanitisedString = sanitisedString.replaceAll(regex, token);
        }
        return sanitisedString;
    }
    private boolean stringContainsSpecialCharacters(String string) {
        return Pattern.compile(".*[^a-zA-Z0-9].*")
                      .matcher(string)
                      .matches();
    }
}
