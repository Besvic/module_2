package com.epam.esm.dto.converter;

import com.epam.esm.util.MapperUtil;
import com.epam.esm.dto.entity.UserDTO;
import com.epam.esm.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


/**
 * The type User converter.
 */
@Component
public class UserConverter {

    private final ModelMapper modelMapper;
    private final OrderLazyConverter orderLazyConverter;
    private final RoleConverter roleConverter;

    /**
     * Instantiates a new User converter.
     *
     * @param modelMapper        the model mapper
     * @param orderLazyConverter the order lazy converter
     * @param roleConverter      the role converter
     */
    public UserConverter(ModelMapper modelMapper,
                         OrderLazyConverter orderLazyConverter,
                         RoleConverter roleConverter) {
        this.modelMapper = modelMapper;
        this.orderLazyConverter = orderLazyConverter;
        this.roleConverter = roleConverter;
    }

    /**
     * Convert to user dto user dto.
     *
     * @param user the user
     * @return the user dto
     */
    public UserDTO convertToUserDTO(User user){
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        userDTO.setRoleDTOList(
                MapperUtil.convertList(user.getRoleList(), roleConverter::convertToRoleDTO)
        );
        if (user.getOrderList() != null){
            userDTO.setOrderLazyDTOList(
                    MapperUtil.convertList(user.getOrderList(), orderLazyConverter::convertToOrderDTO)
            );
        }
        return userDTO;
    }

    /**
     * Convert to user user.
     *
     * @param userDTO the user dto
     * @return the user
     */
    public User convertToUser(UserDTO userDTO){
        User user = modelMapper.map(userDTO, User.class);
        user.setOrderList(
                MapperUtil.convertList(userDTO.getOrderLazyDTOList(), orderLazyConverter::convertToOrder)
        );
        user.setRoleList(
                MapperUtil.convertList(userDTO.getRoleDTOList(), roleConverter::convertToRole)
        );
       return user;
    }

}
