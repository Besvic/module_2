package com.epam.esm.repository;

import com.epam.esm.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface User repository.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find all by name containing page.
     *
     * @param name     the name
     * @param pageable the pageable
     * @return the page
     */
    Page<User> findAllByNameContaining(String name, Pageable pageable);
}
