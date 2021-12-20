package com.epam.esm.service;

import com.epam.esm.dao.exception.DaoException;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateService {

    List<GiftCertificate> findAll() throws ServiceException;
    List<GiftCertificate> findAllCertificateByTagName(String tagName) throws ServiceException;
    List<GiftCertificate> findAllCertificateByNameOrDescription(String name, String description) throws ServiceException;
    List<GiftCertificate> findAllCertificateByDate(String type) throws ServiceException;
    List<GiftCertificate> findAllCertificateByName(String type) throws ServiceException;
    Optional<GiftCertificate> findById(long id) throws ServiceException;
    boolean create(GiftCertificate giftCertificate) throws ServiceException;
    boolean updateById(GiftCertificate currentGiftCertificate) throws ServiceException;
    boolean removeById(long id) throws ServiceException;

}
