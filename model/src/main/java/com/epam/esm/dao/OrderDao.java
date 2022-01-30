package com.epam.esm.dao;

import com.epam.esm.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderDao extends JpaRepository<Order, Long> {

    List<Order> findAllByUser_Id(long userId);
}
