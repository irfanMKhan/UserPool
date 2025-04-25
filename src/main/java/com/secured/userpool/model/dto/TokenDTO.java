package com.secured.userpool.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TokenDTO {
    private String subject;
    private String message;
    private Boolean status;
}
