package com.epam.esm.service.impl;

import com.epam.esm.repository.TagRepository;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.epam.esm.util.LocalizedMessage.getMessageForLocale;

/**
 * The type Tag service.
 */
@Slf4j
@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    /**
     * Instantiates a new Tag service.
     *
     * @param tagRepository the tag repository
     */
    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }


    private Page<Tag> checkValueListGiftCertificate(Page<Tag> tagList)
            throws ServiceException {
        if (tagList.isEmpty()){
            printWarnMessage();
        }
        return tagList;
    }

    private Tag checkValueOptionalGiftCertificate(Optional<Tag> tagOptional)
            throws ServiceException {
        if (!tagOptional.isPresent()){
            printWarnMessage();
        }
        return tagOptional.orElse(new Tag());
    }

    private void printWarnMessage() throws ServiceException {
        log.warn(getMessageForLocale("tag.not.found"));
        throw new ServiceException(getMessageForLocale("tag.not.found"));
    }

    @Override
    public Tag create(Tag tag) throws ServiceException {
        Tag saveTag = tagRepository.save(tag);
        if (saveTag.getId() == 0){
            log.warn(getMessageForLocale("tag.not.create"));
            throw new ServiceException(getMessageForLocale("tag.not.create"));
        }
        return saveTag;
    }

    @Override
    public boolean removeById(long tagId) throws ServiceException {
        try {
            tagRepository.deleteById(tagId);
        } catch (Exception e) {
            log.warn(getMessageForLocale("tag.not.delete"));
            throw new ServiceException(getMessageForLocale("tag.not.delete"));
        }
        return true;
    }

    @Override
    public Page<Tag> findAll(Pageable pageable) throws ServiceException {
        Page<Tag> tagList = tagRepository.findAll(pageable);
        return checkValueListGiftCertificate(tagList);
    }

    @Override
    public Tag findAllMostlyUsedTagByOrderPrice(long userId) throws ServiceException {
        List<Tag> tagList = tagRepository.findAllMostlyUsedTagByOrderPrice(userId);
        return checkValueOptionalGiftCertificate(tagList.stream().findFirst());
    }

    @Override
    public Tag findById(long tagId) throws ServiceException {
        Optional<Tag> tagOptional = tagRepository.findById(tagId);
        return checkValueOptionalGiftCertificate(tagOptional);
    }
}
