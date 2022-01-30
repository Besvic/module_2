/*
package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.GiftCertificateTagDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.exception.DaoException;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.validator.GiftCertificateValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceImplTest {

    @Mock
    private GiftCertificateDao giftCertificateDao;

    @Mock
    private TagDao tagDao;

    @Mock
    private GiftCertificateTagDao giftCertificateTagDao;

    @Mock
    private GiftCertificateValidator validator;

    @InjectMocks
    private GiftCertificateServiceImpl giftCertificateService;

    @Test
    void findAll() throws DaoException, ServiceException {
        when(giftCertificateDao.findAll()).thenReturn(Collections.singletonList(new GiftCertificate()));
        List<GiftCertificate> expected = Collections.singletonList(new GiftCertificate());
        List<GiftCertificate> actual = giftCertificateService.findAll();
        assertEquals(expected, actual);
    }

    @Test
    void findAllCertificateByTagName() throws DaoException, ServiceException {
        when(giftCertificateDao.findAllCertificateByTagList(anyString())).thenReturn(Collections.singletonList(new GiftCertificate()));
        List<GiftCertificate> expected = Collections.singletonList(new GiftCertificate());
        List<GiftCertificate> actual = giftCertificateService.findAllCertificateByTagName(anyString());
        assertEquals(expected, actual);
    }

    @Test
    void findAllCertificateByNameOrDescription() throws DaoException, ServiceException {
        when(giftCertificateDao.findAllByNameAndDescription(anyString(), anyString())).thenReturn(Collections.singletonList(new GiftCertificate()));
        List<GiftCertificate> expected = Collections.singletonList(new GiftCertificate());
        List<GiftCertificate> actual = giftCertificateService.findAllCertificateByNameOrDescription(anyString(), anyString());
        assertEquals(expected, actual);
    }

    @Test
    void findAllCertificateByDate() throws DaoException, ServiceException {
        when(validator.isSortedType(anyString())).thenReturn(true);
        when(giftCertificateDao.findAllCertificateByDate(anyString())).thenReturn(Collections.singletonList(new GiftCertificate()));
        List<GiftCertificate> expected = Collections.singletonList(new GiftCertificate());
        List<GiftCertificate> actual = giftCertificateService.findAllCertificateByDate(anyString());
        assertEquals(expected, actual);
    }

    @Test
    void findAllCertificateByName() throws DaoException, ServiceException {
        when(validator.isSortedType(anyString())).thenReturn(true);
        when(giftCertificateDao.findAllCertificateByName(anyString())).thenReturn(Collections.singletonList(new GiftCertificate()));
        List<GiftCertificate> expected = Collections.singletonList(new GiftCertificate());
        List<GiftCertificate> actual = giftCertificateService.findAllCertificateByName(anyString());
        assertEquals(expected, actual);
    }

    @Test
    void findById() throws DaoException, ServiceException {
        when(giftCertificateDao.findById(anyLong())).thenReturn(Optional.of(new GiftCertificate()));
        Optional<GiftCertificate> expected = Optional.of(new GiftCertificate());
        Optional<GiftCertificate> actual = giftCertificateService.findById(anyLong());
        assertEquals(expected, actual);
    }

    */
/*@Test
    void create() throws ServiceException, DaoException {
        GiftCertificate certificate = GiftCertificate.builder()
                .tagList(Collections.singletonList(new Tag(1, "name")))
                .build();
        when(giftCertificateDao.save(certificate)).thenReturn(1L);
        when(tagDao.create(new Tag(1, "name"))).thenReturn(1L);
        when(giftCertificateTagDao.addTagToCertificate(1L, Collections.singletonList(1L)))
                .thenReturn(new int[] {1});
        GiftCertificate actual = giftCertificateService.create(certificate);
        certificate.setId(1L);

        assertEquals(certificate, actual);
    }*//*

    // TODO: 25.01.2022 comment
    */
/*@Test
    void updateById() throws ServiceException, DaoException {
        GiftCertificate certificate = GiftCertificate.builder()
                .id(1L)
                .name("name")
                .description("description")
                .duration(1)
                .price(BigDecimal.valueOf(1))
                .build();
        when(giftCertificateDao.updateNameById("name", 1L)).thenReturn(true);
        when(giftCertificateDao.updateDescriptionById("description", 1L)).thenReturn(true);
        when(giftCertificateDao.updateDurationById(1, 1L)).thenReturn(true);
        when(giftCertificateDao.updatePriceById(BigDecimal.valueOf(1), 1L)).thenReturn(true);
        when(giftCertificateDao.findById(1L)).thenReturn(Optional.of(certificate));
        Optional<GiftCertificate> actual = giftCertificateService.updateById(certificate);
        assertEquals(certificate, actual.get());
    }*//*


    @Test
    void removeById() throws ServiceException, DaoException {
        when(giftCertificateDao.removeById(anyLong())).thenReturn(true);
        boolean actual = giftCertificateService.removeById(anyLong());
        assertTrue(actual);
    }

    // false result

    @Test
    void findAllFalse() throws DaoException {
        when(giftCertificateDao.findAll()).thenThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> giftCertificateService.findAll());
    }

    @Test
    void findAllCertificateByTagNameFalse() throws DaoException {
        when(giftCertificateDao.findAllCertificateByTagList(anyString())).thenThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> giftCertificateService.findAllCertificateByTagName(anyString()));
    }

    @Test
    void findAllCertificateByNameOrDescriptionFalse() throws DaoException {
        when(giftCertificateDao.findAllByNameAndDescription(anyString(), anyString())).thenThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> giftCertificateService.findAllCertificateByNameOrDescription(anyString(), anyString()));
    }

    @Test
    void findAllCertificateByDateFalse() throws DaoException {
        when(validator.isSortedType(anyString())).thenReturn(true);
        when(giftCertificateDao.findAllCertificateByDate(anyString())).thenThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> giftCertificateService.findAllCertificateByDate(anyString()));
    }

    @Test
    void findAllCertificateByNameFalse() throws DaoException {
        when(validator.isSortedType(anyString())).thenReturn(true);
        when(giftCertificateDao.findAllCertificateByName(anyString())).thenThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> giftCertificateService.findAllCertificateByName(anyString()));
    }

    @Test
    void findByIdFalse() throws DaoException {
        when(giftCertificateDao.findById(anyLong())).thenThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> giftCertificateService.findById(anyLong()));
    }

    */
/*@Test
    void createDaoExceptionInGiftCertificateCreate() throws DaoException {
        GiftCertificate certificate = GiftCertificate.builder()
                .tagList(Collections.singletonList(new Tag(1, "name")))
                .build();
        when(giftCertificateDao.create(certificate)).thenThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> giftCertificateService.create(certificate));
    }

    @Test
    void createDaoExceptionInTagCreate() throws DaoException {
        GiftCertificate certificate = GiftCertificate.builder()
                .tagList(Collections.singletonList(new Tag(1, "name")))
                .build();
        when(giftCertificateDao.create(certificate)).thenReturn(1L);
        when(tagDao.create(new Tag(1, "name"))).thenThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> giftCertificateService.create(certificate));
    }

    @Test
    void createDaoExceptionInGiftCertificateTagCreate() throws DaoException {
        GiftCertificate certificate = GiftCertificate.builder()
                .tagList(Collections.singletonList(new Tag(1, "name")))
                .build();
        when(giftCertificateDao.create(certificate)).thenReturn(1L);
        when(tagDao.create(new Tag(1, "name"))).thenReturn(1L);
        when(giftCertificateTagDao.addTagToCertificate(1L, Collections.singletonList(1L)))
                .thenThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> giftCertificateService.create(certificate));
    }*//*


   */
/* @Test
    void updateByIdThrowDaoExceptionInUpdateName() throws DaoException {
        GiftCertificate certificate = GiftCertificate.builder()
                .id(1L)
                .name("name")
                .description("description")
                .duration(1)
                .price(BigDecimal.valueOf(1))
                .build();
        when(giftCertificateDao.updateNameById("name", 1L)).thenThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> giftCertificateService.updateById(certificate));
    }

    @Test
    void updateByIdThrowDaoExceptionInUpdateDescription() throws DaoException {
        GiftCertificate certificate = GiftCertificate.builder()
                .id(1L)
                .name("name")
                .description("description")
                .duration(1)
                .price(BigDecimal.valueOf(1))
                .build();
        when(giftCertificateDao.updateNameById("name", 1L)).thenReturn(true);
        when(giftCertificateDao.updateDescriptionById("description", 1L)).thenThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> giftCertificateService.updateById(certificate));
    }

    @Test
    void updateByIdThrowDaoExceptionInUpdateDuration() throws DaoException {
        GiftCertificate certificate = GiftCertificate.builder()
                .id(1L)
                .name("name")
                .description("description")
                .duration(1)
                .price(BigDecimal.valueOf(1))
                .build();
        when(giftCertificateDao.updateNameById("name", 1L)).thenReturn(true);
        when(giftCertificateDao.updateDescriptionById("description", 1L)).thenReturn(true);
        when(giftCertificateDao.updateDurationById(1, 1L)).thenThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> giftCertificateService.updateById(certificate));
    }

    @Test
    void updateByIdThrowDaoExceptionInUpdatePrice() throws DaoException {
        GiftCertificate certificate = GiftCertificate.builder()
                .id(1L)
                .name("name")
                .description("description")
                .duration(1)
                .price(BigDecimal.valueOf(1))
                .build();
        when(giftCertificateDao.updateNameById("name", 1L)).thenReturn(true);
        when(giftCertificateDao.updateDescriptionById("description", 1L)).thenReturn(true);
        when(giftCertificateDao.updateDurationById(1, 1L)).thenReturn(true);
        when(giftCertificateDao.updatePriceById(BigDecimal.valueOf(1), 1L)).thenThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> giftCertificateService.updateById(certificate));
    }*//*


    @Test
    void removeByIdFalse() throws DaoException {
        when(giftCertificateDao.removeById(anyLong())).thenThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> giftCertificateService.removeById(anyLong()));
    }
}*/
