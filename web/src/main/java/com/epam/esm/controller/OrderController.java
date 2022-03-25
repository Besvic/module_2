package com.epam.esm.controller;

import com.epam.esm.util.MapperUtil;
import com.epam.esm.dto.converter.OrderConverter;
import com.epam.esm.dto.entity.GiftCertificateDTO;
import com.epam.esm.dto.entity.OrderDTO;
import com.epam.esm.dto.entity.TagDTO;
import com.epam.esm.exception.ControllerException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.OrderService;
import lombok.var;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * The type Order controller.
 */
@Validated
@RestController
@RequestMapping(value = "/orders", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {

    private final OrderService orderService;
    private final OrderConverter orderConverter;

    /**
     * Instantiates a new Order controller.
     *
     * @param orderService   the order service
     * @param orderConverter the order converter
     */
    public OrderController(OrderService orderService,
                           OrderConverter orderConverter) {
        this.orderService = orderService;
        this.orderConverter = orderConverter;
    }

    /**
     * Find all response entity.
     *
     * @param pageable           the pageable
     * @param resourcesAssembler the resources assembler
     * @return the response entity
     * @throws ControllerException the controller exception
     */
    @GetMapping()
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<PagedModel<EntityModel<OrderDTO>>> findAll(Pageable pageable, PagedResourcesAssembler<OrderDTO> resourcesAssembler) throws ControllerException {
        Page<OrderDTO> orderDTOS;
        PagedModel<EntityModel<OrderDTO>> pagedModel;
        try {
            orderDTOS = MapperUtil
                    .convertList(orderService.findAll(pageable), orderConverter::convertToOrderDTO);
            for (var i : orderDTOS) {
                i.add(linkTo(methodOn(OrderController.class).findById(i.getId())).withSelfRel().withType(HttpMethod.GET.name()));
                initializeCertificateDTOList(i.getCertificateDTOList());
            }
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }
        pagedModel = resourcesAssembler.toModel(orderDTOS);
        return new ResponseEntity<>(pagedModel, HttpStatus.OK);
    }

    /**
     * Find by id response entity.
     *
     * @param orderId the order id
     * @return the response entity
     * @throws ControllerException the controller exception
     */
    @GetMapping("/{order_id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<OrderDTO> findById(@PathVariable("order_id") long orderId) throws ControllerException {
        OrderDTO orderDTO;
        try {
            orderDTO = orderConverter.convertToOrderDTO(orderService.findById(orderId));
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }
        return new ResponseEntity<>(orderDTO, HttpStatus.OK);
    }

    private void initializeCertificateDTOList(List<GiftCertificateDTO> certificateDTOS) throws ControllerException {
        for (var i: certificateDTOS) {
            i.add(linkTo(methodOn(GiftCertificateController.class).findById(i.getId())).withSelfRel().withType(HttpMethod.GET.name()))
                    .add(linkTo(methodOn(GiftCertificateController.class).removeById(i.getId())).withSelfRel().withType(HttpMethod.DELETE.name()))
                    .add(linkTo(GiftCertificateController.class).slash(i.getId()).withSelfRel().withType(HttpMethod.PATCH.name()));
            initializeTagDTOListWithLink(i.getTagDTOList());
        }
    }

    private void initializeTagDTOListWithLink(List<TagDTO> tagDTOPage) throws ControllerException {
        String tag = "tag";
        for (var i: tagDTOPage) {
            i.add(linkTo(methodOn(TagController.class).findById(i.getId())).withRel(tag).withType(HttpMethod.GET.name()))
                    .add(linkTo(methodOn(TagController.class).remove(i.getId())).withRel(tag).withType(HttpMethod.DELETE.name()));
        }
    }
}
