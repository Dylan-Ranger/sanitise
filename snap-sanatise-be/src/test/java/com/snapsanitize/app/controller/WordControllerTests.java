package com.snapsanitize.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.snapsanitize.app.model.Word;
import com.snapsanitize.app.service.WordService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;


@WebMvcTest(controllers = WordController.class)
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class WordControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private WordService wordService;
    @Autowired
    private ObjectMapper objectMapper;
    private Word word;

    @BeforeEach
    public void init() {
        word = Word.builder().word("test").build();
    }

    @Test
    public void WordController_CreateWord_ReturnedCreated() throws Exception {
        given(wordService.addWord(ArgumentMatchers.any()))
                .willAnswer((invocation -> invocation.getArgument(0)));

        ResultActions response = mockMvc.perform(post("/word")
                                        .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(word)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.word", CoreMatchers.is(word.getWord())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", CoreMatchers.is("Word added successfully")));
    }

    @Test
    public void WordController_GetAllWords_ReturnsWords() throws Exception {
        List<Word> words = new ArrayList<Word>();
        words.add(new Word(1L, "one"));
        words.add(new Word(2L, "two"));
        words.add(new Word(3L, "three"));

        when(wordService.getAllWords()).thenReturn(words);

        ResultActions response = mockMvc.perform(get("/word/list"));
        response.andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.size()", CoreMatchers.is(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", CoreMatchers.is("Words retrieved successfully")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].id", CoreMatchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].id", CoreMatchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[2].id", CoreMatchers.is(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].word", CoreMatchers.is("one")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].word", CoreMatchers.is("two")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[2].word", CoreMatchers.is("three")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", CoreMatchers.is("Words retrieved successfully")));
    }

    @Test
    public void WordController_GetWord_Returns_Word() throws Exception {
        when(wordService.getWordById(0L)).thenReturn(word);

        ResultActions response = mockMvc.perform(get("/word/0")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id", CoreMatchers.is(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.word", CoreMatchers.is("test")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", CoreMatchers.is("Word found")));
    }

    @Test
    public void WordController_GetWord_Returns_Failure_On_Word_Not_Found() throws Exception {
        when(wordService.getWordById(5L)).thenThrow(new IllegalArgumentException("Word not found"));

        ResultActions response = mockMvc.perform(get("/word/5")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", CoreMatchers.is("Word not found")));
    }


    @Test
    public void WordController_updateWord_Succeeds() throws Exception {
        when(wordService.updateWord(0L, word)).thenReturn(word);

        ResultActions response = mockMvc.perform(put("/word/0")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(word)));

        response.andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    public void WordController_deleteWord_Succeeds() throws Exception {
        doNothing().when(wordService).deleteWord(0L);

        ResultActions response = mockMvc.perform(delete("/word/0")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    public void WordController_deleteWord_HandlesNoId() throws Exception {
        doThrow(new IllegalArgumentException("Word not found"))
                .when(wordService).deleteWord(555L);

        ResultActions response = mockMvc.perform(delete("/word/555")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", CoreMatchers.is("Word not found")));
    }
}
