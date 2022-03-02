package com.epam.esm.service;

import com.epam.esm.entity.User;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.pojo.security.JwtResponse;
import com.epam.esm.pojo.security.LoginRequest;
import com.epam.esm.pojo.security.SignUpRequest;

/**
 * The interface Auth service.
 */
public interface AuthService {

    /**
     * Registration user.
     *
     * @param signUpRequest the sign up request
     * @return the user
     * @throws ServiceException the service exception
     */
    User registration(SignUpRequest signUpRequest) throws ServiceException;

    /**
     * Authentication jwt response.
     *
     * @param loginRequest the login request
     * @return the jwt response
     */
    JwtResponse authentication(LoginRequest loginRequest);
}
