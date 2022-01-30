package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;

import java.util.List;

public interface GiftCertificateRepositoryCustom {

    List<GiftCertificate> findAllByTagListId(List<Long> tagId);
}
