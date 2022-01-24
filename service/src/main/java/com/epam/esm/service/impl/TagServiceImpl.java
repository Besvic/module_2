package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.exception.DaoException;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.epam.esm.config.LocalizedMessage.getMessageForLocale;

/**
 * The type Tag service.
 */
@Slf4j
@Service
public class TagServiceImpl implements TagService {

    private final TagDao tagDao;

    /**
     * Instantiates a new Tag service.
     *
     * @param tagDao the tag dao
     */
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
    public long create(Tag tag) throws ServiceException {
        try {
            long tagId = tagDao.create(tag);
            if ( tagId == 0){
                log.warn(getMessageForLocale("tag.not.create"));
                throw new ServiceException(getMessageForLocale("tag.not.create"));
            }
            return tagId;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean removeById(long id) throws ServiceException {
        try {
            if (!tagDao.removeById(id)){
                log.warn(getMessageForLocale("tag.not.delete"));
                throw new ServiceException(getMessageForLocale("tag.not.delete"));
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return true;
    }

    @Override
    public List<Tag> findAll() throws ServiceException {
        try {
            List<Tag> tagList = tagDao.findAll();
            return checkValueListGiftCertificate(tagList);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<Tag> findById(long id) throws ServiceException {
        try {
            Optional<Tag> tagOptional = tagDao.findById(id);
            return checkValueOptionalGiftCertificate(tagOptional);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
