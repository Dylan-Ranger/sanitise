package com.snapsanitize.app.service;

import com.snapsanitize.app.model.Word;
import com.snapsanitize.app.repository.WordRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.List;
import java.util.Arrays;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WordServiceTests {
    @Mock
    private WordRepository repository;
    @InjectMocks
    private WordService service;

    @Test
    public void WordService_Creates_Word() {
        Word word = Word.builder().word("test").build();
        when(repository.save(word)).thenReturn(word);
        Word savedWord = service.addWord(word);

        Assertions.assertThat(savedWord).isNotNull();
        Assertions.assertThat(savedWord.getWord()).isEqualTo("test");
    }

    @Test
    public void getWordById_ShouldReturnWord_WhenWordExists() {
        Long wordId = 1L;
        Word word = Word.builder().id(wordId).word("test").build();
        when(repository.findById(wordId)).thenReturn(Optional.of(word));

        Word foundWord = service.getWordById(wordId);

        Assertions.assertThat(foundWord).isNotNull();
        Assertions.assertThat(foundWord.getId()).isEqualTo(wordId);
        Assertions.assertThat(foundWord.getWord()).isEqualTo("test");
    }

    @Test
    public void getWordById_ShouldThrowException_WhenWordDoesNotExist() {
        Long wordId = 1L;
        when(repository.findById(wordId)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> service.getWordById(wordId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Word not found");
    }

    @Test
    public void getAllWords_ShouldReturnListOfWords() {
        Word word1 = Word.builder().word("test1").build();
        Word word2 = Word.builder().word("test2").build();
        List<Word> words = Arrays.asList(word1, word2);

        when(repository.findAll()).thenReturn(words);

        List<Word> foundWords = service.getAllWords();

        Assertions.assertThat(foundWords).isNotEmpty();
        Assertions.assertThat(foundWords.size()).isEqualTo(2);
    }

    @Test
    public void updateWord_ShouldUpdateWord_WhenWordExists() {
        Long wordId = 1L;
        Word existingWord = Word.builder().id(wordId).word("test").build();
        Word updatedWord = Word.builder().id(wordId).word("updated").build();

        when(repository.findById(wordId)).thenReturn(Optional.of(existingWord));
        when(repository.save(existingWord)).thenReturn(updatedWord);

        Word result = service.updateWord(wordId, updatedWord);

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getWord()).isEqualTo("updated");
        verify(repository, times(1)).save(existingWord);
    }

    @Test
    public void updateWord_ShouldThrowException_WhenWordDoesNotExist() {
        Long wordId = 1L;
        Word updatedWord = Word.builder().id(wordId).word("updated").build();

        when(repository.findById(wordId)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> service.updateWord(wordId, updatedWord))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Word not found");

        verify(repository, times(0)).save(updatedWord);
    }

    @Test
    public void deleteWord_ShouldDeleteWord_WhenWordExists() {
        Long wordId = 1L;
        Word word = Word.builder().id(wordId).word("test").build();

        when(repository.existsById(wordId)).thenReturn(true);

        service.deleteWord(wordId);

        verify(repository, times(1)).deleteById(wordId);
    }

    @Test
    public void deleteWord_ShouldThrowException_WhenWordDoesNotExist() {
        Long wordId = 1L;
        when(repository.existsById(wordId)).thenReturn(false);

        Assertions.assertThatThrownBy(() -> service.deleteWord(wordId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Word not found");

        verify(repository, times(0)).deleteById(wordId);
    }
}
