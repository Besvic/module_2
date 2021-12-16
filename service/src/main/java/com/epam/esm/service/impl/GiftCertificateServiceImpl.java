package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.GiftCertificateTagDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.exception.DaoException;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.validator.CustomValidator;
import lombok.var;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final GiftCertificateDao giftCertificateDao;
    private final TagDao tagDao;
    private final GiftCertificateTagDao giftCertificateTagDao;
    private final CustomValidator customValidator;

    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateDao, TagDao tagDao,
                                      GiftCertificateTagDao giftCertificateTagDao, CustomValidator customValidator) {
        this.giftCertificateDao = giftCertificateDao;
        this.tagDao = tagDao;
        this.giftCertificateTagDao = giftCertificateTagDao;
        this.customValidator = customValidator;
    }

    @Override
    public List<GiftCertificate> findAll() {
        return giftCertificateDao.findAll();
    }

    @Override
    public List<GiftCertificate> findAllCertificateByTagName(String tagName) {
        return giftCertificateDao.findAllCertificateByTagName(tagName);
    }

    @Override
    public List<GiftCertificate> findAllCertificateByNameOrDescription(String name, String description) {
        return giftCertificateDao.findAllCertificateByNameOrDescription(name, description);
    }

    @Override
    public List<GiftCertificate> findAllCertificateByDate(String type) throws ServiceException {
        if (customValidator.isSortedType(type)){
            return giftCertificateDao.findAllCertificateByDate(type);
        }
        throw new ServiceException("Incorrect input data!");
    }

    @Override
    public List<GiftCertificate> findAllCertificateByName(String type) throws ServiceException {
        if (customValidator.isSortedType(type)){
            return giftCertificateDao.findAllCertificateByName(type);
        }
        throw new ServiceException("Incorrect input data!");
    }

    @Override
    public Optional<GiftCertificate> findById(long id) throws ServiceException {
        try {
            return giftCertificateDao.findById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional
    public boolean create(GiftCertificate giftCertificate) {
        long certificateId = giftCertificateDao.create(giftCertificate);
        List<Long> tagIdList = new ArrayList<>();
        Optional<Tag> tagOptional;
        for (var i: giftCertificate.getTagList()) {
            tagOptional = tagDao.findByName(i.getName());
            tagIdList.add(tagOptional.map(Tag::getId).orElseGet(() -> tagDao.create(i)));
        }
        return giftCertificateTagDao.addTagToCertificate(certificateId, tagIdList) != null;        // TODO: 10.12.2021 may be change

    }

    @Transactional
    @Override
    public boolean updateById(GiftCertificate current) {
        if (!current.getName().isEmpty()){
            // TODO: 05.12.2021 may be insert validation
            giftCertificateDao.updateNameById(current.getName(), current.getId());
        }
        if (!current.getDescription().isEmpty()){
            giftCertificateDao.updateDescriptionById(current.getDescription(), current.getId());
        }
        if (current.getDuration() != 0){
            giftCertificateDao.updateDurationById(current.getDuration(), current.getId());
        }
        if (current.getPrice() != 0){
            giftCertificateDao.updatePriceById(current.getPrice(), current.getId());
        }
        return true;
    }

    @Override
    public boolean removeById(long id) {
        return giftCertificateDao.removeById(id);
    }
}
