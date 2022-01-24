package com.epam.esm.dao;

import com.epam.esm.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    long create(User user);
    List<User> findAll();
    Optional<User> findById(long id);
}
