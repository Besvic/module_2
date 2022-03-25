package com.epam.esm.dto.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * The type User dto.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO extends RepresentationModel<UserDTO> implements Serializable {

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

    @Valid
    @JsonProperty("orderList")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<OrderLazyDTO> orderLazyDTOList = new ArrayList<>();
    @JsonProperty("roleList")
    private List<RoleDTO> roleDTOList = new ArrayList<>();

    /**
     * Gets password.
     * For ignore serialize
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
