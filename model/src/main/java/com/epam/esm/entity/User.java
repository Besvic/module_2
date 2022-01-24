package com.epam.esm.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements Serializable {

    private long id;
    @Pattern(regexp = "[a-zA-Zа-яА-Я\\s]+", message = "Name may contain only letters. You input: ${validatedValue}")
    private String name;
    @Pattern(regexp = "[a-zA-Zа-яА-Я\\s]+", message = "Name may contain only letters. You input: ${validatedValue}")
    private String lastname;
    @Pattern(regexp = "[a-zA-Zа-яА-Я\\s]+", message = "Name may contain only letters. You input: ${validatedValue}")
    private String surname;
    @Min(0)
    private BigDecimal balance;
    @Email
    private String email;
    private String password;
}
