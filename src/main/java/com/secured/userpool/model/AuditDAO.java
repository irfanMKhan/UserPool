package com.secured.userpool.model;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Objects;

@Getter
@Setter
@MappedSuperclass
public class AuditDAO {
    private Boolean isActive;

    private Long createdBy;
    private LocalDateTime createdDate;
    private Long createdDateLong;

    private Long updatedBy;
    private LocalDateTime updatedDate;
    private Long updatedDateLong;

    private Boolean isDeleted;
    private Long deletedBy;
    private LocalDateTime deletedDate;
    private Long deletedDateLong;

    public AuditDAO() {
        LocalDateTime now = LocalDateTime.now();
        ZonedDateTime zdt = ZonedDateTime.of(now, ZoneId.systemDefault());
        long longDateTime = zdt.toInstant().toEpochMilli();

        this.setIsActive(true);
        this.setIsDeleted(false);
        this.setCreatedDate(now);
        this.setCreatedDateLong(longDateTime);
    }
}
