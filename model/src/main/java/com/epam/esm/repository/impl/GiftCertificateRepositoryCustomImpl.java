package com.epam.esm.repository.impl;

import com.epam.esm.repository.GiftCertificateRepositoryCustom;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * The type Gift certificate repository custom for request with criteria builder
 */
public class GiftCertificateRepositoryCustomImpl implements GiftCertificateRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<GiftCertificate> findAllByTagListId(List<Long> tagId, Pageable pageable) {
        String id = "id";
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> certificateCriteria = cb.createQuery(GiftCertificate.class);
        Root<GiftCertificate> certificateRoot = certificateCriteria.from(GiftCertificate.class);
        Join<GiftCertificate, Tag> tag = certificateRoot.join("tagList");
        Predicate[] predicates = new Predicate[tagId.size()];
        for (int i = 0; i < tagId.size(); i++) {
            predicates[i] = cb.equal(tag.get(id), tagId.get(i));
        }
        certificateCriteria.select(certificateRoot).where(cb.or(predicates)).distinct(true);

        List<GiftCertificate> certificateAllResult = entityManager.createQuery(certificateCriteria).getResultList();
        List<GiftCertificate> certificateResult = entityManager.createQuery(certificateCriteria).setFirstResult((int) pageable.getOffset()).setMaxResults(pageable.getPageSize()).getResultList();

        return new PageImpl<>(certificateResult, pageable, certificateAllResult.size());
    }
}
