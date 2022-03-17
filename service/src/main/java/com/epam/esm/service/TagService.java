package com.epam.esm.service;

import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ServiceException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * The interface Tag service.
 */
public interface TagService {

    /**
     * Create tag.
     *
     * @param tag the tag
     * @return the tag
     * @throws ServiceException the service exception
     */
    Tag create(Tag tag) throws ServiceException;

    /**
     * Remove by id boolean.
     *
     * @param tagId the tag id
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean removeById(long tagId) throws ServiceException;

    /**
     * Find all page.
     *
     * @param pageable the pageable
     * @return the page
     * @throws ServiceException the service exception
     */
    Page<Tag> findAll(Pageable pageable) throws ServiceException;

    /**
     * Find all mostly used tag by order price tag.
     *
     * @return the tag
     * @throws ServiceException the service exception
     */
    Tag findAllMostlyUsedTagByOrderPrice() throws ServiceException;

    /**
     * Find by id tag.
     *
     * @param tagId the tag id
     * @return the tag
     * @throws ServiceException the service exception
     */
    Tag findById(long tagId) throws ServiceException;

}
