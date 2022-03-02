package com.epam.esm.dto.converter;

import com.epam.esm.util.MapperUtil;
import com.epam.esm.dto.entity.OrderDTO;
import com.epam.esm.entity.Order;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * The type Order converter.
 */
@Component
public class OrderConverter {

    private final ModelMapper modelMapper;
    private final GiftCertificateConverter certificateConverter;

    /**
     * Instantiates a new Order converter.
     *
     * @param modelMapper          the model mapper
     * @param certificateConverter the certificate converter
     */
    public OrderConverter(ModelMapper modelMapper, GiftCertificateConverter certificateConverter) {
        this.modelMapper = modelMapper;
        this.certificateConverter = certificateConverter;
    }

    /**
     * Convert to order dto order dto.
     *
     * @param order the order
     * @return the order dto
     */
    public OrderDTO convertToOrderDTO(Order order){
        OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);
        orderDTO.setCertificateDTOList(MapperUtil.convertList(order.getCertificateList(), certificateConverter::convertToGiftCertificateDTO));
        return orderDTO;
    }

    /**
     * Convert to order order.
     *
     * @param orderDTO the order dto
     * @return the order
     */
    public Order convertToOrder(OrderDTO orderDTO){
        Order order = modelMapper.map(orderDTO, Order.class);
        order.setCertificateList(MapperUtil.convertList(orderDTO.getCertificateDTOList(), certificateConverter::convertToGiftCertificate));
        return order;
    }
}
