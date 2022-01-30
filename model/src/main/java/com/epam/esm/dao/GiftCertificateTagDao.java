package com.epam.esm.dao;

import com.epam.esm.exception.DaoException;

import java.util.List;

public interface GiftCertificateTagDao {

    int[] addTagToCertificate(long idCertificate, List<Long> tagIdList) throws DaoException;

}
