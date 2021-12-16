package com.epam.esm.service;

import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagService {

    boolean create(Tag tag);
    boolean removeById(long id);
    List<Tag> findAll();
    Optional<Tag> findById(long id);

}
