package com.epam.esm.dao;

import com.epam.esm.exception.DaoException;

import java.util.List;

/**
 * The interface Gift certificate tag dao.
 */
public interface GiftCertificateTagDao {

    /**
     * Add tag to certificate int [ ].
     *
     * @param idCertificate the id certificate
     * @param tagIdList     the tag id list
     * @return the int [ ]
     * @throws DaoException the dao exception
     */
    int[] addTagToCertificate(long idCertificate, List<Long> tagIdList) throws DaoException;

}
