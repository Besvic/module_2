package com.epam.esm.dto.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * The type Tag dto.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class TagDTO extends RepresentationModel<TagDTO> implements Serializable {

    private long id;
    @NotBlank(message = "{not.blank.name}")
    @Pattern(regexp = "[a-zA-zа-яА-Я\\s]+", message = "{incorrect.name} ${validatedValue}")
    private String name;
}
