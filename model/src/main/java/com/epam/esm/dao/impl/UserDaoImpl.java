package com.epam.esm.dao.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@PropertySource("classpath:sql_query/sql_user.properties")
public class UserDaoImpl implements UserDao {

    private final JdbcTemplate jdbcTemplate;

    @Value("${user.create}")
    private String SQL_CREATE;

    @Value("${user.find.all}")
    private String SQL_FIND_ALL;

    @Value("${user.find.id}")
    private String SQL_FIND_BY_ID;

    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public long create(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(SQL_CREATE, new String[] {"id"});
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLastname());
            preparedStatement.setString(3, user.getSurname());
            preparedStatement.setBigDecimal(4, user.getBalance());
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.setString(6, user.getPassword());
            return preparedStatement;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL, new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public Optional<User> findById(long id) {
        return jdbcTemplate.query(SQL_FIND_BY_ID, new BeanPropertyRowMapper<>(User.class), id).stream().findAny();
    }
}
