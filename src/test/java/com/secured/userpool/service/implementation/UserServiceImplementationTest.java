package com.secured.userpool.service.implementation;

import com.secured.userpool.exception.CommonException;
import com.secured.userpool.model.dao.Password;
import com.secured.userpool.model.dao.User;
import com.secured.userpool.model.dto.UserDTO;
import com.secured.userpool.model.payload.UserRequest;
import com.secured.userpool.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplementationTest {
    private UserRepository userRepository;
    private UserServiceImplementation userService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userService = new UserServiceImplementation(userRepository);
    }

    @Test
    void save_whenUsernameDoesNotExist_shouldSaveAndReturnUserDTO() {
        // Arrange
        UserRequest request = new UserRequest();
        request.setUsername("newuser");
        request.setPassword("password123");
        request.setFirstName("New");
        request.setLastName("User");

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setUsername(request.getUsername());
        savedUser.setFirstName(request.getFirstName());
        savedUser.setLastName(request.getLastName());

        when(userRepository.findByUsername("newuser")).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        when(userRepository.savePassword(any(Password.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        UserDTO result = userService.save(request);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("newuser", result.getUsername());
        assertEquals("New", result.getFirstName());
        assertEquals("User", result.getLastName());

        verify(userRepository).findByUsername("newuser");
        verify(userRepository).save(any(User.class));
        verify(userRepository).savePassword(any(Password.class));
    }

    @Test
    void save_whenUsernameAlreadyExists_shouldThrowCommonException() {
        // Arrange
        UserRequest request = new UserRequest();
        request.setUsername("existinguser");
        request.setPassword("password");

        when(userRepository.findByUsername("existinguser"))
                .thenReturn(Optional.of(new User()));

        // Act & Assert
        CommonException exception = assertThrows(CommonException.class, () -> userService.save(request));

        assertEquals("username already present", exception.getMessage());
        verify(userRepository).findByUsername("existinguser");
        verify(userRepository, never()).save(any(User.class));
    }
    
}