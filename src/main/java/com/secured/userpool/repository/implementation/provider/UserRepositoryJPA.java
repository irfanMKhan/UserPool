package com.secured.userpool.repository.implementation.provider;

import com.secured.userpool.model.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepositoryJPA extends JpaRepository<User, Long> {
    
}
