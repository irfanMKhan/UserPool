package com.secured.userpool.repository;

import com.secured.userpool.model.dao.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Should find user by username")
    void shouldFindUserByUsername() {

        String random = "test-"+String.valueOf(Math.random());

        User user = new User();
        // Change the username
        user.setUsername(random);
        user.setFirstName("John");
        user.setLastName("Doe");

        userRepository.save(user);

        Optional<User> foundUser = userRepository.findByUsername(random);

        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getUsername()).isEqualTo(random);
    }
}