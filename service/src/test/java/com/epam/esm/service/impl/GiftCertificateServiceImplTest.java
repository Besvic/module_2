package com.epam.esm.service.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.validator.GiftCertificateValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceImplTest {

    @Mock
    private GiftCertificateRepository certificateRepository;

    @Mock
    private TagRepository tagRepository;

    @Mock
    private Page<GiftCertificate> certificatePage;

    @Mock
    private Pageable pageable;

    @Mock
    private GiftCertificateValidator validator;

    @InjectMocks
    private GiftCertificateServiceImpl certificateService;

    @Test
    void findAll() throws ServiceException {
        when(certificateRepository.findAll(pageable)).thenReturn(certificatePage);
        Page<GiftCertificate> expected = certificatePage;
        Page<GiftCertificate> actual = certificateService.findAll(pageable);
        assertEquals(expected, actual);
    }

    @Test
    void findAllEmpty() {
        when(certificateRepository.findAll(pageable)).thenReturn(Page.empty());
        assertThrows(ServiceException.class, () -> certificateService.findAll(pageable));
    }

    @Test
    void findAllCertificateByTagName() throws ServiceException {
        String str = "str";
        when(certificateRepository.findGiftCertificatesByTagList_Name(str, pageable)).thenReturn(certificatePage);
        Page<GiftCertificate> expected = certificatePage;
        Page<GiftCertificate> actual = certificateService.findAllCertificateByTagName(str, pageable);
        assertEquals(expected, actual);
    }

    @Test
    void findAllCertificateByNameOrDescription() throws ServiceException {
        String str = "str";
        when(certificateRepository.findAllByNameContainingOrDescriptionContaining(str, str, pageable))
                .thenReturn(certificatePage);
        Page<GiftCertificate> expected = certificatePage;
        Page<GiftCertificate> actual = certificateService.findAllCertificateByNameOrDescription(str, str, pageable);
        assertEquals(expected, actual);
    }

    @Test
    void findAllCertificateByDateAsc() throws ServiceException {
        when(validator.isSortedType(anyString())).thenReturn(true);
        when(certificateRepository.findAllByOrderByCreateDateAsc(pageable))
                .thenReturn(certificatePage);
        Page<GiftCertificate> expected = certificatePage;
        Page<GiftCertificate> actualAsc = certificateService.findAllCertificateByDate(Sort.Direction.ASC.name(), pageable);
        assertEquals(expected, actualAsc);
    }

    @Test
    void findAllCertificateByDateDesc() throws ServiceException {
        when(validator.isSortedType(anyString())).thenReturn(true);
        when(certificateRepository.findAllByOrderByCreateDateDesc(pageable))
                .thenReturn(certificatePage);
        Page<GiftCertificate> expected = certificatePage;
        Page<GiftCertificate> actualDesc = certificateService.findAllCertificateByDate(Sort.Direction.DESC.name(), pageable);
        assertEquals(expected, actualDesc);
    }

    @Test
    void findAllCertificateByDateIncorrectType() {
        String type = "incorrect type";
        when(validator.isSortedType(anyString())).thenReturn(false);
        assertThrows(ServiceException.class, () -> certificateService.findAllCertificateByDate(type, pageable));
    }

    @Test
    void findAllCertificateByNameAsc() throws ServiceException {
        when(validator.isSortedType(anyString())).thenReturn(true);
        when(certificateRepository.findAllByOrderByNameAsc(pageable))
                .thenReturn(certificatePage);
        Page<GiftCertificate> expected = certificatePage;
        Page<GiftCertificate> actualAsc = certificateService.findAllCertificateByName(Sort.Direction.ASC.name(), pageable);
        assertEquals(expected, actualAsc);
    }

    @Test
    void findAllCertificateByNameDesc() throws ServiceException {
        when(validator.isSortedType(anyString())).thenReturn(true);
        when(certificateRepository.findAllByOrderByNameDesc(pageable))
                .thenReturn(certificatePage);
        Page<GiftCertificate> expected = certificatePage;
        Page<GiftCertificate> actualDesc = certificateService.findAllCertificateByName(Sort.Direction.DESC.name(), pageable);
        assertEquals(expected, actualDesc);
    }

    @Test
    void findAllByTagIdList() throws ServiceException {
        String strId = "1,2";
        String[] strTagId = {"1", "2"};
        List<Long> tagIdList = new ArrayList<>();
        when(validator.isTagId(strTagId)).thenReturn(true);
        when(certificateRepository.findAllByTagListId(tagIdList, pageable)).thenReturn(certificatePage);
        Arrays.stream(strTagId).forEach(s -> tagIdList.add(Long.valueOf(s)));
        Page<GiftCertificate> expected = certificatePage;
        Page<GiftCertificate> actual = certificateService.findAllByTagIdList(strId, pageable);
        assertEquals(expected, actual);
    }

    @Test
    void findAllByTagIdListInvalid() {
        String strId = "1,2";
        String[] strTagId = {"1", "2"};
        when(validator.isTagId(strTagId)).thenReturn(false);
        assertThrows(ServiceException.class, () -> certificateService.findAllByTagIdList(strId, pageable));
    }

    @Test
    void findById() throws ServiceException {
        GiftCertificate certificate = GiftCertificate.builder().name("name").build();
        when(certificateRepository.findById(anyLong())).thenReturn(Optional.of(certificate));
        GiftCertificate actual = certificateService.findById(anyLong());
        assertEquals(certificate, actual);
    }

    @Test
    void findByIdEmpty()  {
        when(certificateRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ServiceException.class, () -> certificateService.findById(anyLong()));
    }

    @Test
    void createWithPresentTag() {
        Tag firstTag = new Tag(1, "first");
        GiftCertificate certificate = GiftCertificate.builder().tagList(Collections.singletonList(firstTag)).build();
        when(certificateRepository.saveAndFlush(certificate)).thenReturn(certificate);
        when(tagRepository.existsByName(firstTag.getName())).thenReturn(true);
        GiftCertificate actual = certificateService.create(certificate);
        certificate.setLastUpdateDate(actual.getLastUpdateDate());
        certificate.setCreateDate(actual.getCreateDate());
        assertEquals(certificate, actual);
    }

    @Test
    void createWithNewTag() {
        Tag firstTag = new Tag(1, "first");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(firstTag);
        GiftCertificate certificate = GiftCertificate.builder().tagList(tagList).build();
        when(tagRepository.existsByName(firstTag.getName())).thenReturn(false);
        when(tagRepository.saveAndFlush(firstTag)).thenReturn(firstTag);
        when(certificateRepository.saveAndFlush(certificate)).thenReturn(certificate);
        GiftCertificate actual = certificateService.create(certificate);
        certificate.setLastUpdateDate(actual.getLastUpdateDate());
        certificate.setCreateDate(actual.getCreateDate());
        assertEquals(certificate, actual);
    }

@Test
    void updateById() throws ServiceException {
        GiftCertificate certificate = GiftCertificate.builder()
                .id(1L)
                .name("name")
                .description("description")
                .duration(1)
                .price(BigDecimal.valueOf(1))
                .tagList(Collections.singletonList(new Tag(1L, "name")))
                .build();
        when(certificateRepository.findById(1L)).thenReturn(Optional.of(GiftCertificate.builder().id(1L).build()));
        when(certificateRepository.saveAndFlush(certificate)).thenReturn(certificate);
        GiftCertificate actual = certificateService.updateById(certificate);
        assertEquals(certificate, actual);
    }

    @Test
    void updateByIdThrowRuntimeException() {
        GiftCertificate certificate = GiftCertificate.builder()
                .id(1L)
                .name("name")
                .description("description")
                .duration(1)
                .price(BigDecimal.valueOf(1))
                .tagList(Collections.singletonList(new Tag(1L, "name")))
                .build();
        when(certificateRepository.findById(1L)).thenReturn(Optional.of(GiftCertificate.builder().id(1L).build()));
        when(certificateRepository.saveAndFlush(certificate)).thenThrow(RuntimeException.class);
        assertThrows(ServiceException.class, () -> certificateService.updateById(certificate));
    }

    @Test
    void removeById() throws ServiceException {
        boolean actual = certificateService.removeById(anyLong());
        assertTrue(actual);
    }

    @Test
    void removeByIdThrowRuntimeException() {
        doThrow(ServiceException.class).when(certificateRepository).deleteById(anyLong());
        assertThrows(ServiceException.class, () -> certificateService.removeById(anyLong()));
    }
}
