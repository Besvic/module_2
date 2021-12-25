package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateTagDao;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * The type Gift certificate tag dao.
 */
@Repository
public class GiftCertificateTagDaoImpl implements GiftCertificateTagDao {

    private static final String SQL_CREATE = "insert into gift_certification_tag (id_gift_certification, id_tag)\n" +
            "values (?, ?);";

    private final JdbcTemplate jdbcTemplate;

    /**
     * Instantiates a new Gift certificate tag dao.
     *
     * @param jdbcTemplate the jdbc template
     */
    public GiftCertificateTagDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int[] addTagToCertificate(long idCertificate, List<Long> tagIdList) {
        return jdbcTemplate.batchUpdate(SQL_CREATE, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setLong(1, idCertificate);
                ps.setLong(2, tagIdList.get(i));
            }

            @Override
            public int getBatchSize() {
                return tagIdList.size();
            }
        });
    }
}
