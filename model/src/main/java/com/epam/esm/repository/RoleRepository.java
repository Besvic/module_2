package com.epam.esm.repository;

import com.epam.esm.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * The interface Role repository.
 */
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * Find by name optional.
     *
     * @param name the name
     * @return the optional
     */
    Optional<Role> findByName(String name);
}
