package com.epam.esm.security;

import com.epam.esm.pojo.CustomResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The type Auth entry point jwt.
 */
@Component
@Log4j2
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {

        String loginUrl = "http://localhost:8080/auth/login";

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        log.warn(authException.getMessage());

        CustomResponse customResponse = new CustomResponse();
        customResponse.setErrorCode("40102");
        customResponse.setMessage(loginUrl);
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), customResponse);
    }
}
