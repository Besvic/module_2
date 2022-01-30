package com.epam.esm.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
@Entity(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    private List<Order> orderList = new ArrayList<>();

    protected User(){

    }
}
