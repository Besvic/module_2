package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.GiftCertificateResultSetExtractor;
import com.epam.esm.exception.DaoException;
import com.epam.esm.entity.GiftCertificate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static com.epam.esm.config.LocalizedMessage.getMessageForLocale;

/**
 * The type Gift certificate dao.
 */
@Repository
@PropertySource("classpath:sql_query/sql_gift_certificate.properties")
public class GiftCertificateDaoImpl implements GiftCertificateDao {

    private static final String ASC = "asc";

    @Value("${find.all.certificate}")
    private String SQL_FIND_ALL;

    @Value("${sort.name.asc}")
    private String SQL_SORT_BY_NAME_ASC;

    @Value("${sort.name.desc}")
    private String SQL_SORT_BY_NAME_DESC;

    @Value("${sort.create.date.asc}")
    private String SQL_SORT_BY_CREATE_DATE_ASC;

    @Value("${sort.create.date.desc}")
    private String SQL_SORT_BY_CREATE_DATE_DESC;

    @Value("${find.id.certificate}")
    private String SQL_FIND_BY_ID;

    @Value("${insert.certificate}")
    private String SQL_INSERT;

    @Value("${update.name}")
    private String SQL_UPDATE_NAME;

    @Value("${update.description}")
    private String SQL_UPDATE_DESCRIPTION;

    @Value("${update.duration}")
    private String SQL_UPDATE_DURATION;

    @Value("${update.price}")
    private String SQL_UPDATE_PRICE;

    @Value("${update.last.update.date}")
    private String SQL_UPDATE_LAST_UPDATE_DATE;

    @Value("${remove}")
    private String SQL_REMOVE;

    @Value("${find.all.tag.name}")
    private String SQL_FIND_ALL_BY_TAG_NAME;

    @Value("${find.all.name.description}")
    private String SQL_FIND_ALL_BY_NAME_OR_DESCRIPTION;

    private final JdbcTemplate jdbcTemplate;
    private final GiftCertificateResultSetExtractor giftCertificateResultSetExtractor;

    /**
     * Instantiates a new Gift certificate dao.
     *
     * @param jdbcTemplate the jdbc template
     * @param giftCertificateResultSetExtractor
     */
    public GiftCertificateDaoImpl(JdbcTemplate jdbcTemplate, GiftCertificateResultSetExtractor giftCertificateResultSetExtractor) {
        this.jdbcTemplate = jdbcTemplate;
        this.giftCertificateResultSetExtractor = giftCertificateResultSetExtractor;
    }

    @Override
    public List<GiftCertificate> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL, giftCertificateResultSetExtractor);
    }

    @Override
    public List<GiftCertificate> findAllCertificateByTagName(String tagName) {
        return jdbcTemplate.query(SQL_FIND_ALL_BY_TAG_NAME, new BeanPropertyRowMapper<>(GiftCertificate.class),'%' + tagName + '%');
    }

    @Override
    public List<GiftCertificate> findAllCertificateByNameOrDescription(String name, String description) {
        return jdbcTemplate.query(SQL_FIND_ALL_BY_NAME_OR_DESCRIPTION, new BeanPropertyRowMapper<>(GiftCertificate.class),
                '%' + name + '%', '%' + description + '%');
    }

    @Override
    public List<GiftCertificate> findAllCertificateByDate(String type) {
        return jdbcTemplate.query(type.equalsIgnoreCase(ASC) ? SQL_SORT_BY_CREATE_DATE_ASC : SQL_SORT_BY_CREATE_DATE_DESC,
                new BeanPropertyRowMapper<>(GiftCertificate.class));
    }

    @Override
    public List<GiftCertificate> findAllCertificateByName(String type) {
        return jdbcTemplate.query(type.equalsIgnoreCase(ASC) ? SQL_SORT_BY_NAME_ASC : SQL_SORT_BY_NAME_DESC,
                new BeanPropertyRowMapper<>(GiftCertificate.class));
    }

    @Override
    public Optional<GiftCertificate> findById(long id) throws DaoException {
        Optional<GiftCertificate> giftCertificate = jdbcTemplate.query(SQL_FIND_BY_ID,
                giftCertificateResultSetExtractor, id).stream().findAny();
        if (giftCertificate.isPresent()){
            return giftCertificate;
        }
        throw new DaoException(getMessageForLocale("certificate.not.found.by.id") + id);
    }

    @Override
    public long create(GiftCertificate giftCertificate) {
        Timestamp currentTime = Timestamp.valueOf(LocalDateTime.now());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setTimestamp(1, currentTime);
            preparedStatement.setString(2, giftCertificate.getDescription());
            preparedStatement.setInt(3, giftCertificate.getDuration());
            preparedStatement.setTimestamp(4, currentTime);
            preparedStatement.setString(5, giftCertificate.getName());
            preparedStatement.setBigDecimal(6, giftCertificate.getPrice());
            return preparedStatement;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public boolean updateNameById(String name, long id) {
        return jdbcTemplate.update(SQL_UPDATE_NAME, name, id) != 0;
    }

    @Override
    public boolean updateDescriptionById(String description, long id) {
        return jdbcTemplate.update(SQL_UPDATE_DESCRIPTION, description, id) != 0;
    }

    @Override
    public boolean updateDurationById(int duration, long id) {
        return jdbcTemplate.update(SQL_UPDATE_DURATION, duration, id) != 0;
    }

    @Override
    public boolean updatePriceById(BigDecimal price, long id) {
        return jdbcTemplate.update(SQL_UPDATE_PRICE, price, id) != 0;
    }

    @Override
    public boolean updateLastUpdateDateById(long id) {
        return jdbcTemplate.update(SQL_UPDATE_LAST_UPDATE_DATE, LocalDate.now(), id) != 0;
    }

    @Override
    public boolean removeById(long id) {
        return jdbcTemplate.update(SQL_REMOVE, id) != 0;
    }
}
