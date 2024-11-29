package com.snapsanitize.app.controller;


import com.snapsanitize.app.service.SanitisationService;
import org.hamcrest.CoreMatchers;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = SanitisationController.class )
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class SanitisationControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private SanitisationService service;

    @Test
    public void SanitisationController_SanitiseString_Returns_Sanitised_Result() throws Exception {
        String input = "Hi there mary blue";
        when(service.sanitise(input)).thenReturn("Hi there mary ***");

        ResultActions response = mockMvc.perform(post("/sanitise")
                .contentType(MediaType.APPLICATION_JSON)
                .content(input));

        response.andExpect(MockMvcResultMatchers.status().is(201))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result", CoreMatchers.is("Hi there mary ***")));
    }

    @Test
    public void SanitisationController_SanitiseString_Returns_InvalidInput() throws Exception {
        String input = "\"\"";

        ResultActions response = mockMvc.perform(post("/sanitise")
                .contentType(MediaType.APPLICATION_JSON)
                .content(input));

        response.andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", CoreMatchers.is("Input must not be empty and must contain at least one word.")));
    }

}
