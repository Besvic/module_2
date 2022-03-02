package com.epam.esm.dto.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Sign up request dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignUpRequestDTO {

    private long id;
    @NotBlank(message = "{not.blank.name}")
    @Pattern(regexp = "[a-zA-Zа-яА-Я\\s]+", message = "{incorrect.name} ${validatedValue}")
    private String name;
    @NotBlank(message = "{not.blank.lastname}")
    @Pattern(regexp = "[a-zA-Zа-яА-Я\\s]+", message = "{incorrect.lastname} ${validatedValue}")
    private String lastname;
    @NotBlank(message = "{not.blank.surname}")
    @Pattern(regexp = "[a-zA-Zа-яА-Я\\s]+", message = "{incorrect.surname} ${validatedValue}")
    private String surname;
    @Min(value = 0, message = "{incorrect.balance}")
    private BigDecimal balance;
    @Email(message = "{incorrect.email} ${validatedValue}")
    private String email;
    private String password;

    @JsonProperty("roleList")
    private List<String> roleList = new ArrayList<>();

    /**
     * Gets password.
     *
     * @return the password
     */
    @JsonIgnore
    public String getPassword() {
        return password;
    }

    /**
     * Sets password.
     *
     * @param password the password
     */
    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }
}
