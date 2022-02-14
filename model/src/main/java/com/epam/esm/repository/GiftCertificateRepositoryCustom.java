package com.epam.esm.repository;

import com.epam.esm.entity.GiftCertificate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * The interface Gift certificate repository custom.
 */
public interface GiftCertificateRepositoryCustom {

    /**
     * Find all by tag list id page with any id for search.
     *
     * @param tagId    the tag id
     * @param pageable the pageable
     * @return the page
     */
    Page<GiftCertificate> findAllByTagListId(List<Long> tagId, Pageable pageable);
}
