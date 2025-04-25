package com.secured.userpool.model.dao;

import com.secured.userpool.model.AuditDAO;
import com.secured.userpool.model.dao.privilege.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLOrder;
import org.hibernate.annotations.SQLRestriction;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "iam_user")
@NoArgsConstructor
@AllArgsConstructor
public class User extends AuditDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_generator_user")
    @SequenceGenerator(name = "id_generator_user", sequenceName = "id_generator_user", initialValue = 1, allocationSize = 1)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;
    private String firstName;
    private String lastName;
    private String occupation;
    private Boolean isTokenExpired;
    private Boolean isLocked;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Role.class)
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, targetEntity = Password.class)
    @SQLRestriction("is_active = true")
    @SQLOrder("created_date desc")
    private List<Password> password;

    public Password getLastPassword() {
        return this.password.get(0);
    }

}
