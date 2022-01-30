package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DaoException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.validator.GiftCertificateValidator;
import javafx.scene.control.Pagination;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.epam.esm.config.LocalizedMessage.getMessageForLocale;
import static org.springframework.data.domain.Sort.Direction.ASC;

@Slf4j
@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final GiftCertificateDao giftCertificateDao;
    private final TagDao tagDao;
    private final GiftCertificateValidator giftCertificateValidator;

    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateDao, TagDao tagDao,
          GiftCertificateValidator giftCertificateValidator) {
        this.giftCertificateDao = giftCertificateDao;
        this.tagDao = tagDao;
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
    public List<GiftCertificate> findAll(Pageable pageable) throws ServiceException {
        Page<GiftCertificate> certificateList = giftCertificateDao.findAll(pageable);
        return checkValueListGiftCertificate(certificateList.getContent());
    }

    @Override
    public List<GiftCertificate> findAllCertificateByTagName(String tagName) throws ServiceException {
        try {
            List<GiftCertificate> certificateList = giftCertificateDao.findAllByTagListName(tagName);
            return checkValueListGiftCertificate(certificateList);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<GiftCertificate> findAllCertificateByNameOrDescription(String name, String description) throws ServiceException {
        try {
            List<GiftCertificate> certificateList = giftCertificateDao.findAllByNameContainingOrDescriptionContaining(name, description);
            return checkValueListGiftCertificate(certificateList);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<GiftCertificate> findAllCertificateByDate(String type) throws ServiceException {
        checkType(type);
        List<GiftCertificate> certificateList = type.equalsIgnoreCase(ASC.toString()) ?
                giftCertificateDao.findAllByOrderByCreateDateAsc() :
                giftCertificateDao.findAllByOrderByCreateDateDesc();
        return checkValueListGiftCertificate(certificateList);
    }

    @Override
    public List<GiftCertificate> findAllCertificateByName(String type) throws ServiceException {
        checkType(type);
        List<GiftCertificate> certificateList = type.equalsIgnoreCase(ASC.toString()) ?
                giftCertificateDao.findAllByOrderByNameAsc() :
                giftCertificateDao.findAllByOrderByNameDesc();
        return checkValueListGiftCertificate(certificateList);
    }

    // TODO: 30.01.2022 localize exception message
    @Override
    public List<GiftCertificate> findAllByTagIdList(String strTagId) throws ServiceException {
        String splitRegex = "and";
        String[] stringsTagId = strTagId.split(splitRegex);
        if (!giftCertificateValidator.isTagId(stringsTagId)) {
           throw new ServiceException("incorrect input tag list");
        }
        List<Long> idList = new ArrayList<>();
        Arrays.stream(stringsTagId).forEach(s -> idList.add(Long.valueOf(s)));
        List<GiftCertificate> certificateList = giftCertificateDao.findAllByTagListId(idList);
        return checkValueListGiftCertificate(certificateList);
    }

    @Override
    public Optional<GiftCertificate> findById(long id) throws ServiceException {
        Optional<GiftCertificate> certificateOptional = giftCertificateDao.findById(id);
        return checkValueOptionalGiftCertificate(certificateOptional);
    }

    @Override
    @Transactional
    public GiftCertificate create(GiftCertificate giftCertificate) {
        LocalDateTime nowTime = LocalDateTime.now();
        giftCertificate.setCreateDate(nowTime);
        giftCertificate.setLastUpdateDate(nowTime);
        for (Tag tag:giftCertificate.getTagList()) {
             if (!tagDao.existsByName(tag.getName())){
                 giftCertificate.getTagList().remove(tag);
                 giftCertificate.getTagList().add(tagDao.saveAndFlush(tag));
             }
        }
        return giftCertificateDao.saveAndFlush(giftCertificate);
    }

    @Override
    public GiftCertificate updateById(GiftCertificate current) throws ServiceException {
        GiftCertificate certificate = giftCertificateDao.findById(current.getId())
                .orElse(GiftCertificate.builder().build());
        if ((current.getName() != null) && !current.getName().isEmpty()) {
            certificate.setName(current.getName());
        }
        if ((current.getDescription() != null) && !current.getDescription().isEmpty()) {
            certificate.setDescription(current.getDescription());
        }
        if (current.getDuration() > 0) {
            certificate.setDuration(current.getDuration());
        }
        if (current.getPrice() != null && current.getPrice().compareTo(BigDecimal.ZERO) > 0) {
            certificate.setPrice(current.getPrice());
        }
        if ((current.getTagList() != null) && !(current.getTagList().isEmpty()) ){
            certificate.setTagList(current.getTagList());
        }
        try {
            return giftCertificateDao.saveAndFlush(certificate);
        } catch (Exception e){
            log.warn(getMessageForLocale("certificate.not.update") + e.getMessage());
            throw new ServiceException(getMessageForLocale("certificate.not.update") + e.getMessage());
        }

    }

    @Override
    public boolean removeById(long id) throws ServiceException {
        try {
            giftCertificateDao.deleteById(id);
            return true;
        } catch (Exception e) {
            log.warn(getMessageForLocale("certificate.not.delete") + id + e.getMessage());
            throw new ServiceException(getMessageForLocale("certificate.not.delete") + id + e.getMessage());
        }
    }
}
