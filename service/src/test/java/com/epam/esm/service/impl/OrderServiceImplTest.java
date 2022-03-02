package com.epam.esm.service.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private Page<Order> orderPage;

    @Mock
    private Pageable pageable;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private GiftCertificateRepository certificateRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    void findAll() throws ServiceException {
        when(orderRepository.findAll(pageable)).thenReturn(orderPage);
        Page<Order> expected = orderPage;
        Page<Order> actual = orderService.findAll(pageable);
        assertEquals(expected, actual);
    }

    @Test
    void findAllEmpty() {
        when(orderRepository.findAll(pageable)).thenReturn(Page.empty());
        assertThrows(ServiceException.class, () -> orderService.findAll(pageable));
    }

    @Test
    void findAllByUserId() throws ServiceException {
        when(orderRepository.findAllByUserId(1L, pageable)).thenReturn(orderPage);
        Page<Order> actual = orderService.findAllByUserId(1L, pageable);
        assertEquals(orderPage, actual);
    }

    @Test
    void findAllByUserIdEmpty() {
        when(orderRepository.findAllByUserId(1L, pageable)).thenReturn(Page.empty());
        assertThrows(ServiceException.class, () -> orderService.findAllByUserId(1L, pageable));
    }

    @Test
    void findById() throws ServiceException {
        Order order = new Order();
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));
        Order actual = orderService.findById(anyLong());
        assertEquals(order, actual);
    }

    @Test
    void findByIdEmpty() {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ServiceException.class, () -> orderService.findById(anyLong()));
    }

    @Test
    void create() throws ServiceException {
        Order order = new Order();
        User user = User.builder().id(1L).name("name").balance(BigDecimal.TEN).build();
        GiftCertificate certificate = GiftCertificate.builder().id(2L).price(BigDecimal.ONE).name("certificate").build();
        List<Long> longList = new ArrayList<>();
        longList.add(2L);
        order.setUser(user);
        order.setCertificateList(Collections.singletonList(certificate));
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user));
        when(certificateRepository.findAllById(longList)).
                thenReturn(Collections.singletonList(certificate));
        when(orderRepository.saveAndFlush(order)).thenReturn(order);
        Order actual = orderService.create(order);
        assertEquals(order, actual);

    }

    @Test
    void createIncorrectUserBalance() {
        Order order = new Order();
        User user = User.builder().id(1L).balance(BigDecimal.ONE).name("name").build();
        GiftCertificate certificate = GiftCertificate.builder().id(2L).price(BigDecimal.TEN).name("certificate").build();
        List<Long> longList = new ArrayList<>();
        longList.add(2L);
        order.setUser(user);
        order.setCertificateList(Collections.singletonList(certificate));
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user));
        when(certificateRepository.findAllById(longList)).
                thenReturn(Collections.singletonList(certificate));
        assertThrows(ServiceException.class, () -> orderService.create(order));
    }

    @Test
    void createIncorrectCertificateId() {
        Order order = new Order();
        User user = User.builder().id(1L).name("name").build();
        GiftCertificate certificate = GiftCertificate.builder().id(2L).price(BigDecimal.TEN).name("certificate").build();
        List<Long> longList = new ArrayList<>();
        longList.add(2L);
        order.setUser(user);
        order.setCertificateList(Collections.singletonList(certificate));
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user));
        when(certificateRepository.findAllById(longList)).
                thenReturn(Collections.emptyList());
        assertThrows(ServiceException.class, () -> orderService.create(order));
    }

    @Test
    void createIncorrectUserId() {
        Order order = new Order();
        User user = User.builder().id(1L).name("name").build();
        order.setUser(user);
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ServiceException.class, () -> orderService.create(order));
    }
}