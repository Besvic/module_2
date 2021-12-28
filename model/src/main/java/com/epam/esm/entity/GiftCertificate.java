package com.epam.esm.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * The type Gift certificate.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GiftCertificate implements Serializable {

    private long id;
    @NotBlank(message = "Name can not be empty")
    @Pattern(regexp = "[a-zA-zа-яА-Я\\s]+", message = "Name may contain only letters. You input: ${validatedValue}")
    private String name;
    @NotBlank(message = "Description can not be empty")
    @Pattern(regexp = "[a-zA-zа-яА-Я\\s]+", message = "Description may contain only letters. You input: ${validatedValue}")
    private String description;
    @Min(value = 0, message = "Price can not be less then 0. You input: ${validatedValue}")
    private double price;
    @Min(value = 1, message = "Duration can not be less then 1. You input: ${validatedValue}")
    private int duration;
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime createDate;
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime lastUpdateDate;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Valid
    private List<Tag> tagList;

}
