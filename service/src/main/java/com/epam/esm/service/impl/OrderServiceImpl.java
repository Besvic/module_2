package com.epam.esm.service.impl;

import com.epam.esm.pojo.security.ERole;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.security.UserDetailsImpl;
import com.epam.esm.service.OrderService;
import lombok.extern.log4j.Log4j2;
import lombok.var;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.epam.esm.util.LocalizedMessage.getMessageForLocale;
import static java.time.LocalDateTime.now;

/**
 * The type Order service.
 */
@Service
@Log4j2
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final GiftCertificateRepository certificateRepository;
    private final UserRepository userRepository;

    /**
     * Instantiates a new Order service.
     *
     * @param orderRepository       the order repository
     * @param certificateRepository the certificate repository
     * @param userRepository        the user repository
     */
    public OrderServiceImpl(OrderRepository orderRepository, GiftCertificateRepository certificateRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.certificateRepository = certificateRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Page<Order> findAll(Pageable pageable) throws ServiceException {
        Page<Order> orderList = orderRepository.findAll(pageable);
        if (orderList.isEmpty()){
            log.warn(getMessageForLocale("order.not.found"));
            throw new ServiceException(getMessageForLocale("order.not.found"));
        }
        return orderList;
    }

    @Override
    public Page<Order> findAllByUserId(long userId, Pageable pageable) throws ServiceException {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        GrantedAuthority roleUser = new SimpleGrantedAuthority(ERole.ROLE_USER.name());
        GrantedAuthority roleAdmin = new SimpleGrantedAuthority(ERole.ROLE_ADMIN.name());
        Page<Order> orderList;
        if(userDetails.getAuthorities().contains(roleAdmin) ||
                (userDetails.getAuthorities().contains(roleUser) &&
                        userDetails.getId() == userId)){
            orderList = orderRepository.findAllByUserId(userId, pageable);
        } else {
            log.warn(getMessageForLocale("define.accesses"));
            throw new ServiceException(getMessageForLocale("define.accesses"));
        }
        if (orderList.isEmpty()){
            log.warn(getMessageForLocale("order.not.found.by.user.id") + userId);
            throw new ServiceException(getMessageForLocale("order.not.found.by.user.id") + userId);
        }
        return orderList;
    }

    @Override
    public Order findById(long orderId) throws ServiceException {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        Optional<Order> orderOptional;
        GrantedAuthority roleUser = new SimpleGrantedAuthority(ERole.ROLE_USER.name());
        GrantedAuthority roleAdmin = new SimpleGrantedAuthority(ERole.ROLE_ADMIN.name());
        if (userDetails.getAuthorities().contains(roleAdmin) ||
                    (userDetails.getAuthorities().contains(roleUser) &&
                    orderRepository.existsByIdAndUserId(orderId, userDetails.getId()))){
            orderOptional = orderRepository.findById(orderId);
        }else{
            log.warn(getMessageForLocale("define.accesses"));
            throw new ServiceException(getMessageForLocale("define.accesses"));
        }
        if (orderOptional.isPresent()){
            return orderOptional.get();
        }
        log.warn(getMessageForLocale("order.not.found.by.id") + orderId);
        throw new ServiceException(getMessageForLocale("order.not.found.by.id") + orderId);
    }

    @Override
    @Transactional
    public Order create(Order order) throws ServiceException {
        Optional<User> userOptional = userRepository.findById(order.getUser().getId());
        if (!userOptional.isPresent()){
            log.warn(getMessageForLocale("user.not.found.by.id") + order.getUser().getId());
            throw new ServiceException(getMessageForLocale("user.not.found.by.id") + order.getUser().getId());
        }
        order.setUser(userOptional.get());
        List<Long> certificateIdList = new ArrayList<>();
        order.getCertificateList().forEach(giftCertificate -> {
            long id = giftCertificate.getId();
            certificateIdList.add(id);
        });
        List<GiftCertificate> certificateList = certificateRepository.findAllById(certificateIdList);
        if (certificateList.isEmpty()){
            log.warn(getMessageForLocale("certificate.not.found"));
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
            log.warn(getMessageForLocale("insufficient.funds.balance"));
            throw new ServiceException(getMessageForLocale("insufficient.funds.balance"));
        }
        userOptional.get().setBalance(userOptional.get().getBalance().subtract(cost));
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        GrantedAuthority roleUser = new SimpleGrantedAuthority(ERole.ROLE_USER.name());
        GrantedAuthority roleAdmin = new SimpleGrantedAuthority(ERole.ROLE_ADMIN.name());
        if (userDetails.getAuthorities().contains(roleAdmin) ||
                (userDetails.getAuthorities().contains(roleUser) &&
                        order.getUser().getId() == userDetails.getId())){
            return orderRepository.saveAndFlush(order);
        }else{
            log.warn(getMessageForLocale("define.accesses"));
            throw new ServiceException(getMessageForLocale("define.accesses"));
        }
    }
}
