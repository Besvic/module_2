package com.epam.esm.dto.converter;

import com.epam.esm.util.MapperUtil;
import com.epam.esm.dto.entity.GiftCertificateDTO;
import com.epam.esm.entity.GiftCertificate;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * The type Gift certificate converter.
 */
@Component
public class GiftCertificateConverter {

    private final ModelMapper modelMapper;
    private final TagConverter tagConverter;

    /**
     * Instantiates a new Gift certificate converter.
     *
     * @param modelMapper  the model mapper
     * @param tagConverter the tag converter
     */
    public GiftCertificateConverter(ModelMapper modelMapper, TagConverter tagConverter) {
        this.modelMapper = modelMapper;
        this.tagConverter = tagConverter;
    }

    /**
     * Convert to gift certificate dto gift certificate dto.
     *
     * @param giftCertificate the gift certificate
     * @return the gift certificate dto
     */
    public GiftCertificateDTO convertToGiftCertificateDTO(GiftCertificate giftCertificate){
        GiftCertificateDTO giftCertificateDTO = modelMapper.map(giftCertificate, GiftCertificateDTO.class);
        giftCertificateDTO.setTagDTOList(
                MapperUtil.convertList(giftCertificate.getTagList(), tagConverter::convertToTagDTO)
        );
        return giftCertificateDTO;
    }

    /**
     * Convert to gift certificate gift certificate.
     *
     * @param giftCertificateDTO the gift certificate dto
     * @return the gift certificate
     */
    public GiftCertificate convertToGiftCertificate(GiftCertificateDTO giftCertificateDTO){
        GiftCertificate giftCertificate = modelMapper.map(giftCertificateDTO, GiftCertificate.class);
        giftCertificate.setTagList(
                MapperUtil.convertList(giftCertificateDTO.getTagDTOList(), tagConverter::converterToTag)
        );
        return giftCertificate;
    }
}
