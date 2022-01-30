package com.epam.esm.service.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.entity.User;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.epam.esm.config.LocalizedMessage.getMessageForLocale;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User create(User user) throws ServiceException {
        return userDao.save(user);
    }

    @Override
    public User findById(long id) throws ServiceException {
        Optional<User> userOptional = userDao.findById(id);
        if (userOptional.isPresent()){
            return userOptional.get();
        }
        throw new ServiceException(getMessageForLocale("user.not.found.by.id"));
    }

    @Override
    public List<User> findAll() throws ServiceException {
        List<User> userList = userDao.findAll();
        if (!userList.isEmpty()){
            return userList;
        }
        throw new ServiceException(getMessageForLocale("user.not.found"));
    }

    @Override
    public List<User> findAllByName(String name) throws ServiceException {
        List<User> userList = userDao.findAllByNameContaining(name);
        if (!userList.isEmpty()){
            return userList;
        }
        throw new ServiceException(getMessageForLocale("user.not.found"));
    }

    @Override
    public long deleteById(long userId) throws ServiceException {
        try {
            userDao.deleteById(userId);
            return userId;
        } catch (Exception e){
            throw new ServiceException("impossible delete user " + e);
        }
    }


}
