package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateRepositoryCustom;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import lombok.var;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GiftCertificateRepositoryCustomImpl implements GiftCertificateRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<GiftCertificate> findAllByTagListId(List<Long> tagId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> certificateCriteria = cb.createQuery(GiftCertificate.class);
        Root<GiftCertificate> certificateRoot = certificateCriteria.from(GiftCertificate.class);
        Join<GiftCertificate, Tag> tag = certificateRoot.join("tagList");
        Predicate[] predicates = new Predicate[tagId.size()];
        for (int i = 0; i < tagId.size(); i++) {
            predicates[i] = cb.equal(tag.get("id"), tagId.get(i));
        }

       /* List<Predicate> predicateList = new ArrayList<>();
        for (var i : tagId) {
            predicateList.add(cb.equal(tag.get("id"), i));
        }*/
        certificateCriteria.select(certificateRoot).where(cb.or(predicates/*predicateList.toArray(new Predicate[tagId.size()])*/)).distinct(true);
        List<GiftCertificate> resultList = entityManager.createQuery(certificateCriteria).getResultList();
        resultList.forEach(System.out::println);
        return resultList;

    }
}
