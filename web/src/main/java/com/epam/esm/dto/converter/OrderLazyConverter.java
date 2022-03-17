package com.epam.esm.dto.converter;

import com.epam.esm.dto.entity.OrderLazyDTO;
import com.epam.esm.entity.Order;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * The type Order lazy converter.
 */
@Component
public class OrderLazyConverter {

    private final ModelMapper modelMapper;

    /**
     * Instantiates a new Order lazy converter.
     *
     * @param modelMapper the model mapper
     */
    public OrderLazyConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    /**
     * Convert to order dto order lazy dto.
     *
     * @param order the order
     * @return the order lazy dto
     */
    public OrderLazyDTO convertToOrderDTO(Order order){
        return modelMapper.map(order, OrderLazyDTO.class);
    }

    /**
     * Convert to order order.
     *
     * @param orderLazyDTO the order lazy dto
     * @return the order
     */
    public Order convertToOrder(OrderLazyDTO orderLazyDTO){
        return modelMapper.map(orderLazyDTO, Order.class);
    }
}
