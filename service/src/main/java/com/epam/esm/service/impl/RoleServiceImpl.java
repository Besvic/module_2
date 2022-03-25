package com.epam.esm.service.impl;

import com.epam.esm.entity.Role;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.repository.RoleRepository;
import com.epam.esm.service.RoleService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import static com.epam.esm.util.LocalizedMessage.getMessageForLocale;

/**
 * The type Role service.
 */
@Service
@Log4j2
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    /**
     * Instantiates a new Role service.
     *
     * @param roleRepository the role repository
     */
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findByName(String name) throws ServiceException {
        return roleRepository.findByName(name).orElseThrow(() ->
                new ServiceException(getMessageForLocale("not.found.role.name") + name));
    }
}
