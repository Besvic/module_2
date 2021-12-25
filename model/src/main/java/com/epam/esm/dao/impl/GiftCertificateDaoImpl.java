package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.exception.DaoException;
import com.epam.esm.entity.GiftCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.epam.esm.database.Messages.getMessageForLocale;

/**
 * The type Gift certificate dao.
 */
@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao {

    private static final String ASC = "asc";

    private static final String SQL_FIND_ALL = "" +
            "select id, create_date, description, last_update_date, duration, name, price\n" +
            "from gift_certificate\n" +
            "where status = 'active'\n" +
            "order by name";

    private static final String SQL_SORT_BY_NAME_ASC = "select id, create_date, description, last_update_date, " +
            "duration, name, price\n" +
            "from gift_certificate\n" +
            "where status = 'active'\n" +
            "order by name asc;";

    private static final String SQL_SORT_BY_NAME_DESC = "select id, create_date, description, last_update_date, " +
            "duration, name, price\n" +
            "from gift_certificate\n" +
            "where status = 'active'\n" +
            "order by name desc;";

    private static final String SQL_SORT_BY_CREATE_DATE_ASC = "select id, create_date, description, last_update_date, " +
            "duration, name, price\n" +
            "from gift_certificate\n" +
            "where status = 'active'\n" +
            "order by create_date asc;";

    private static final String SQL_SORT_BY_CREATE_DATE_DESC = "select id, create_date, description, last_update_date, " +
            "duration, name, price\n" +
            "from gift_certificate\n" +
            "where status = 'active'\n" +
            "order by create_date desc;";

    private static final String SQL_FIND_BY_ID =
            "select id, create_date, description, last_update_date, duration, name, price\n" +
            "from gift_certificate " +
            "where id = ? and status = 'active'";

    private static final String SQL_INSERT = "insert into gift_certificate (create_date, description, duration, " +
            "last_update_date, name, price)" +
            "values (?, ? ,?, ?, ?, ?);";

    private static final String SQL_UPDATE_NAME = "update gift_certificate\n" +
            "set name = ?\n" +
            "where id = ?;";

    private static final String SQL_UPDATE_DESCRIPTION = "update gift_certificate\n" +
            "set description = ?\n" +
            "where id = ?;";

    private static final String SQL_UPDATE_DURATION = "update gift_certificate\n" +
            "set duration = ?\n" +
            "where id = ?;";

    private static final String SQL_UPDATE_PRICE = "update gift_certificate\n" +
            "set price = ?\n" +
            "where id = ?;";

    private static final String SQL_UPDATE_LAST_UPDATE_DATE = "update gift_certificate\n" +
            "set last_update_date = ?\n" +
            "where id = ?;";

    private static final String SQL_REMOVE = "update gift_certificate\n" +
            "set status = 'delete'\n" +
            "where id = ?;";

    private static final String SQL_FIND_ALL_BY_TAG_NAME = "select gc.id, gc.create_date, gc.description, gc.duration," +
            " gc.last_update_date, gc.name, gc.price\n" +
            "from tag\n" +
            "join gift_certification_tag gct on tag.id = gct.id_tag\n" +
            "join gift_certificate gc on gct.id_gift_certification = gc.id\n" +
            "where tag.name like ? and gc.status = 'active' and tag.status = 'active'\n" +
            "order by tag.name;";

    private static final String SQL_FIND_ALL_BY_NAME_OR_DESCRIPTION = "select id, create_date, description, duration, " +
            "last_update_date, name, price\n" +
            "from gift_certificate\n" +
            "where status = 'active' and (name like ? or description like ?)\n" +
            "order by name;";

    private final JdbcTemplate jdbcTemplate;

    /**
     * Instantiates a new Gift certificate dao.
     *
     * @param jdbcTemplate the jdbc template
     */
    @Autowired
    public GiftCertificateDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<GiftCertificate> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL, new BeanPropertyRowMapper<>(GiftCertificate.class));
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
                new BeanPropertyRowMapper<>(GiftCertificate.class), id).stream().findAny();
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
            preparedStatement.setDouble(6, giftCertificate.getPrice());
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
    public boolean updatePriceById(double price, long id) {
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
