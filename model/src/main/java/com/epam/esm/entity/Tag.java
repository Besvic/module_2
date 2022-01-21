package com.epam.esm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * The type Tag.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tag implements Serializable {

    public Tag(@NotBlank(message = "Name can not be empty")
               @Pattern(regexp = "[a-zA-zа-яА-Я\\s]+", message = "Name may contain only letters. You input: ${validatedValue}")
                       String name) {
        this.name = name;
    }

    private long id;
    @NotBlank(message = "Name can not be empty")
    @Pattern(regexp = "[a-zA-zа-яА-Я\\s]+", message = "Name may contain only letters. You input: ${validatedValue}")
    private String name;

}
