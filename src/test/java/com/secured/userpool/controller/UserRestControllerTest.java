package com.secured.userpool.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.secured.userpool.model.dto.UserDTO;
import com.secured.userpool.model.payload.UserRequest;
import com.secured.userpool.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserRestController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserRestControllerTest {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;



    @Test
    @DisplayName("POST /api/v1/user - Create User")
    void saveUser_shouldReturnCreatedUser() throws Exception {

        UserRequest userRequest = new UserRequest();
        userRequest.setUsername("john");
        userRequest.setPassword("password");
        userRequest.setFirstName("John");
        userRequest.setLastName("Doe");

        UserDTO mockResponse = new UserDTO();
        mockResponse.setId(1L);
        mockResponse.setUsername("john");
        mockResponse.setFirstName("John");
        mockResponse.setLastName("Doe");

        when(userService.save(Mockito.any(UserRequest.class))).thenReturn(mockResponse);

        mockMvc.perform(post("/api/v1/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.username").value("john"))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"));
    }

}