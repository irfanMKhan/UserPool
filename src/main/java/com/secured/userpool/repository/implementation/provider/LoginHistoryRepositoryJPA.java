package com.secured.userpool.repository.implementation.provider;

import com.secured.userpool.model.dao.LoginHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginHistoryRepositoryJPA extends JpaRepository<LoginHistory, Long> {
}
