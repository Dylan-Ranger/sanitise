package com.snapsanitize.app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Entity
@Table(name = "words")
@Builder
public class Word {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "word", nullable = false, unique = true)
    @NotBlank(message = "Word cannot be empty")
    @Pattern(regexp = "^[a-zA-Z]+([-a-zA-Z0-9#]*)\\+{0,2}$|^[a-zA-Z0-9]+([#]+[a-zA-Z0-9]*)+$\n", message = "That word is not allowed.")
    private String word;

    public Word() {
    }

    public Word(Long id, String word) {
        this.id = id;
        this.word = word;
    }

    public String getWord() {
        return word;
    }

    public long getId() {
        return id;
    }

    public void setWord(String word) {
        this.word = word;
    }

    @Override
    public String toString() {
        return "Word{" +
                "id=" + id +
                ", word='" + word + '\'' +
                '}';
    }
}
