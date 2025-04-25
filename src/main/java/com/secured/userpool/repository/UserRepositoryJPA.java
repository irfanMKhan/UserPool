package com.secured.userpool.repository;

import com.secured.userpool.model.dao.Password;
import com.secured.userpool.model.dao.User;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryJPA {

    User save(User user);

    Optional<User> findByUsername(String username);

    Password savePassword(Password password);

    List<User> get(String sort, String order);

}
