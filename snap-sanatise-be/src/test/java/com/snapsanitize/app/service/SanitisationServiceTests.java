package com.snapsanitize.app.service;

import com.snapsanitize.app.model.Word;
import com.snapsanitize.app.repository.WordRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SanitisationServiceTests {

    @Mock
    private WordRepository wordRepository;

    @InjectMocks
    private SanitisationService sanitisationService;

    private Word word1;
    private Word word2;
    private List<Word> words;

    @BeforeEach
    public void setUp() {
        word1 = Word.builder().word("bad").build();
        word2 = Word.builder().word("ugly").build();
        words = Arrays.asList(word1, word2);
    }

    @Test
    public void sanitise_ShouldReplaceWord_WhenWordIsFound() {
        when(wordRepository.findAll()).thenReturn(Arrays.asList(word1));

        String input = "This is a bad example.";
        String expected = "This is a *** example.";

        String result = sanitisationService.sanitise(input);
        Assertions.assertThat(result).isEqualTo(expected);
    }

    @Test
    public void sanitise_ShouldReplaceMultipleWords_WhenWordsAreFound() {
        when(wordRepository.findAll()).thenReturn(words);

        String input = "This is a bad and ugly example.";
        String expected = "This is a *** and **** example.";

        String result = sanitisationService.sanitise(input);
        Assertions.assertThat(result).isEqualTo(expected);
    }

    @Test
    public void sanitise_ShouldNotChangeString_WhenNoWordsMatch() {
        when(wordRepository.findAll()).thenReturn(Arrays.asList(word1));

        String input = "This is a good example.";
        String expected = "This is a good example.";

        String result = sanitisationService.sanitise(input);
        Assertions.assertThat(result).isEqualTo(expected);
    }

    @Test
    public void sanitise_ShouldReturnEmpty_WhenInputIsEmpty() {
        when(wordRepository.findAll()).thenReturn(Arrays.asList(word1));

        String input = "";
        String expected = "";

        String result = sanitisationService.sanitise(input);
        Assertions.assertThat(result).isEqualTo(expected);
    }

    @Test
    public void sanitise_ShouldReturnInput_WhenNoWordsInRepository() {
        when(wordRepository.findAll()).thenReturn(Arrays.asList());

        String input = "This is a good example.";
        String expected = "This is a good example.";

        String result = sanitisationService.sanitise(input);

        Assertions.assertThat(result).isEqualTo(expected);
    }

    @Test
    public void sanitise_ShouldReplaceWordCaseInsensitively_WhenWordIsFound() {
        when(wordRepository.findAll()).thenReturn(Arrays.asList(word1));

        String input = "This is a BaD example.";
        String expected = "This is a *** example.";

        String result = sanitisationService.sanitise(input);

        Assertions.assertThat(result).isEqualTo(expected);
    }

    @Test
    public void sanitise_ShouldReplaceWordWithSpecialCharacters() {
        Word specialWord = Word.builder().word("café").build();
        when(wordRepository.findAll()).thenReturn(Arrays.asList(specialWord));

        String input = "I love the café at the corner.";
        String expected = "I love the **** at the corner.";

        String result = sanitisationService.sanitise(input);

        Assertions.assertThat(result).isEqualTo(expected);
    }

    @Test
    public void sanitise_ShouldNotReplacePartialWord() {
        Word wordToReplace = Word.builder().word("bad").build();
        when(wordRepository.findAll()).thenReturn(Arrays.asList(wordToReplace));

        String input = "This is a badass example.";
        String expected = "This is a badass example.";

        String result = sanitisationService.sanitise(input);

        Assertions.assertThat(result).isEqualTo(expected);
    }

    @Test
    public void sanitise_ShouldReplaceWordWithPunctuationBoundaries() {
        Word wordWithPunctuation = Word.builder().word("hello").build();
        when(wordRepository.findAll()).thenReturn(Arrays.asList(wordWithPunctuation));

        String input = "Hello, how are you? Hello! I said hello.";
        String expected = "*****, how are you? *****! I said *****.";

        String result = sanitisationService.sanitise(input);

        Assertions.assertThat(result).isEqualTo(expected);
    }

    @Test
    public void sanitise_ShouldNotReplaceHyphenatedWord() {
        Word wordToReplace = Word.builder().word("bad").build();
        when(wordRepository.findAll()).thenReturn(Arrays.asList(wordToReplace));

        String input = "The bad-ass biker rode into town.";
        String expected = "The bad-ass biker rode into town.";

        String result = sanitisationService.sanitise(input);

        Assertions.assertThat(result).isEqualTo(expected);
    }
}
