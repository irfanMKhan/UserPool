package com.secured.userpool.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.secured.userpool.model.dto.UserDTO;
import com.secured.userpool.model.payload.UserRequest;
import com.secured.userpool.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserRestController.class)
class UserRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createUser_ShouldReturn201_WhenUserIsValid() throws Exception {

        UserRequest userRequest = new UserRequest();
        userRequest.setFirstName("john");
        userRequest.setUsername("john");
        userRequest.setLastName("doe");
        userRequest.setPassword("123");

        UserDTO mockResponse = new UserDTO();

        when(userService.save(any(UserRequest.class))).thenReturn(mockResponse);

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isCreated());

        verify(userService, times(1)).save(any(UserRequest.class));
    }
}