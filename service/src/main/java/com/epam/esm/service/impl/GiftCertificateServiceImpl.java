package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.GiftCertificateTagDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.exception.DaoException;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.validator.GiftCertificateValidator;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.epam.esm.config.LocalizedMessage.getMessageForLocale;

/**
 * The type Gift certificate service.
 */
@Slf4j
@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final GiftCertificateDao giftCertificateDao;
    private final TagDao tagDao;
    private final GiftCertificateTagDao giftCertificateTagDao;
    private final GiftCertificateValidator giftCertificateValidator;

    /**
     * Instantiates a new Gift certificate service.
     *
     * @param giftCertificateDao    the gift certificate dao
     * @param tagDao                the tag dao
     * @param giftCertificateTagDao the gift certificate tag dao
     * @param giftCertificateValidator       the custom validator
     */
    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateDao, TagDao tagDao,
                                      GiftCertificateTagDao giftCertificateTagDao, GiftCertificateValidator giftCertificateValidator) {
        this.giftCertificateDao = giftCertificateDao;
        this.tagDao = tagDao;
        this.giftCertificateTagDao = giftCertificateTagDao;
        this.giftCertificateValidator = giftCertificateValidator;
    }

    private List<GiftCertificate> checkValueListGiftCertificate(List<GiftCertificate> certificateList)
            throws ServiceException {
        if (certificateList.isEmpty()){
           printWarnMessage();
        }
        return certificateList;
    }

    private Optional<GiftCertificate> checkValueOptionalGiftCertificate(Optional<GiftCertificate> certificateOptional)
            throws ServiceException {
        if (!certificateOptional.isPresent()){
            printWarnMessage();
        }
        return certificateOptional;
    }

    private void printWarnMessage() throws ServiceException {
        log.warn(getMessageForLocale("certificate.not.found"));
        throw new ServiceException(getMessageForLocale("certificate.not.found"));
    }

    private void checkType(String type) throws ServiceException {
        if (!giftCertificateValidator.isSortedType(type)) {
            log.warn(getMessageForLocale("certificate.incorrect.type.sort"));
            throw new ServiceException(getMessageForLocale("certificate.incorrect.type.sort"));
        }
    }

    @Override
    public List<GiftCertificate> findAll() throws ServiceException {
        try {
            List<GiftCertificate> certificateList = giftCertificateDao.findAll();
            return checkValueListGiftCertificate(certificateList);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<GiftCertificate> findAllCertificateByTagName(String tagName) throws ServiceException {
        try {
            List<GiftCertificate> certificateList = giftCertificateDao.findAllCertificateByTagName(tagName);
            return checkValueListGiftCertificate(certificateList);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<GiftCertificate> findAllCertificateByNameOrDescription(String name, String description) throws ServiceException {
        try {
            List<GiftCertificate> certificateList = giftCertificateDao.findAllCertificateByNameOrDescription(name, description);
            return checkValueListGiftCertificate(certificateList);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<GiftCertificate> findAllCertificateByDate(String type) throws ServiceException {
        checkType(type);
        try {
            List<GiftCertificate> certificateList = giftCertificateDao.findAllCertificateByDate(type);
            return checkValueListGiftCertificate(certificateList);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<GiftCertificate> findAllCertificateByName(String type) throws ServiceException {
       checkType(type);
        try {
            List<GiftCertificate> certificateList = giftCertificateDao.findAllCertificateByName(type);
            return checkValueListGiftCertificate(certificateList);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<GiftCertificate> findById(long id) throws ServiceException {
        try {
            Optional<GiftCertificate> certificateOptional = giftCertificateDao.findById(id);
            return checkValueOptionalGiftCertificate(certificateOptional);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional
    public long create(GiftCertificate giftCertificate) throws ServiceException {
        long certificateId;
        try {
            certificateId = giftCertificateDao.create(giftCertificate);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        if (certificateId == 0){
            log.warn(getMessageForLocale("certificate.not.create"));
            throw new ServiceException(getMessageForLocale("certificate.not.create"));
        }
        List<Long> tagIdList = new ArrayList<>();
        Optional<Tag> tagOptional;
        try {
            if (giftCertificate.getTagList() == null){
                log.warn(getMessageForLocale("certificate.not.create"));
                throw new ServiceException(getMessageForLocale("certificate.not.create"));
            }
            for (var i: giftCertificate.getTagList()) {
                    tagOptional = tagDao.findByName(i.getName());
                    tagIdList.add(tagOptional.isPresent() ? tagOptional.get().getId() : tagDao.create(i));
            }
            if (giftCertificateTagDao.addTagToCertificate(certificateId, tagIdList) == null){
                log.warn(getMessageForLocale("tag.not.create"));
                throw new ServiceException(getMessageForLocale("tag.not.create"));
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return certificateId;
    }

    @Transactional
    @Override
    public Optional<GiftCertificate> updateById(GiftCertificate current) throws ServiceException {
        Optional<GiftCertificate> updateCertificate;
        try {
            if ((current.getName() != null) && !current.getName().isEmpty()) {
                giftCertificateDao.updateNameById(current.getName(), current.getId());
            }
            if ((current.getDescription() != null) && !current.getDescription().isEmpty()) {
                giftCertificateDao.updateDescriptionById(current.getDescription(), current.getId());
            }
            if (current.getDuration() > 0) {
                giftCertificateDao.updateDurationById(current.getDuration(), current.getId());
            }
            if (current.getPrice() > 0) {
                giftCertificateDao.updatePriceById(current.getPrice(), current.getId());
            }
            updateCertificate = giftCertificateDao.findById(current.getId());
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return updateCertificate;
    }

    @Override
    public boolean removeById(long id) throws ServiceException {
        try {
            if (giftCertificateDao.removeById(id)){
                return true;
            }
            log.warn(getMessageForLocale("certificate.not.delete"));
            throw new ServiceException(getMessageForLocale("certificate.not.delete"));
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
