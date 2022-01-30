package com.epam.esm.dao;

import com.epam.esm.exception.DaoException;
import com.epam.esm.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TagDao extends JpaRepository<Tag, Long> {

    /*long create(Tag tag) throws DaoException;

    boolean removeById(long id) throws DaoException;

    Optional<Tag> findById(long id) throws DaoException;

    Optional<Tag> findByName(String name) throws DaoException;

    List<Tag> findAll() throws DaoException;*/

    Optional<Tag> findByName(String name);
    boolean existsByName(String name);

    @Query("select t from Tag t join t.certificateList cl join cl.order o group by t.id order by sum(o.cost) desc")
    List<Tag> findAllMostlyUsedTagByOrderPrice();

}
