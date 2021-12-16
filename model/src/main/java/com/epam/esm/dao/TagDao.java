package com.epam.esm.dao;

import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagDao {

    long create(Tag tag);
    boolean removeById(long id);
    Optional<Tag> findById(long id);
    Optional<Tag> findByName(String name);
    List<Tag> findAll();
}
