package com.epam.esm.service.impl;

import com.epam.esm.config.LocalizedMessage;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.OrderDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.OrderService;
import lombok.var;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Transient;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.epam.esm.config.LocalizedMessage.getMessageForLocale;
import static java.time.LocalDateTime.now;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderDao orderDao;
    private GiftCertificateDao certificateDao;
    private UserDao userDao;

    public OrderServiceImpl(OrderDao orderDao, GiftCertificateDao certificateDao, UserDao userDao) {
        this.orderDao = orderDao;
        this.certificateDao = certificateDao;
        this.userDao = userDao;
    }

    @Override
    public List<Order> findAll() throws ServiceException {
        List<Order> orderList = orderDao.findAll();
        if (orderList.isEmpty()){
            throw new ServiceException(getMessageForLocale("order.not.found"));
        }
        return orderList;
    }

    @Override
    public List<Order> findAllByUserId(long userId) throws ServiceException {
        List<Order> orderList = orderDao.findAllByUser_Id(userId);
        if (orderList.isEmpty()){
            throw new ServiceException(getMessageForLocale("order.not.found.by.id") + userId);
        }
        return orderList;
    }

    @Override
    @Transactional
    public Order create(Order order) throws ServiceException {
        Optional<User> userOptional = userDao.findById(order.getUser().getId());
        if (!userOptional.isPresent()){
            throw new ServiceException(getMessageForLocale("user.not.found.by.id") + order.getUser().getId());
        }
        order.setUser(userOptional.get());
        List<Long> certificateIdList = new ArrayList<>();
        order.getCertificateList().forEach(giftCertificate -> {
            long id = giftCertificate.getId();
            certificateIdList.add(id);
        });
        List<GiftCertificate> certificateList = certificateDao.findAllById(certificateIdList);
        if (certificateList.isEmpty()){
            throw new ServiceException(getMessageForLocale("certificate.not.found"));
        }
        order.setCertificateList(certificateList);
        order.setDateTime(now());
        BigDecimal cost = BigDecimal.ZERO;
        for (var i: certificateList) {
            cost = cost.add(i.getPrice());
        }
        order.setCost(cost);
        if (userOptional.get().getBalance().compareTo(cost) < 0){
            throw new ServiceException(getMessageForLocale("insufficient.funds.balance"));
        }
        userOptional.get().setBalance(userOptional.get().getBalance().subtract(cost));
        return orderDao.saveAndFlush(order);
    }
}
