package com.epam.esm.dto.converter;

import com.epam.esm.dto.entity.RoleDTO;
import com.epam.esm.entity.Role;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * The type Role converter.
 */
@Component
public class RoleConverter {

    private final ModelMapper modelMapper;

    /**
     * Instantiates a new Role converter.
     *
     * @param modelMapper the model mapper
     */
    public RoleConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    /**
     * Convert to role role.
     *
     * @param roleDTO the role dto
     * @return the role
     */
    public Role convertToRole(RoleDTO roleDTO){
        return modelMapper.map(roleDTO, Role.class);
    }

    /**
     * Convert to role dto role dto.
     *
     * @param role the role
     * @return the role dto
     */
    public RoleDTO convertToRoleDTO(Role role){
        return modelMapper.map(role, RoleDTO.class);
    }
}
