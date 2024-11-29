package com.snapsanitize.app.repository;

import com.snapsanitize.app.model.Word;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class WordRepositoryTests {

    @Autowired
    private WordRepository repository;

    @Test
    public void WordRepository_Save_ReturnsSavedWord() {
        Word word = Word.builder().word("test").build();

        Word savedWord = repository.save(word);

        Assertions.assertThat(savedWord).isNotNull();
        Assertions.assertThat(savedWord.getWord()).isEqualTo("test");
    }

    @Test
    public void WordRepository_Does_Not_Save_Duplicate_Word() {
        Word word = Word.builder().word("blue").build();
        Assertions.assertThatThrownBy(() -> repository.save(word)).isInstanceOf(DataIntegrityViolationException.class);
    }
    @Test
    public void WordRepository_ReturnsAll() {
        Word wordOne = Word.builder().word("test1").build();
        Word wordTwo = Word.builder().word("test2").build();
        Word wordThree = Word.builder().word("test3").build();
        repository.save(wordOne);
        repository.save(wordTwo);
        repository.save(wordThree);

        List<Word> words = repository.findAll();
        System.out.println(words);

        Assertions.assertThat(words.size()).isGreaterThan(0);
    }
    @Test
    public void WordRepository_UpdatesWord() {
        Word word = Word.builder().word("test").build();
        Word savedWord = repository.save(word);
        savedWord.setWord("updated");
        Word updatedWord = repository.save(savedWord);
        Assertions.assertThat(updatedWord.getWord()).isEqualTo("updated");
        Assertions.assertThat(savedWord.getId()).isEqualTo(updatedWord.getId());
    }

    @Test
    public void WordRepository_FindsById() {
        Word word = Word.builder().word("findMe").build();
        Word savedWord = repository.save(word);

        Optional<Word> foundWord = repository.findById(savedWord.getId());

        Assertions.assertThat(foundWord).isPresent();
        Assertions.assertThat(foundWord.get().getWord()).isEqualTo("findMe");
    }

    @Test
    public void WordRepository_DeletesWord() {
        Word word = Word.builder().word("toDelete").build();
        Word savedWord = repository.save(word);

        repository.deleteById(savedWord.getId());

        Optional<Word> deletedWord = repository.findById(savedWord.getId());
        Assertions.assertThat(deletedWord).isNotPresent();
    }
}
