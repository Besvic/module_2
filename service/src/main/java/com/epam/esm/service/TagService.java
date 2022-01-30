package com.epam.esm.service;

import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface TagService {

    Tag create(Tag tag) throws ServiceException;

    boolean removeById(long id) throws ServiceException;

    List<Tag> findAll() throws ServiceException;

    Optional<Tag> findAllMostlyUsedTagByOrderPrice() throws ServiceException;

    Optional<Tag> findById(long id) throws ServiceException;

}
