package com.secured.userpool.model.dao.privilege;


import com.secured.userpool.model.AuditDAO;
import com.secured.userpool.model.dao.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "iam_role")
public class Role extends AuditDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_generator_role")
    @SequenceGenerator(name = "id_generator_role", sequenceName = "id_generator_role", initialValue = 1, allocationSize = 1)
    private Long id;

    private String name;
    private String description;
    private Long priority;

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY, targetEntity = User.class)
    private List<User> user;

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY, targetEntity = RolePermission.class)
    private List<RolePermission> permission;

}
