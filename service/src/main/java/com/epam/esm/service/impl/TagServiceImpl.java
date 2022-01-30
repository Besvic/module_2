package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.epam.esm.config.LocalizedMessage.getMessageForLocale;

@Slf4j
@Service
public class TagServiceImpl implements TagService {

    private final TagDao tagDao;

    public TagServiceImpl(TagDao tagDao) {
        this.tagDao = tagDao;
    }


    private List<Tag> checkValueListGiftCertificate(List<Tag> tagList)
            throws ServiceException {
        if (tagList.isEmpty()){
            printWarnMessage();
        }
        return tagList;
    }

    private Optional<Tag> checkValueOptionalGiftCertificate(Optional<Tag> tagOptional)
            throws ServiceException {
        if (!tagOptional.isPresent()){
            printWarnMessage();
        }
        return tagOptional;
    }

    private void printWarnMessage() throws ServiceException {
        log.warn(getMessageForLocale("tag.not.found"));
        throw new ServiceException(getMessageForLocale("tag.not.found"));
    }

    @Override
    public Tag create(Tag tag) throws ServiceException {
        Tag saveTag = tagDao.save(tag);
        if ( saveTag.getId() == 0){
            log.warn(getMessageForLocale("tag.not.create"));
            throw new ServiceException(getMessageForLocale("tag.not.create"));
        }
        return saveTag;
    }

    @Override
    public boolean removeById(long id) throws ServiceException {
        try {
            tagDao.deleteById(id);
        } catch (Exception e) {
            log.warn(getMessageForLocale("tag.not.delete"));
            throw new ServiceException(getMessageForLocale("tag.not.delete"));
        }
        return true;
    }

    @Override
    public List<Tag> findAll() throws ServiceException {
        List<Tag> tagList = tagDao.findAll();
        return checkValueListGiftCertificate(tagList);
    }

    @Override
    public Optional<Tag> findAllMostlyUsedTagByOrderPrice() throws ServiceException {
        List<Tag> tagList = tagDao.findAllMostlyUsedTagByOrderPrice();
        return checkValueOptionalGiftCertificate(tagList.stream().findFirst());
    }

    @Override
    public Optional<Tag> findById(long id) throws ServiceException {
        Optional<Tag> tagOptional = tagDao.findById(id);
        return checkValueOptionalGiftCertificate(tagOptional);
    }
}
