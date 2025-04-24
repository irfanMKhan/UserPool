package com.secured.userpool.repository.implementation.provider;

import com.secured.userpool.model.dao.privilege.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepositoryJPA extends JpaRepository<Permission, Long> {

}
