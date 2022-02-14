package com.epam.esm.dto.converter;

import com.epam.esm.dto.entity.TagDTO;
import com.epam.esm.entity.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * The type Tag converter.
 */
@Component
public class TagConverter {

    private final ModelMapper modelMapper;

    /**
     * Instantiates a new Tag converter.
     *
     * @param modelMapper the model mapper
     */
    public TagConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    /**
     * Convert to tag dto tag dto.
     *
     * @param tag the tag
     * @return the tag dto
     */
    public TagDTO convertToTagDTO(Tag tag){
        return modelMapper.map(tag, TagDTO.class);
    }

    /**
     * Converter to tag tag.
     *
     * @param tagDTO the tag dto
     * @return the tag
     */
    public Tag converterToTag(TagDTO tagDTO){
        return modelMapper.map(tagDTO, Tag.class);
    }

}
