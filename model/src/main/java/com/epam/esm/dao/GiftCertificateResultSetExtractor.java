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

    // TODO: 21.01.2022 may be move to a separate class?

    private static final String price = "price";
    private static final String duration = "duration";
    private static final String name = "name";
    private static final String tagName = "tagName";
    private static final String certificateId = "certificateId";
    private static final String tagId = "tagId";
    private static final String description = "description";
    private static final String createDate = "create_date";
    private static final String lastUpdateDate = "last_update_date";

    @Override
    public List<GiftCertificate> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<GiftCertificate> certificateList = new ArrayList<>();

        long id = 0;
        while (rs.next()){
            GiftCertificate giftCertificate = new GiftCertificate();
            giftCertificate.setId(rs.getLong(certificateId));
            if (id != giftCertificate.getId()){
                id = giftCertificate.getId();
                giftCertificate.setName(rs.getString(name));
                giftCertificate.setPrice(rs.getBigDecimal(price));
                giftCertificate.setDuration(rs.getInt(duration));
                giftCertificate.setDescription(rs.getString(description));
                giftCertificate.setCreateDate(rs.getTimestamp(createDate).toLocalDateTime());
                giftCertificate.setLastUpdateDate(rs.getTimestamp(lastUpdateDate).toLocalDateTime());
                List<Tag> tagList = new ArrayList<>();
                tagList.add(new Tag(rs.getLong(tagId), rs.getString(tagName)));
                giftCertificate.setTagList(tagList);
                certificateList.add(giftCertificate);
            } else {
                certificateList.get(certificateList.size() - 1).getTagList().add(new Tag(rs.getLong(tagId), rs.getString(tagName)));
            }
        }
        return certificateList;
    }
}
