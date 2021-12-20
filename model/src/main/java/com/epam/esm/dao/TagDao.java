package com.epam.esm.dao;

import com.epam.esm.dao.exception.DaoException;
import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagDao {

    long create(Tag tag) throws DaoException;
    boolean removeById(long id) throws DaoException;
    Optional<Tag> findById(long id) throws DaoException;
    Optional<Tag> findByName(String name) throws DaoException;
    List<Tag> findAll() throws DaoException;
}
