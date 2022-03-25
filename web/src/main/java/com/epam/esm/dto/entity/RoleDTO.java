package com.epam.esm.dto.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * The type Role dto.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO extends RepresentationModel<RoleDTO> implements Serializable {

    private long id;

    @Pattern(regexp = "[a-zA-Zа-яА-Я\\s]+", message = "{incorrect.name} ${validatedValue}")
    private String name;

}
