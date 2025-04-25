package com.secured.userpool.model.dao;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Data
@Entity
@Table(name = "iam_login_history")
@AllArgsConstructor
public class LoginHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_generator_log_history")
    @SequenceGenerator(name = "id_generator_log_history", sequenceName = "id_generator_log_history", initialValue = 1, allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private Long createdBy;
    private LocalDateTime createdDate;
    private Long createdDateLong;
    private Boolean attempt;

    public LoginHistory() {
        LocalDateTime now = LocalDateTime.now();
        ZonedDateTime zdt = ZonedDateTime.of(now, ZoneId.systemDefault());
        long longDateTime = zdt.toInstant().toEpochMilli();
        this.setCreatedDate(now);
        this.setCreatedDateLong(longDateTime);
    }

}
