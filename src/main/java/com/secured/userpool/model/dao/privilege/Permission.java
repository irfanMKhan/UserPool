package com.secured.userpool.model.dao.privilege;

import com.secured.userpool.model.AuditDAO;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "iam_permission")
public class Permission extends AuditDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_generator_permission")
    @SequenceGenerator(name = "id_generator_permission", sequenceName = "id_generator_permission", initialValue = 1, allocationSize = 1)
    private Long id;

    private String name;
    private String description;
    private String module;
    private String controller;
    private String ip;
    private Long port;

    @Column(name = "partial_url")
    private String partialURL;

    @Column(name = "full_url")
    private String fullURL;

    private String actionType;

    @OneToMany(mappedBy = "permission", fetch = FetchType.LAZY, targetEntity = RolePermission.class)
    private List<RolePermission> permission;

}
