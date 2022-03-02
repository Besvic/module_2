package com.epam.esm.service.impl;

import com.epam.esm.repository.UserRepository;
import com.epam.esm.entity.User;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.epam.esm.util.LocalizedMessage.getMessageForLocale;

/**
 * The type User service.
 */
@Service
@Log4j2
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * Instantiates a new User service.
     *
     * @param userRepository        the user repository
     * @param bCryptPasswordEncoder the crypt password
     */
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User create(User user) throws ServiceException {
        try {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        }catch (Exception e){
            log.error(getMessageForLocale("impossible.create.user"), e);
            throw new ServiceException(getMessageForLocale("impossible.create.user") + e);
        }
    }

    @Override
    public User findById(long id) throws ServiceException {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()){
            return userOptional.get();
        }
        log.warn(getMessageForLocale("user.not.found.by.id") + id);
        throw new ServiceException(getMessageForLocale("user.not.found.by.id") + id);
    }

    @Override
    public Page<User> findAll(Pageable pageable) throws ServiceException {
        Page<User> userPage = userRepository.findAll(pageable);
        if (userPage.isEmpty()){
            log.warn(getMessageForLocale("user.not.found"));
            throw new ServiceException(getMessageForLocale("user.not.found"));
        }
        return userPage;
    }

    @Override
    public Page<User> findAllByName(String name, Pageable pageable) throws ServiceException {
        Page<User> userList = userRepository.findAllByNameContaining(name, pageable);
        if (userList.isEmpty()){
            log.warn(getMessageForLocale("user.not.found"));
            throw new ServiceException(getMessageForLocale("user.not.found"));
        }
        return userList;
    }

    @Override
    public long deleteById(long userId) throws ServiceException {
        try {
            userRepository.deleteById(userId);
            return userId;
        } catch (Exception e){
            log.warn(getMessageForLocale("impossible.delete.user"), e);
            throw new ServiceException(getMessageForLocale("impossible.delete.user") + e);
        }
    }


    @Override
    public boolean existByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
