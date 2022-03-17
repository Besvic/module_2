package com.epam.esm.service;

import com.epam.esm.entity.User;
import com.epam.esm.exception.ServiceException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * The interface User service.
 */
public interface UserService {

    /**
     * Create user.
     *
     * @param user the user
     * @return the user
     * @throws ServiceException the service exception
     */
    User create(User user) throws ServiceException;

    /**
     * Find by id user.
     *
     * @param id the id
     * @return the user
     * @throws ServiceException the service exception
     */
    User findById(long id) throws ServiceException;

    /**
     * Find all page.
     *
     * @param pageable the pageable
     * @return the page
     * @throws ServiceException the service exception
     */
    Page<User> findAll(Pageable pageable) throws ServiceException;

    /**
     * Find all by name page.
     *
     * @param name     the name
     * @param pageable the pageable
     * @return the page
     * @throws ServiceException the service exception
     */
    Page<User> findAllByName(String name, Pageable pageable) throws ServiceException;

    /**
     * Delete by id long.
     *
     * @param id the id
     * @return the long
     * @throws ServiceException the service exception
     */
    long deleteById(long id) throws ServiceException;
}
