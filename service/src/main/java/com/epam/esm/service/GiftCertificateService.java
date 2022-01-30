package com.epam.esm.service;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.ServiceException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.criteria.Predicate;
import java.util.List;
import java.util.Optional;

public interface GiftCertificateService {

    List<GiftCertificate> findAll(Pageable pageable) throws ServiceException;

    List<GiftCertificate> findAllCertificateByTagName(String tagName) throws ServiceException;

    List<GiftCertificate> findAllCertificateByNameOrDescription(String name, String description) throws ServiceException;

    List<GiftCertificate> findAllCertificateByDate(String type) throws ServiceException;

    List<GiftCertificate> findAllCertificateByName(String type) throws ServiceException;

    List<GiftCertificate> findAllByTagIdList(String strTagId) throws ServiceException;

    Optional<GiftCertificate> findById(long id) throws ServiceException;

    GiftCertificate create(GiftCertificate giftCertificate) throws ServiceException;

    GiftCertificate updateById(GiftCertificate currentGiftCertificate) throws ServiceException;

    boolean removeById(long id) throws ServiceException;

}
