package com.secured.userpool.repository.implementation.provider;

import com.secured.userpool.model.dao.privilege.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolePermissionRepositoryJPA extends JpaRepository<RolePermission, Long> {

}
