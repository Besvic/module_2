package com.epam.esm.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * The type Custom response.
 */
@Data
@NoArgsConstructor
public class CustomResponse {

    private String errorCode;
    private String message;
    /**
     * The Error.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    List<String> error;

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

    /**
     * Instantiates a new Custom response.
     *
     * @param errorCode the error code
     * @param message   the message
     * @param error     the error
     */
    public CustomResponse(String errorCode, String message, List<String> error) {
        this.errorCode = errorCode;
        this.message = message;
        this.error = error;
    }
}
