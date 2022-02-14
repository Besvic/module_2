package com.epam.esm.service;


import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.ServiceException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * The interface Gift certificate service.
 */
public interface GiftCertificateService {

    /**
     * Find all page.
     *
     * @param pageable the pageable
     * @return the page
     * @throws ServiceException the service exception
     */
    Page<GiftCertificate> findAll(Pageable pageable) throws ServiceException;

    /**
     * Find all certificate by tag name page.
     *
     * @param tagName  the tag name
     * @param pageable the pageable
     * @return the page
     * @throws ServiceException the service exception
     */
    Page<GiftCertificate> findAllCertificateByTagName(String tagName, Pageable pageable) throws ServiceException;

    /**
     * Find all certificate by name or description page.
     *
     * @param name        the name
     * @param description the description
     * @param pageable    the pageable
     * @return the page
     * @throws ServiceException the service exception
     */
    Page<GiftCertificate> findAllCertificateByNameOrDescription(String name, String description, Pageable pageable) throws ServiceException;

    /**
     * Find all certificate by date page.
     *
     * @param type     the type
     * @param pageable the pageable
     * @return the page
     * @throws ServiceException the service exception
     */
    Page<GiftCertificate> findAllCertificateByDate(String type, Pageable pageable) throws ServiceException;

    /**
     * Find all certificate by name page.
     *
     * @param type     the type
     * @param pageable the pageable
     * @return the page
     * @throws ServiceException the service exception
     */
    Page<GiftCertificate> findAllCertificateByName(String type, Pageable pageable) throws ServiceException;

    /**
     * Find all by tag id list page.
     *
     * @param strTagId the str tag id
     * @param pageable the pageable
     * @return the page
     * @throws ServiceException the service exception
     */
    Page<GiftCertificate> findAllByTagIdList(String strTagId, Pageable pageable) throws ServiceException;

    /**
     * Find by id gift certificate.
     *
     * @param id the id
     * @return the gift certificate
     * @throws ServiceException the service exception
     */
    GiftCertificate findById(long id) throws ServiceException;

    /**
     * Create gift certificate.
     *
     * @param giftCertificate the gift certificate
     * @return the gift certificate
     * @throws ServiceException the service exception
     */
    GiftCertificate create(GiftCertificate giftCertificate) throws ServiceException;

    /**
     * Update by id gift certificate.
     *
     * @param currentGiftCertificate the current gift certificate
     * @return the gift certificate
     * @throws ServiceException the service exception
     */
    GiftCertificate updateById(GiftCertificate currentGiftCertificate) throws ServiceException;

    /**
     * Remove by id boolean.
     *
     * @param id the id
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean removeById(long id) throws ServiceException;

}
