package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
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
@Repository
public class TagDaoImpl implements TagDao {

    private static final String SQL_INSERT = "insert into tag (name)\n" +
            "values (?);";

    private static final String SQL_REMOVE_BY_ID = "update tag\n" +
            "set status = 'delete'\n" +
            "where id = ?;";

    private static final String SQL_FIND_BY_ID = "select tag.id, tag.name\n" +
            "from tag\n" +
            "where id = ? and status = 'active';";

    private static final String SQL_FIND_BY_NAME = "select tag.id, tag.name\n" +
            "from tag\n" +
            "where name = ? and status = 'active';";

    private static final String SQL_FIND_ALL = "select tag.id, tag.name\n" +
            "from tag\n" +
            "where status = 'active'\n" +
            "order by name;";

    private final JdbcTemplate jdbcTemplate;

    /**
     * Instantiates a new Tag dao.
     *
     * @param jdbcTemplate the jdbc template
     */
    @Autowired
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
