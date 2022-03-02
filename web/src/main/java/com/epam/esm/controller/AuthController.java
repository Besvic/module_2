package com.epam.esm.controller;

import com.epam.esm.dto.converter.SignUpRequestConverter;
import com.epam.esm.dto.converter.UserConverter;
import com.epam.esm.dto.entity.SignUpRequestDTO;
import com.epam.esm.dto.entity.UserDTO;
import com.epam.esm.entity.User;
import com.epam.esm.exception.ControllerException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.pojo.security.JwtResponse;
import com.epam.esm.pojo.security.LoginRequest;
import com.epam.esm.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * The type Auth controller.
 */
@RestController
@RequestMapping(value = "/auth",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
//@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {

    private final AuthService authService;
    private final UserConverter userConverter;
    private final SignUpRequestConverter requestConverter;

    /**
     * Instantiates a new Auth controller.
     *
     * @param authService      the auth service
     * @param userConverter    the user converter
     * @param requestConverter the request converter
     */
    public AuthController(AuthService authService,
                          UserConverter userConverter,
                          SignUpRequestConverter requestConverter) {
        this.authService = authService;
        this.userConverter = userConverter;
        this.requestConverter = requestConverter;
    }

    /**
     * Auth user response entity.
     *
     * @param loginRequest the login request
     * @return the response entity
     */
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> authUser(@RequestBody LoginRequest loginRequest) {
        JwtResponse jwtResponse = authService.authentication(loginRequest);
        return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
    }


    /**
     * Register user response entity.
     *
     * @param signUpRequestDTO the sign up request dto
     * @return the response entity
     * @throws ControllerException the controller exception
     */
    @PostMapping("/signup")
    public ResponseEntity<UserDTO> registerUser(@RequestBody @Valid SignUpRequestDTO signUpRequestDTO) throws ControllerException {
        User user;
        UserDTO userDTO;
        try {
            user = authService.registration(requestConverter
                    .convertToRequest(signUpRequestDTO));
            userDTO = userConverter.convertToUserDTO(user);
            return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }
    }
}
