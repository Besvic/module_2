package com.epam.esm.dao;

import com.epam.esm.exception.DaoException;
import com.epam.esm.entity.GiftCertificate;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface GiftCertificateDao extends JpaRepository<GiftCertificate, Long>, GiftCertificateRepositoryCustom {

//    List<GiftCertificate> findAll();

    @Query("select t, gc from GiftCertificate gc inner join Tag t where t.name = 'qaz'")
    List<GiftCertificate> findAllByTagListName(@Param("tagName") String tagName) throws DaoException;

    List<GiftCertificate> findAllByNameContainingOrDescriptionContaining(String name, String description) throws DaoException;


    List<GiftCertificate> findAllByOrderByCreateDateAsc();

    List<GiftCertificate> findAllByOrderByCreateDateDesc();


    List<GiftCertificate> findAllByOrderByNameAsc();
    List<GiftCertificate> findAllByOrderByNameDesc();

//    @Query("select gc from GiftCertificate gc join gc.tagList t where t.id = :predicate")
//    List<GiftCertificate> foundByTagIdList(Predicate predicate);

    List<GiftCertificate> findAllByTagListId(List<Long> tagId);

   // boolean updateNameById(String name, long id) throws DaoException;



//    boolean updateDescriptionById(String description, long id) throws DaoException;
//
//    boolean updateDurationById(int duration, long id) throws DaoException;
//
//    boolean updatePriceById(BigDecimal price, long id) throws DaoException;
//
//    boolean updateLastUpdateDateById(long id) throws DaoException;

    boolean removeById(long id) throws DaoException;


}
