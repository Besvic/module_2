package com.epam.esm.dto.converter;

import com.epam.esm.dto.entity.SignUpRequestDTO;
import com.epam.esm.pojo.security.SignUpRequest;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * The type Sign up request converter.
 */
@Component
public class SignUpRequestConverter {

    private final ModelMapper modelMapper;

    /**
     * Instantiates a new Sign up request converter.
     *
     * @param modelMapper the model mapper
     */
    public SignUpRequestConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    /**
     * Convert to request sign up request.
     *
     * @param requestDTO the request dto
     * @return the sign up request
     */
    public SignUpRequest convertToRequest(SignUpRequestDTO requestDTO){
        return modelMapper.map(requestDTO, SignUpRequest.class);
    }

    /**
     * Convert to request dto sign up request dto.
     *
     * @param request the request
     * @return the sign up request dto
     */
    public SignUpRequestDTO convertToRequestDTO(SignUpRequest request){
        return modelMapper.map(request, SignUpRequestDTO.class);
    }
}
