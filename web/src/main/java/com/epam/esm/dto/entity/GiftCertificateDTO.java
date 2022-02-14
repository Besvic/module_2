package com.epam.esm.dto.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Gift certificate dto.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class GiftCertificateDTO extends RepresentationModel<GiftCertificateDTO> implements Serializable {

    private long id;
    @NotBlank(message = "{not.blank.name}")
    @Pattern(regexp = "[a-zA-zа-яА-Я\\s]+", message = "{incorrect.name} ${validatedValue}")
    private String name;
    @NotBlank(message = "{not.blank.description}")
    @Pattern(regexp = "[a-zA-zа-яА-Я\\s]+", message = "{incorrect.description} ${validatedValue}")
    private String description;
    @Min(value = 0, message = "{incorrect.cost}")
    private BigDecimal price;
    @Min(value = 0, message = "{incorrect.duration} ${validatedValue}")
    private int duration;
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime createDate;
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime lastUpdateDate;

    @Valid
    @JsonProperty("tagList")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<TagDTO> tagDTOList = new ArrayList<>();
}
