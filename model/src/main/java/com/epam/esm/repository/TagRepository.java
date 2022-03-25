package com.epam.esm.repository;

import com.epam.esm.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface Tag repository.
 */
@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    /**
     * Exists by name boolean.
     *
     * @param name the name
     * @return the boolean
     */
    boolean existsByName(String name);

    /**
     * Find all mostly used tag by order price list.
     *
     * @param userId the user id
     * @return the list
     */
    @Query(value = "select tag_id, t.name as name " +
            "from gift_certificate " +
            "inner join gift_certificate_tag_list gctl on certificate_id = gctl.certificate_list_certificate_id " +
            "inner join tag t on gctl.tag_list_tag_id = t.tag_id " +
            "where order_id = (select orders.order_id from orders where user_id = 1518 order by cost desc limit 1) " +
            "group by t.tag_id " +
            "order by count(t.tag_id) desc ",
            nativeQuery = true
    )
    List<Tag> findAllMostlyUsedTagByOrderPrice(@Param("user_id") long userId);

}
