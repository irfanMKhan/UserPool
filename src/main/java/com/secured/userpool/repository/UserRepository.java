package com.secured.userpool.repository;

import com.secured.userpool.model.dao.Password;
import com.secured.userpool.model.dao.User;

import java.util.Optional;

public interface UserRepository {

    User save(User user);

    Optional<User> findByUsername(String username);

    Password savePassword(Password password);
}
