package com.epam.esm.service;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.exception.ServiceException;

import java.util.List;

public interface OrderService {

    List<Order> findAll() throws ServiceException;
    List<Order> findAllByUserId(long userId) throws ServiceException;
    Order create(Order order) throws ServiceException;
}
