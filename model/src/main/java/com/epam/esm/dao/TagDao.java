package com.epam.esm.dao;

import com.epam.esm.exception.DaoException;
import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Optional;

/**
 * The interface Tag dao.
 */
public interface TagDao {

    /**
     * Create long.
     *
     * @param tag the tag
     * @return the long
     * @throws DaoException the dao exception
     */
    long create(Tag tag) throws DaoException;

    /**
     * Remove by id boolean.
     *
     * @param id the id
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean removeById(long id) throws DaoException;

    /**
     * Find by id optional.
     *
     * @param id the id
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<Tag> findById(long id) throws DaoException;

    /**
     * Find by name optional.
     *
     * @param name the name
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<Tag> findByName(String name) throws DaoException;

    /**
     * Find all list.
     *
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Tag> findAll() throws DaoException;
}
