package com.snapsanitize.app.repository;

import com.snapsanitize.app.model.Word;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

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
    public void WordRepository_ReturnsAll() {

    }
}
