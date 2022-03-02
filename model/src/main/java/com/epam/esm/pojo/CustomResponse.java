package com.epam.esm.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * The type Custom response.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomResponse {

    private String errorCode;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> error;

    /**
     * Instantiates a new Custom response.
     *
     * @param errorCode the error code
     * @param message   the message
     */
    public CustomResponse(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }


}
