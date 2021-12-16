package com.epam.esm.dao;

import com.epam.esm.dao.exception.DaoException;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.google.protobuf.ServiceException;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateDao {

    List<GiftCertificate> findAll();
    List<GiftCertificate> findAllCertificateByTagName(String tagName);
    List<GiftCertificate> findAllCertificateByNameOrDescription(String name, String description);
    List<GiftCertificate> findAllCertificateByDate(String type);
    List<GiftCertificate> findAllCertificateByName(String type);
    Optional<GiftCertificate> findById(long id) throws DaoException;
    long create(GiftCertificate giftCertificate);
    boolean updateNameById(String name, long id);
    boolean updateDescriptionById(String description, long id);
    boolean updateDurationById(int duration, long id);
    boolean updatePriceById(double price, long id);
    boolean updateLastUpdateDateById(long id);
    boolean removeById(long id);


}
