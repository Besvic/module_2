package com.epam.esm.repository;

import com.epam.esm.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
     * @return the list
     */
    @Query("select t from Tag t join t.certificateList cl join cl.order o group by t.id order by sum(o.cost) desc")
    List<Tag> findAllMostlyUsedTagByOrderPrice();

}
