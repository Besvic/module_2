package com.epam.esm.service;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.ServiceException;

import java.util.List;
import java.util.Optional;

/**
 * The interface Gift certificate service.
 */
public interface GiftCertificateService {

    /**
     * Find all list.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    List<GiftCertificate> findAll() throws ServiceException;

    /**
     * Find all certificate by tag name list.
     *
     * @param tagName the tag name
     * @return the list
     * @throws ServiceException the service exception
     */
    List<GiftCertificate> findAllCertificateByTagName(String tagName) throws ServiceException;

    /**
     * Find all certificate by name or description list.
     *
     * @param name        the name
     * @param description the description
     * @return the list
     * @throws ServiceException the service exception
     */
    List<GiftCertificate> findAllCertificateByNameOrDescription(String name, String description) throws ServiceException;

    /**
     * Find all certificate by date list.
     *
     * @param type the type
     * @return the list
     * @throws ServiceException the service exception
     */
    List<GiftCertificate> findAllCertificateByDate(String type) throws ServiceException;

    /**
     * Find all certificate by name list.
     *
     * @param type the type
     * @return the list
     * @throws ServiceException the service exception
     */
    List<GiftCertificate> findAllCertificateByName(String type) throws ServiceException;

    /**
     * Find by id optional.
     *
     * @param id the id
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<GiftCertificate> findById(long id) throws ServiceException;

    /**
     * Create boolean.
     *
     * @param giftCertificate the gift certificate
     * @return the boolean
     * @throws ServiceException the service exception
     */
    long create(GiftCertificate giftCertificate) throws ServiceException;

    /**
     * Update by id boolean.
     *
     * @param currentGiftCertificate the current gift certificate
     * @return the boolean
     * @throws ServiceException the service exception
     */
    Optional<GiftCertificate> updateById(GiftCertificate currentGiftCertificate) throws ServiceException;

    /**
     * Remove by id boolean.
     *
     * @param id the id
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean removeById(long id) throws ServiceException;

}
