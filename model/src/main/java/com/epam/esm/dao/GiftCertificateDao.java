package com.epam.esm.dao;

import com.epam.esm.dao.exception.DaoException;
import com.epam.esm.entity.GiftCertificate;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateDao {

    List<GiftCertificate> findAll() throws DaoException;
    List<GiftCertificate> findAllCertificateByTagName(String tagName) throws DaoException;
    List<GiftCertificate> findAllCertificateByNameOrDescription(String name, String description) throws DaoException;
    List<GiftCertificate> findAllCertificateByDate(String type) throws DaoException;
    List<GiftCertificate> findAllCertificateByName(String type) throws DaoException;
    Optional<GiftCertificate> findById(long id) throws DaoException;
    long create(GiftCertificate giftCertificate) throws DaoException;
    boolean updateNameById(String name, long id) throws DaoException;
    boolean updateDescriptionById(String description, long id) throws DaoException;
    boolean updateDurationById(int duration, long id) throws DaoException;
    boolean updatePriceById(double price, long id) throws DaoException;
    boolean updateLastUpdateDateById(long id) throws DaoException;
    boolean removeById(long id) throws DaoException;


}
