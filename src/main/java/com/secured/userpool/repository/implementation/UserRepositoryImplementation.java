package com.secured.userpool.repository.implementation;

import com.secured.userpool.model.dao.Password;
import com.secured.userpool.model.dao.User;
import com.secured.userpool.repository.UserRepository;
import com.secured.userpool.repository.implementation.provider.PasswordRepositoryJPA;
import com.secured.userpool.repository.implementation.provider.UserRepositoryJPA;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImplementation implements UserRepository {

    private final UserRepositoryJPA userRepositoryJPA;
    private final PasswordRepositoryJPA passwordRepositoryJPA;

    public Optional<User> findByUsername(String username) {
        return userRepositoryJPA.findByUsername(username);
    }

    public User save(User user) {
        return userRepositoryJPA.save(user);
    }

    public Password savePassword(Password password) {
        return passwordRepositoryJPA.save(password);
    }

}
