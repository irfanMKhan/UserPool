package com.secured.userpool.repository.implementation.provider;

import com.secured.userpool.model.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepositoryJPA extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

}
