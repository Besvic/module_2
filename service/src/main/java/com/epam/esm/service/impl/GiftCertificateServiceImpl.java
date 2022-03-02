package com.epam.esm.service.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.validator.GiftCertificateValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.epam.esm.util.LocalizedMessage.getMessageForLocale;
import static org.springframework.data.domain.Sort.Direction.ASC;

/**
 * The type Gift certificate service.
 */
@Slf4j
@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final GiftCertificateRepository giftCertificateRepository;
    private final TagRepository tagRepository;
    private final GiftCertificateValidator giftCertificateValidator;

    /**
     * Instantiates a new Gift certificate service.
     *
     * @param giftCertificateRepository the gift certificate repository
     * @param tagRepository             the tag repository
     * @param giftCertificateValidator  the gift certificate validator
     */
    public GiftCertificateServiceImpl(GiftCertificateRepository giftCertificateRepository, TagRepository tagRepository,
                                      GiftCertificateValidator giftCertificateValidator) {
        this.giftCertificateRepository = giftCertificateRepository;
        this.tagRepository = tagRepository;
        this.giftCertificateValidator = giftCertificateValidator;
    }

    private Page<GiftCertificate> checkValueListGiftCertificate(Page<GiftCertificate> certificateList)
            throws ServiceException {
        if (certificateList.isEmpty()){
           printWarnMessage();
        }
        return certificateList;
    }

    private GiftCertificate checkValueOptionalGiftCertificate(Optional<GiftCertificate> certificateOptional)
            throws ServiceException {
        if (!certificateOptional.isPresent()){
            printWarnMessage();
        }
        return certificateOptional.orElse(GiftCertificate.builder().build());
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
    public Page<GiftCertificate> findAll(Pageable pageable) throws ServiceException {
        Page<GiftCertificate> certificateList = giftCertificateRepository.findAll(pageable);
        return checkValueListGiftCertificate(certificateList);
    }

    @Override
    public Page<GiftCertificate> findAllCertificateByTagName(String tagName, Pageable pageable) throws ServiceException {
        Page<GiftCertificate> certificateList = giftCertificateRepository.findGiftCertificatesByTagListName(tagName, pageable);
        return checkValueListGiftCertificate(certificateList);
    }

    @Override
    public Page<GiftCertificate> findAllCertificateByNameOrDescription(String name, String description, Pageable pageable) throws ServiceException {
        Page<GiftCertificate> certificateList = giftCertificateRepository.findAllByNameContainingOrDescriptionContaining(name, description, pageable);
        return checkValueListGiftCertificate(certificateList);
    }

    @Override
    public Page<GiftCertificate> findAllCertificateByDate(String type, Pageable pageable) throws ServiceException {
        checkType(type);
        Page<GiftCertificate> certificateList = type.equalsIgnoreCase(ASC.toString()) ?
                giftCertificateRepository.findAllByOrderByCreateDateAsc(pageable) :
                giftCertificateRepository.findAllByOrderByCreateDateDesc(pageable);
        return checkValueListGiftCertificate(certificateList);
    }

    @Override
    public Page<GiftCertificate> findAllCertificateByName(String type, Pageable pageable) throws ServiceException {
        checkType(type);
        Page<GiftCertificate> certificateList = type.equalsIgnoreCase(ASC.toString()) ?
                giftCertificateRepository.findAllByOrderByNameAsc(pageable) :
                giftCertificateRepository.findAllByOrderByNameDesc(pageable);
        return checkValueListGiftCertificate(certificateList);
    }

    @Override
    public Page<GiftCertificate> findAllByTagIdList(String strTagId, Pageable pageable) throws ServiceException {
        String splitRegex = ",";
        String[] stringsTagId = strTagId.split(splitRegex);
        if (!giftCertificateValidator.isTagId(stringsTagId)) {
            log.warn(getMessageForLocale("incorrect.tag.list"));
           throw new ServiceException(getMessageForLocale("incorrect.tag.list"));
        }
        List<Long> idList = new ArrayList<>();
        Arrays.stream(stringsTagId).forEach(s -> idList.add(Long.valueOf(s)));
        Page<GiftCertificate> certificateList = giftCertificateRepository.findAllByTagListId(idList, pageable);
        return checkValueListGiftCertificate(certificateList);
    }

    @Override
    public GiftCertificate findById(long id) throws ServiceException {
        Optional<GiftCertificate> certificateOptional = giftCertificateRepository.findById(id);
        return checkValueOptionalGiftCertificate(certificateOptional);
    }

    @Override
    @Transactional
    public GiftCertificate create(GiftCertificate giftCertificate) {
        LocalDateTime nowTime = LocalDateTime.now();
        giftCertificate.setCreateDate(nowTime);
        giftCertificate.setLastUpdateDate(nowTime);
        for (Tag tag:giftCertificate.getTagList()) {
             if (!tagRepository.existsByName(tag.getName())){
                 giftCertificate.getTagList().add(tagRepository.saveAndFlush(tag));
                 giftCertificate.getTagList().remove(tag);
             }
        }
        return giftCertificateRepository.saveAndFlush(giftCertificate);
    }

    @Override
    public GiftCertificate updateById(GiftCertificate current) throws ServiceException {
        GiftCertificate certificate;
        try {
            certificate = giftCertificateRepository.findById(current.getId())
                    .orElse(GiftCertificate.builder().build());
            if (giftCertificateValidator.isName(current.getName())) {
                certificate.setName(current.getName());
            }
            if (giftCertificateValidator.isDescription(current.getDescription())) {
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
            return giftCertificateRepository.saveAndFlush(certificate);
        } catch (Exception e){
            log.warn(getMessageForLocale("certificate.not.update"), e);
            throw new ServiceException(getMessageForLocale("certificate.not.update") + e);
        }

    }

    @Override
    public boolean removeById(long id) throws ServiceException {
        try {
            giftCertificateRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            log.warn(getMessageForLocale("certificate.not.delete") + id, e);
            throw new ServiceException(getMessageForLocale("certificate.not.delete") + id + e);
        }
    }
}
