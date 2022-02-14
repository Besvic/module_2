package com.epam.esm.service;

import com.epam.esm.entity.Order;
import com.epam.esm.exception.ServiceException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * The interface Order service.
 */
public interface OrderService {

    /**
     * Find all page.
     *
     * @param pageable the pageable
     * @return the page
     * @throws ServiceException the service exception
     */
    Page<Order> findAll(Pageable pageable) throws ServiceException;

    /**
     * Find all by user id page.
     *
     * @param userId   the user id
     * @param pageable the pageable
     * @return the page
     * @throws ServiceException the service exception
     */
    Page<Order> findAllByUserId(long userId, Pageable pageable) throws ServiceException;

    /**
     * Find by id order.
     *
     * @param orderId the order id
     * @return the order
     * @throws ServiceException the service exception
     */
    Order findById(long orderId) throws ServiceException;

    /**
     * Create order.
     *
     * @param order the order
     * @return the order
     * @throws ServiceException the service exception
     */
    Order create(Order order) throws ServiceException;
}
