package com.epam.esm.pojo.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Sign up request.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignUpRequest {

    private long id;
    private String name;
    private String lastname;
    private String surname;
    private BigDecimal balance;
    private String email;
    private String password;
    private List<String> roleList = new ArrayList<>();

}
