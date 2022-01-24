package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class GiftCertificateResultSetExtractor implements ResultSetExtractor<List<GiftCertificate>> {

    private static final String PRICE = "price";
    private static final String DURATION = "duration";
    private static final String NAME = "name";
    private static final String TAG_NAME = "tagName";
    private static final String CERTIFICATE_ID = "certificateId";
    private static final String TAG_ID = "tagId";
    private static final String DESCRIPTION = "description";
    private static final String CREATE_DATE = "create_date";
    private static final String LAST_UPDATE_DATE = "last_update_date";

    @Override
    public List<GiftCertificate> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<GiftCertificate> certificateList = new ArrayList<>();

        long id = 0;
        while (rs.next()){
            GiftCertificate giftCertificate = new GiftCertificate();
            giftCertificate.setId(rs.getLong(CERTIFICATE_ID));
            if (id != giftCertificate.getId()){
                id = giftCertificate.getId();
                giftCertificate.setName(rs.getString(NAME));
                giftCertificate.setPrice(rs.getBigDecimal(PRICE));
                giftCertificate.setDuration(rs.getInt(DURATION));
                giftCertificate.setDescription(rs.getString(DESCRIPTION));
                giftCertificate.setCreateDate(rs.getTimestamp(CREATE_DATE).toLocalDateTime());
                giftCertificate.setLastUpdateDate(rs.getTimestamp(LAST_UPDATE_DATE).toLocalDateTime());
                List<Tag> tagList = new ArrayList<>();
                tagList.add(new Tag(rs.getLong(TAG_ID), rs.getString(TAG_NAME)));
                giftCertificate.setTagList(tagList);
                certificateList.add(giftCertificate);
            } else {
                certificateList.get(certificateList.size() - 1).getTagList().add(new Tag(rs.getLong(TAG_ID), rs.getString(TAG_NAME)));
            }
        }
        return certificateList;
    }
}
