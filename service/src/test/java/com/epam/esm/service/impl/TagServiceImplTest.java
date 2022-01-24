package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.exception.DaoException;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ServiceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

    @Mock
    private TagDao tagDao;

    @InjectMocks
    private TagServiceImpl tagService;

    @Test
    void create() throws ServiceException, DaoException {
        when(tagDao.create(new Tag())).thenReturn(1L);
        long actual = tagService.create(new Tag());
        assertEquals(1L, actual);
    }

    @Test
    void removeById() throws ServiceException, DaoException {
        when(tagDao.removeById(anyLong())).thenReturn(true);
        boolean actual = tagService.removeById(anyLong());
        assertTrue(actual);
    }

    @Test
    void findAll() throws ServiceException, DaoException {
        when(tagDao.findAll()).thenReturn(Collections.singletonList(new Tag()));
        List<Tag> expected = Collections.singletonList(new Tag());
        List<Tag> actual = tagService.findAll();
        assertEquals(expected, actual);
    }

    @Test
    void findById() throws ServiceException, DaoException {
        when(tagDao.findById(anyLong())).thenReturn(Optional.of(new Tag()));
        Optional<Tag> expected = Optional.of(new Tag());
        Optional<Tag> actual = tagService.findById(anyLong());
        assertEquals(expected, actual);
    }

    // false result

    @Test
    void createFalse() throws DaoException {
        when(tagDao.create(new Tag())).thenReturn(0L);
        assertThrows(ServiceException.class, () -> tagService.create(new Tag()));
    }

    @Test
    void removeByIdFalse() throws DaoException {
        when(tagDao.removeById(anyLong())).thenReturn(false);
        assertThrows(ServiceException.class, () -> tagService.removeById(anyLong()));
    }

    @Test
    void findAllFalse() throws DaoException {
        when(tagDao.findAll()).thenReturn(new ArrayList<>());
        assertThrows(ServiceException.class, () -> tagService.findAll());
    }

    @Test
    void findByIdFalse() throws DaoException {
        when(tagDao.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ServiceException.class, () -> tagService.findById(anyLong()));
    }

    // throw exception result

    @Test
    void createThrowException() throws DaoException {
        when(tagDao.create(new Tag())).thenThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> tagService.create(new Tag()));
    }

    @Test
    void removeByIdThrowException() throws DaoException {
        when(tagDao.removeById(anyLong())).thenThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> tagService.removeById(anyLong()));
    }

    @Test
    void findAllThrowException() throws DaoException {
        when(tagDao.findAll()).thenThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> tagService.findAll());
    }

    @Test
    void findByIdThrowException() throws DaoException {
        when(tagDao.findById(anyLong())).thenThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> tagService.findById(anyLong()));
    }
}