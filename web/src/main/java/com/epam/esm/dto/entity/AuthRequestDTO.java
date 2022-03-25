package com.epam.esm.dto.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Auth request dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequestDTO {

    private String email;
    private String password;
}
