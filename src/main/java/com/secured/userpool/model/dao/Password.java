package com.secured.userpool.model.dao;

import com.secured.userpool.model.AuditDAO;
import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Builder
@Table(name = "iam_password")
@NoArgsConstructor
@AllArgsConstructor
public class Password extends AuditDAO {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_generator_password")
    @SequenceGenerator(name = "id_generator_password", sequenceName = "id_generator_password", initialValue = 1, allocationSize = 1)
    private Long id;

    private String hashed;

    private Long failedAttempt;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
