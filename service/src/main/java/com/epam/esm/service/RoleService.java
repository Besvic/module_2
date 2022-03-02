package com.epam.esm.service;

import com.epam.esm.entity.Role;
import com.epam.esm.exception.ServiceException;

/**
 * The interface Role service.
 */
public interface RoleService {

    /**
     * Find by name role.
     *
     * @param name the name
     * @return the role
     * @throws ServiceException the service exception
     */
    Role findByName(String name) throws ServiceException;
}
