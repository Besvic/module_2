package com.epam.esm.service.impl;

import com.epam.esm.entity.User;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private Page<User> userPage;

    @Mock
    private Pageable pageable;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void create() throws ServiceException {
        User user = User.builder().build();
        when(userRepository.save(user)).thenReturn(user);
        User actual = userService.create(user);
        assertEquals(user, actual);
    }

    @Test
    void createThrowRuntimeException() {
        User user = User.builder().build();
        when(userRepository.save(user)).thenThrow(RuntimeException.class);
        assertThrows(ServiceException.class, () -> userService.create(user));
    }

    @Test
    void findById() throws ServiceException {
        User user = User.builder().build();
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(user));
        User actual = userService.findById(anyLong());
        assertEquals(user, actual);
    }

    @Test
    void findByIdEmpty() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ServiceException.class, () -> userService.findById(anyLong()));
    }

    @Test
    void findAll() throws ServiceException {
        when(userRepository.findAll(pageable)).thenReturn(userPage);
        Page<User> actual = userService.findAll(pageable);
        assertEquals(userPage, actual);
    }

    @Test
    void findAllEmpty() {
        when(userRepository.findAll(pageable)).thenReturn(Page.empty());
        assertThrows(ServiceException.class, () -> userService.findAll(pageable));
    }

    @Test
    void findAllByName() throws ServiceException {
        when(userRepository.findAllByNameContaining("name", pageable)).thenReturn(userPage);
        Page<User> actual = userService.findAllByName("name", pageable);
        assertEquals(userPage, actual);
    }

    @Test
    void findAllByNameEmpty() {
        when(userRepository.findAllByNameContaining("name", pageable)).thenReturn(Page.empty());
        assertThrows(ServiceException.class, () -> userService.findAllByName("name", pageable));
    }

    @Test
    void deleteById() throws ServiceException {
        long actual = userService.deleteById(1L);
        assertEquals(1L, actual);
    }

    @Test
    void deleteByIdThrowRuntimeException() throws ServiceException {
        doThrow(RuntimeException.class).when(userRepository).deleteById(1L);
        assertThrows(ServiceException.class, () -> userService.deleteById(1L));
    }
}