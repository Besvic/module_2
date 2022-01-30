package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateTagDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
@PropertySource("classpath:sql_query/sql_gift_certificate_tag.properties")
public class GiftCertificateTagDaoImpl implements GiftCertificateTagDao {

    @Value("${create}")
    private String SQL_CREATE;

    private final JdbcTemplate jdbcTemplate;

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
