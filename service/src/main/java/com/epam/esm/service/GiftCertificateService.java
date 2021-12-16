package com.epam.esm.service;

import com.epam.esm.dao.exception.DaoException;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateService {

    List<GiftCertificate> findAll();
    List<GiftCertificate> findAllCertificateByTagName(String tagName);
    List<GiftCertificate> findAllCertificateByNameOrDescription(String name, String description);
    List<GiftCertificate> findAllCertificateByDate(String type) throws ServiceException;
    List<GiftCertificate> findAllCertificateByName(String type) throws ServiceException;
    Optional<GiftCertificate> findById(long id) throws ServiceException;
    boolean create(GiftCertificate giftCertificate);
    boolean updateById(GiftCertificate currentGiftCertificate);
    boolean removeById(long id);

}
