package com.epam.esm.dao.impl;

import com.epam.esm.dao.exception.DaoException;
import com.epam.esm.config.H2DatabaseConfig;
import com.epam.esm.entity.GiftCertificate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = H2DatabaseConfig.class)
public class GiftCertificateDaoImplTest {

    private static GiftCertificate giftCertificate;

    private static final String SQL_CREATE_TABLE = "create table gift_certificate\n" +
            "(\n" +
            "    id               bigint auto_increment\n" +
            "        primary key,\n" +
            "    create_date      datetime                                   null,\n" +
            "    description      varchar(255)                               null,\n" +
            "    duration         int                                        not null,\n" +
            "    last_update_date datetime                                   null,\n" +
            "    name             varchar(255)                               null,\n" +
            "    price            double                                     not null,\n" +
            "    status           enum ('active', 'delete') default 'active' not null,\n" +
            "    constraint gift_certificate_name_uindex\n" +
            "        unique (name)\n" +
            ");" ;

    private static final String SQL_DROP_TABLE = "drop table gift_certificate";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private GiftCertificateDaoImpl giftCertificateDao;

    @BeforeEach
    public void setUp() {
        try {
            initDatabase();
        } catch (SQLException | ClassNotFoundException | DaoException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    public void destroy() {
        jdbcTemplate.execute(SQL_DROP_TABLE);
    }

    private void initDatabase() throws SQLException, ClassNotFoundException, DaoException {
        jdbcTemplate.execute(SQL_CREATE_TABLE);
        GiftCertificate certificate = GiftCertificate.builder()
                .name("name")
                .description("description")
                .duration(1)
                .price(BigDecimal.valueOf(1.1))
                .build();
        long id = giftCertificateDao.create(certificate);
        giftCertificate = giftCertificateDao.findById(id).get();
    }

    @Test
    void findAll() {
        GiftCertificate actual = giftCertificateDao.findAll().stream().findFirst().get();
        assertEquals(giftCertificate, actual);
    }


    @Test
    void findAllCertificateByNameOrDescription() {
        GiftCertificate actual = giftCertificateDao.findAllCertificateByNameOrDescription("name", "description").stream().findFirst().get();
        assertEquals(giftCertificate, actual);
    }

    @Test
    void findAllCertificateByDate() {
        GiftCertificate actual = giftCertificateDao.findAllCertificateByDate("asc").stream().findFirst().get();
        assertEquals(giftCertificate, actual);
    }

    @Test
    void findAllCertificateByName() {
        GiftCertificate actual = giftCertificateDao.findAllCertificateByName("asc").stream().findFirst().get();
        assertEquals(giftCertificate, actual);
    }

    @Test
    void updateNameById() throws DaoException {
        String name = "new name";
        if (giftCertificateDao.updateNameById(name, 1)){
            GiftCertificate actual = giftCertificateDao.findById(1).get();
            giftCertificate.setName(name);
            assertEquals(giftCertificate, actual);
            return;
        }
        fail("giftCertificateDao.updateNameById return false");
    }

    @Test
    void updateDescriptionById() throws DaoException {
        String description = "new description";
        if (giftCertificateDao.updateDescriptionById(description, 1)){
            GiftCertificate actual = giftCertificateDao.findById(1).get();
            giftCertificate.setDescription(description);
            assertEquals(giftCertificate, actual);
            return;
        }
        fail("giftCertificateDao.updateDescriptionById return false");
    }

    @Test
    void updateDurationById() throws DaoException {
        int duration = 111;
        if (giftCertificateDao.updateDurationById(duration, 1)){
            GiftCertificate actual = giftCertificateDao.findById(1).get();
            giftCertificate.setDuration(duration);
            assertEquals(giftCertificate, actual);
            return;
        }
        fail("giftCertificateDao.updateDurationById return false");
    }

    @Test
    void updatePriceById() throws DaoException {
        BigDecimal price = BigDecimal.valueOf(111.11);
        if (giftCertificateDao.updatePriceById(price, 1)){
            GiftCertificate actual = giftCertificateDao.findById(1).get();
            giftCertificate.setPrice(price);
            assertEquals(giftCertificate, actual);
            return;
        }
        fail("giftCertificateDao.updatePriceById return false");
    }

}
