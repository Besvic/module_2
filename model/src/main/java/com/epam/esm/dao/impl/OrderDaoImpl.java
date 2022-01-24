/*
package com.epam.esm.dao.impl;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.entity.Order;
import com.epam.esm.row_mapper.OrderRowMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@PropertySource("classpath:sql_query/sql_order.properties")
public class OrderDaoImpl implements OrderDao {

    @Value("${order.create}")
    private String SQL_CREATE;

    @Value("${order.find.all}")
    private String SQL_FIND_ALL;

    @Value("${order.find.id}")
    private String SQL_FIND_BY_ID;

    @Value("${order.remove}")
    private String SQL_REMOVE;

    private final JdbcTemplate jdbcTemplate;

    private final OrderRowMapper orderRowMapper;

    public OrderDaoImpl(JdbcTemplate jdbcTemplate, OrderRowMapper orderRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.orderRowMapper = orderRowMapper;
    }

    @Override
    public long create(Order order) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, order.getUser().getId());
            preparedStatement.setLong(2, order.getGiftCertificate().getId());
            preparedStatement.setBigDecimal(3, order.getPrice());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(order.getDateTime()));
            return preparedStatement;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public Optional<Order> findById(long id) {
        return jdbcTemplate.query(SQL_FIND_BY_ID, orderRowMapper).stream().findAny();
    }

    @Override
    public List<Order> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL, orderRowMapper);
    }

    @Override
    public boolean remove(long id) {
        return false;
    }
}
*/
