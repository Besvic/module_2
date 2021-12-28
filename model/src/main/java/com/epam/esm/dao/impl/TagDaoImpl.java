package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

/**
 * The type Tag dao.
 */
@PropertySource("classpath:sql_tag.properties")
@Repository
public class TagDaoImpl implements TagDao {

    @Value("${insert}")
    private String SQL_INSERT;

    @Value("${remove.id}")
    private String SQL_REMOVE_BY_ID;

    @Value("${find.id}")
    private String SQL_FIND_BY_ID;

    @Value("${find.name}")
    private String SQL_FIND_BY_NAME;

    @Value("${find.all}")
    private String SQL_FIND_ALL;

    private final JdbcTemplate jdbcTemplate;

    /**
     * Instantiates a new Tag dao.
     *
     * @param jdbcTemplate the jdbc template
     */
    public TagDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public long create(Tag tag) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(SQL_INSERT, new String[] {"id"});
            preparedStatement.setString(1, tag.getName());
            return preparedStatement;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public boolean removeById(long id) {
        return jdbcTemplate.update(SQL_REMOVE_BY_ID, id) != 0;
    }

    @Override
    public Optional<Tag> findById(long id) {
        return jdbcTemplate.query(SQL_FIND_BY_ID, new BeanPropertyRowMapper<>(Tag.class), id).stream().findAny();
    }

    @Override
    public Optional<Tag> findByName(String name) {
        return jdbcTemplate.query(SQL_FIND_BY_NAME, new BeanPropertyRowMapper<>(Tag.class), name).stream().findAny();
    }

    @Override
    public List<Tag> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL, new BeanPropertyRowMapper<>(Tag.class));
    }
}
