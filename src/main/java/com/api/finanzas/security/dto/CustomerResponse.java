package com.api.finanzas.security.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponse {
    private Long id;

    private String name;

    private String username;

    private String email;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date created;

    private String dni;

    private String password;
}
