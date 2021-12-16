package com.epam.esm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class Tag implements Serializable {


    private long id;
    @NotBlank(message = "Name can not be empty")
    @Pattern(regexp = "[a-zA-zа-яА-Я\\s]+", message = "Name may contain only letters. You input: ${validatedValue}")
    private String name;

}
