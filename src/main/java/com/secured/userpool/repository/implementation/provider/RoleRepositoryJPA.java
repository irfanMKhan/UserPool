package com.secured.userpool.repository.implementation.provider;

import com.secured.userpool.model.dao.privilege.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepositoryJPA extends JpaRepository<Role, Long> {

}
