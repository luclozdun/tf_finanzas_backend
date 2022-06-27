package com.api.finanzas.security.dto;

import lombok.Data;

@Data
public class AuthenticateRequest {
    private Long type;
    private String login;
    private String password;
}
