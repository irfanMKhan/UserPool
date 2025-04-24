package com.secured.userpool.repository.implementation.provider;

import com.secured.userpool.model.dao.Password;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordRepositoryJPA extends JpaRepository<Password, Long> {
}
