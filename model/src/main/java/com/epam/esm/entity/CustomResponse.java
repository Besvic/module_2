package com.epam.esm.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Locale;

@Data
@NoArgsConstructor
//@AllArgsConstructor
public class CustomResponse {

    private String errorCode;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    List<String> error;

    public CustomResponse(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public CustomResponse(String errorCode, String message, List<String> error) {
        this.errorCode = errorCode;
        this.message = message;
        this.error = error;
    }
}
