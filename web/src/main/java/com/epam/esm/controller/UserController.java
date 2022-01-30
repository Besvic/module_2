package com.epam.esm.controller;

import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.exception.ControllerException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/users",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class UserController {

    private final UserService userService;
    private final OrderService orderService;

    public UserController(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
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

    @GetMapping("/search/name")
    public ResponseEntity<List<User>> findAllByName(@RequestParam(value = "name") String name) throws ControllerException {
        try {
            List<User> userList = userService.findAllByName(name);
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

    @GetMapping(value = "/{userId}/orders")
    public ResponseEntity<List<Order>> findAllOrderByUserId(@PathVariable(name = "userId") Long userId) throws ControllerException {
        try {
            List<Order> orderList = orderService.findAllByUserId(userId);
            return new ResponseEntity<>(orderList, HttpStatus.OK);
        } catch (ServiceException e) {
           throw new ControllerException(e);
        }
    }

    @PostMapping(value = "/{userId}/orders")
    public ResponseEntity<Order> createOrder(@Valid @RequestBody Order order,
                                             @PathVariable(name = "userId") Long userId) throws ControllerException {
        try {
            order.getUser().setId(userId);
            Order createOrder = orderService.create(order);
            return new ResponseEntity<>(createOrder, HttpStatus.CREATED);
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }
    }

    @DeleteMapping(value = "/{userId}")
    public ResponseEntity<Long> deleteById(@PathVariable("userId") Long userId) throws ControllerException {
        try {
            return new ResponseEntity<>(userService.deleteById(userId), HttpStatus.OK);
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }
    }


}
