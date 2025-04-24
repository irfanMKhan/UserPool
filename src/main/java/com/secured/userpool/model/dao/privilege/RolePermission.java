package com.secured.userpool.model.dao.privilege;

import com.secured.userpool.model.AuditDAO;
import jakarta.persistence.*;
import lombok.*;


@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "iam_role_Permission")
public class RolePermission extends AuditDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_generator_role_permission")
    @SequenceGenerator(name = "id_generator_role_permission", sequenceName = "id_generator_role_permission", initialValue = 1, allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, targetEntity = Role.class)
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, targetEntity = Permission.class)
    @JoinColumn(name = "permission_id", referencedColumnName = "id", nullable = false)
    private Permission permission;

}
