package com.epam.esm.controller;

import com.epam.esm.entity.User;
import com.epam.esm.exception.ControllerException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/users",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<List<User>> findAll() throws ControllerException {
        try {
            List<User> userList = userService.findAll();
            return new ResponseEntity<>(userList, HttpStatus.OK);
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<User> findById(@PathVariable long id) throws ControllerException {
        try {
            User user = userService.findById(id);
                return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }
    }

    @PostMapping()
    public ResponseEntity<User> create(@RequestBody @Valid User user) throws ControllerException {
        try {
            user = userService.create(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }
    }
}
