package com.epam.esm.repository;

import com.epam.esm.entity.GiftCertificate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface Gift certificate repository.
 */
@Repository
public interface GiftCertificateRepository extends JpaRepository<GiftCertificate, Long>, GiftCertificateRepositoryCustom {

    /**
     * Find gift certificates by tag list name page.
     *
     * @param tagName  the tag name
     * @param pageable the pageable
     * @return the page
     */
    Page<GiftCertificate> findGiftCertificatesByTagList_Name(String tagName, Pageable pageable);

    /**
     * Find all by name containing or description containing page.
     *
     * @param name        the name
     * @param description the description
     * @param pageable    the pageable
     * @return the page
     */
    Page<GiftCertificate> findAllByNameContainingOrDescriptionContaining(String name, String description, Pageable pageable);

    /**
     * Find all by order by create date asc page.
     *
     * @param pageable the pageable
     * @return the page
     */
    Page<GiftCertificate> findAllByOrderByCreateDateAsc(Pageable pageable);

    /**
     * Find all by order by create date desc page.
     *
     * @param pageable the pageable
     * @return the page
     */
    Page<GiftCertificate> findAllByOrderByCreateDateDesc(Pageable pageable);

    /**
     * Find all by order by name asc page.
     *
     * @param pageable the pageable
     * @return the page
     */
    Page<GiftCertificate> findAllByOrderByNameAsc(Pageable pageable);

    /**
     * Find all by order by name desc page.
     *
     * @param pageable the pageable
     * @return the page
     */
    Page<GiftCertificate> findAllByOrderByNameDesc(Pageable pageable);

    /**
     *
     *Dynamic query with custom realisation
     *
     * @param tagId    the tag id
     * @param pageable the pageable
     * @return page
     */
    Page<GiftCertificate> findAllByTagListId(List<Long> tagId, Pageable pageable);
}
