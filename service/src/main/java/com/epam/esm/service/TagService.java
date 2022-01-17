package com.epam.esm.service;

import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ServiceException;

import java.util.List;
import java.util.Optional;

/**
 * The interface Tag service.
 */
public interface TagService {

    /**
     * Create boolean.
     *
     * @param tag the tag
     * @return the boolean
     * @throws ServiceException the service exception
     */
    long create(Tag tag) throws ServiceException;

    /**
     * Remove by id boolean.
     *
     * @param id the id
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean removeById(long id) throws ServiceException;

    /**
     * Find all list.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Tag> findAll() throws ServiceException;

    /**
     * Find by id optional.
     *
     * @param id the id
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<Tag> findById(long id) throws ServiceException;

}
