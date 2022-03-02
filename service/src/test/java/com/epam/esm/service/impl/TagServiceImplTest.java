package com.epam.esm.service.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.repository.TagRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

    @Mock
    private TagRepository tagDao;

    @Mock
    private Page<Tag> tagPage;

    @Mock
    private Pageable pageable;

    @InjectMocks
    private TagServiceImpl tagService;

    @Test
    void create() throws ServiceException {
        Tag tag = new Tag(1, "name");
        when(tagDao.save(tag)).thenReturn(tag);
        Tag actual = tagService.create(tag);
        assertEquals(tag, actual);
    }

    @Test
    void createThrowServiceException() {
        Tag tag = new Tag(0, "name");
        when(tagDao.save(tag)).thenReturn(tag);
        assertThrows(ServiceException.class, () -> tagService.create(tag));
    }

    @Test
    void removeById() throws ServiceException {
        boolean actual = tagService.removeById(anyLong());
        assertTrue(actual);
    }

    @Test
    void removeByIdThrowRuntimeException() {
        doThrow(RuntimeException.class).when(tagDao).deleteById(anyLong());
        assertThrows(ServiceException.class, () -> tagService.removeById(anyLong()));
    }

    @Test
    void findAll() throws ServiceException {
        when(tagDao.findAll(pageable)).thenReturn(tagPage);
        Page<Tag> expected = tagPage;
        Page<Tag> actual = tagService.findAll(pageable);
        assertEquals(expected, actual);
    }

    @Test
    void findAllEmpty() {
        when(tagDao.findAll(pageable)).thenReturn(Page.empty());
        assertThrows(ServiceException.class, () -> tagService.findAll(pageable));
    }

    @Test
    void findAllMostlyUsedTagByOrderPrice() throws ServiceException {
        Tag tag = new Tag(1, "name");
        when(tagDao.findAllMostlyUsedTagByOrderPrice(1L)).thenReturn(Collections.singletonList(tag));
        Tag actual = tagService.findAllMostlyUsedTagByOrderPrice(1L);
        assertEquals(tag, actual);
    }

    @Test
    void findById() throws ServiceException {
        Tag tag = new Tag();
        when(tagDao.findById(anyLong())).thenReturn(Optional.of(tag));
        Tag actual = tagService.findById(anyLong());
        assertEquals(tag, actual);
    }

    @Test
    void findByIdEmpty() {
        when(tagDao.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ServiceException.class, () -> tagService.findById(anyLong()));
    }
}
