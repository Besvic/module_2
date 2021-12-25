package com.epam.esm.dao;

import com.epam.esm.dao.exception.DaoException;
import com.epam.esm.entity.GiftCertificate;

import java.util.List;
import java.util.Optional;

/**
 * The interface Gift certificate dao.
 */
public interface GiftCertificateDao {

    /**
     * Find all list.
     *
     * @return the list
     * @throws DaoException the dao exception
     */
    List<GiftCertificate> findAll() throws DaoException;

    /**
     * Find all certificate by tag name list.
     *
     * @param tagName the tag name
     * @return the list
     * @throws DaoException the dao exception
     */
    List<GiftCertificate> findAllCertificateByTagName(String tagName) throws DaoException;

    /**
     * Find all certificate by name or description list.
     *
     * @param name        the name
     * @param description the description
     * @return the list
     * @throws DaoException the dao exception
     */
    List<GiftCertificate> findAllCertificateByNameOrDescription(String name, String description) throws DaoException;

    /**
     * Find all certificate by date list.
     *
     * @param type the type
     * @return the list
     * @throws DaoException the dao exception
     */
    List<GiftCertificate> findAllCertificateByDate(String type) throws DaoException;

    /**
     * Find all certificate by name list.
     *
     * @param type the type
     * @return the list
     * @throws DaoException the dao exception
     */
    List<GiftCertificate> findAllCertificateByName(String type) throws DaoException;

    /**
     * Find by id optional.
     *
     * @param id the id
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<GiftCertificate> findById(long id) throws DaoException;

    /**
     * Create long.
     *
     * @param giftCertificate the gift certificate
     * @return the long
     * @throws DaoException the dao exception
     */
    long create(GiftCertificate giftCertificate) throws DaoException;

    /**
     * Update name by id boolean.
     *
     * @param name the name
     * @param id   the id
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean updateNameById(String name, long id) throws DaoException;

    /**
     * Update description by id boolean.
     *
     * @param description the description
     * @param id          the id
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean updateDescriptionById(String description, long id) throws DaoException;

    /**
     * Update duration by id boolean.
     *
     * @param duration the duration
     * @param id       the id
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean updateDurationById(int duration, long id) throws DaoException;

    /**
     * Update price by id boolean.
     *
     * @param price the price
     * @param id    the id
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean updatePriceById(double price, long id) throws DaoException;

    /**
     * Update last update date by id boolean.
     *
     * @param id the id
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean updateLastUpdateDateById(long id) throws DaoException;

    /**
     * Remove by id boolean.
     *
     * @param id the id
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean removeById(long id) throws DaoException;


}
