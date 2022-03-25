package com.epam.esm.repository;

import com.epam.esm.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface Order repository.
 */
public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * Find all by user id page.
     *
     * @param userId   the user id
     * @param pageable the pageable
     * @return the page
     */
    Page<Order> findAllByUserId(long userId, Pageable pageable);

    /**
     * Exists by user id boolean.
     *
     * @param orderId the order id
     * @param userId  the user id
     * @return the boolean
     */
    boolean existsByIdAndUserId(long orderId, long userId);
}
