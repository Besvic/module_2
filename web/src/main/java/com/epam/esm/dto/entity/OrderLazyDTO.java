package com.epam.esm.dto.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.PastOrPresent;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * The type Order lazy dto for user. Missing giftCertificates and tags fields .
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderLazyDTO extends RepresentationModel<OrderLazyDTO> implements Serializable {

    private long id;

    @Valid
    private UserDTO userDTO = new UserDTO();

    @Min(value = 0, message = "{incorrect.balance}")
    private BigDecimal cost;

    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    @PastOrPresent(message = "{past.or.present.date} ${validatedValue}")
    private LocalDateTime dateTime;

    /**
     * Gets user dto.
     * For ignore serialize
     *
     * @return the user dto
     */
    @JsonIgnore
    public UserDTO getUserDTO() {
        return userDTO;
    }

    /**
     * Sets user dto.
     *
     * @param userDTO the user dto
     */
    @JsonProperty
    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }
}
