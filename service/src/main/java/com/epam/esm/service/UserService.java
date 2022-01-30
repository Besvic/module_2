package com.epam.esm.service;

import com.epam.esm.entity.User;
import com.epam.esm.exception.ServiceException;

import java.util.List;

public interface UserService {

    User create(User user) throws ServiceException;
    User findById(long id) throws ServiceException;
    List<User> findAll() throws ServiceException;
    List<User> findAllByName(String name) throws ServiceException;
    long deleteById(long id) throws ServiceException;
}
