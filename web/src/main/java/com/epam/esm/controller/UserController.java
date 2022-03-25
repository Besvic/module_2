package com.epam.esm.controller;

import com.epam.esm.util.MapperUtil;
import com.epam.esm.dto.converter.OrderConverter;
import com.epam.esm.dto.converter.OrderLazyConverter;
import com.epam.esm.dto.converter.TagConverter;
import com.epam.esm.dto.converter.UserConverter;
import com.epam.esm.dto.entity.*;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.exception.ControllerException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.TagService;
import com.epam.esm.service.UserService;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * The type User controller.
 */
@RestController
@RequestMapping(value = "/users",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class UserController {

    private final UserService userService;
    private final OrderService orderService;
    private final UserConverter userConverter;
    private final OrderLazyConverter orderLazyConverter;
    private final OrderConverter orderConverter;
    private final TagConverter tagConverter;
    private final TagService tagService;

    /**
     * Instantiates a new User controller.
     *
     * @param userService        the user service
     * @param orderService       the order service
     * @param userConverter      the user converter
     * @param orderLazyConverter the order lazy converter
     * @param orderConverter     the order converter
     * @param tagConverter       the tag converter
     * @param tagService         the tag service
     */
    public UserController(UserService userService,
                          OrderService orderService,
                          UserConverter userConverter,
                          OrderLazyConverter orderLazyConverter,
                          OrderConverter orderConverter,
                          TagConverter tagConverter,
                          TagService tagService){
        this.userService = userService;
        this.orderService = orderService;
        this.userConverter = userConverter;
        this.orderLazyConverter = orderLazyConverter;
        this.orderConverter = orderConverter;
        this.tagConverter = tagConverter;
        this.tagService = tagService;
    }

    /**
     * Find all response entity.
     *
     * @param pageable              the pageable
     * @param resourcesUserDTO      the resources user dto
     * @param resourcesOrderDTO     the resources order dto
     * @param resourcesOrderLazyDTO the resources order lazy dto
     * @return the response entity
     * @throws ControllerException the controller exception
     */
    @GetMapping()
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<PagedModel<EntityModel<UserDTO>>> findAll(Pageable pageable,
                                            PagedResourcesAssembler<UserDTO> resourcesUserDTO,
                                            PagedResourcesAssembler<OrderDTO> resourcesOrderDTO,
                                            PagedResourcesAssembler<OrderLazyDTO> resourcesOrderLazyDTO) throws ControllerException {
        Page<UserDTO> userDTOS;
        PagedModel<EntityModel<UserDTO>> pagedModel;
        try {
            userDTOS = MapperUtil
                    .convertList(userService.findAll(pageable), userConverter::convertToUserDTO);
            initializeUserDTOListWithLinks(userDTOS, pageable, resourcesOrderDTO, resourcesOrderLazyDTO);
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }
        pagedModel = resourcesUserDTO.toModel(userDTOS);
        initializePageModel(pagedModel);
        return new ResponseEntity<>(pagedModel, HttpStatus.OK);
    }

    /**
     * Search by name response entity.
     *
     * @param name                  the name
     * @param pageable              the pageable
     * @param resourcesUserDTO      the resources user dto
     * @param resourcesOrderDTO     the resources order dto
     * @param resourcesOrderLazyDTO the resources order lazy dto
     * @return the response entity
     * @throws ControllerException the controller exception
     */
    @GetMapping("/search/name")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<PagedModel<EntityModel<UserDTO>>> searchByName(@RequestParam(value = "name") String name,
                                            Pageable pageable, PagedResourcesAssembler<UserDTO> resourcesUserDTO,
                                            PagedResourcesAssembler<OrderDTO> resourcesOrderDTO,
                                            PagedResourcesAssembler<OrderLazyDTO> resourcesOrderLazyDTO) throws ControllerException {
        Page<UserDTO> userDTOS;
        PagedModel<EntityModel<UserDTO>> pagedModel;
        try {
            userDTOS = MapperUtil
                    .convertList(userService.findAllByName(name, pageable), userConverter::convertToUserDTO);
           initializeUserDTOListWithLinks(userDTOS, pageable, resourcesOrderDTO, resourcesOrderLazyDTO);
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }
        pagedModel = resourcesUserDTO.toModel(userDTOS);
        initializePageModel(pagedModel);
        return new ResponseEntity<>(pagedModel, HttpStatus.OK);

    }

    /**
     * Find by id response entity.
     *
     * @param userId                the user id
     * @param pageable              the pageable
     * @param resourcesOrderDTO     the resources order dto
     * @param resourcesOrderLazyDTO the resources order lazy dto
     * @return the response entity
     * @throws ControllerException the controller exception
     */
    @GetMapping(value = "/{user_id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<UserDTO> findById(@PathVariable("user_id") long userId,
                        Pageable pageable, PagedResourcesAssembler<OrderDTO> resourcesOrderDTO,
                                            PagedResourcesAssembler<OrderLazyDTO> resourcesOrderLazyDTO) throws ControllerException {
        UserDTO user;
        try {
            user = userConverter.convertToUserDTO(userService.findById(userId));
            initializeUserDTOWithLinks(user, pageable, resourcesOrderLazyDTO, resourcesOrderDTO);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }
    }

    /**
     * Create response entity.
     *
     * @param userDTO               the user dto
     * @param pageable              the pageable
     * @param resourcesOrderDTO     the resources order dto
     * @param resourcesOrderLazyDTO the resources order lazy dto
     * @return the response entity
     * @throws ControllerException the controller exception
     */
    @PostMapping()
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<UserDTO> create(@RequestBody @Valid UserDTO userDTO,
                                          Pageable pageable, PagedResourcesAssembler<OrderDTO> resourcesOrderDTO,
                                          PagedResourcesAssembler<OrderLazyDTO> resourcesOrderLazyDTO) throws ControllerException {
        User user = userConverter.convertToUser(userDTO);
        try {
            userDTO = userConverter.convertToUserDTO(userService.create(user));
            initializeUserDTOWithLinks(userDTO, pageable, resourcesOrderLazyDTO, resourcesOrderDTO);
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }
        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }

    /**
     * Find all order by user id response entity.
     *
     * @param userId                the user id
     * @param pageable              the pageable
     * @param resourcesOrderLazyDTO the resources order lazy dto
     * @param resourcesOrderDTO     the resources order dto
     * @return the response entity
     * @throws ControllerException the controller exception
     */
    @GetMapping(value = "/{user_id}/orders")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<PagedModel<EntityModel<OrderLazyDTO>>> findAllOrderByUserId(@PathVariable(name = "user_id") long userId,
                                    Pageable pageable, PagedResourcesAssembler<OrderLazyDTO> resourcesOrderLazyDTO,
                                    PagedResourcesAssembler<OrderDTO> resourcesOrderDTO) throws ControllerException {
        String order = "order";
        Page<OrderLazyDTO> orderDTOS;
        PagedModel<EntityModel<OrderLazyDTO>> pagedModel;
        try {
            orderDTOS = MapperUtil
                    .convertList(orderService.findAllByUserId(userId, pageable),
                            orderLazyConverter::convertToOrderDTO);
            for (var i: orderDTOS) {
                i.add(linkTo(methodOn(OrderController.class).findById(i.getId())).withRel(order).withType(HttpMethod.GET.name()));
            }
        } catch (ServiceException e) {
           throw new ControllerException(e);
        }
        pagedModel = resourcesOrderLazyDTO.toModel(orderDTOS);
        pagedModel.add(linkTo(methodOn(OrderController.class).findAll(pageable, resourcesOrderDTO)).withRel(order).withType(HttpMethod.GET.name()));
        return new ResponseEntity<>(pagedModel, HttpStatus.OK);
    }

    /**
     * Search mostly used tag by order price response entity.
     *
     * @param userId the user id
     * @return the response entity
     * @throws ControllerException the controller exception
     */
    @GetMapping(value = "/{user_id}/tags/search")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<TagDTO> searchMostlyUsedTagByOrderPrice(@PathVariable("user_id") long userId)
            throws ControllerException {
        TagDTO tagDTO;
        try {
            tagDTO = tagConverter.convertToTagDTO(tagService.findAllMostlyUsedTagByOrderPrice(userId));
            initializeTagDTOWithLink(tagDTO);
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }
        return new ResponseEntity<>(tagDTO, HttpStatus.OK);
    }

    /**
     * Create order response entity.
     *
     * @param orderDTO the order dto
     * @param userId   the user id
     * @return the response entity
     * @throws ControllerException the controller exception
     */
    @PostMapping(value = "/{user_id}/orders")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO,
                                             @PathVariable(name = "user_id") long userId) throws ControllerException {
        orderDTO.getUserDTO().setId(userId);
        Order order = orderConverter.convertToOrder(orderDTO);
        try {
            orderDTO = orderConverter.convertToOrderDTO(orderService.create(order));
            orderDTO.add(linkTo(methodOn(OrderController.class).findById(orderDTO.getId())).withSelfRel().withType(HttpMethod.GET.name()));
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }
        return new ResponseEntity<>(orderDTO, HttpStatus.CREATED);
    }

    /**
     * Delete by id response entity.
     *
     * @param userId the user id
     * @return the response entity
     * @throws ControllerException the controller exception
     */
    @DeleteMapping(value = "/{user_id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Long> deleteById(@PathVariable("user_id") long userId) throws ControllerException {
        try {
            return new ResponseEntity<>(userService.deleteById(userId), HttpStatus.OK);
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }
    }

    private void initializeUserDTOListWithLinks(Page<UserDTO> userDTOS, Pageable pageable,
                                                PagedResourcesAssembler<OrderDTO> resourcesOrderDTO,
                                                PagedResourcesAssembler<OrderLazyDTO> resourcesOrderLazyDTO) throws ControllerException {
        String order = "order";
        for (var i: userDTOS) {
            linksForUserDTO(pageable, resourcesOrderDTO, resourcesOrderLazyDTO, order, i);
        }
    }

    private void initializeOrderDTOListWithLinks(List<OrderLazyDTO> orderLazyDTOS) throws ControllerException {
        String order = "order";
        for (var i: orderLazyDTOS) {
            i.add(linkTo(methodOn(OrderController.class).findById(i.getId())).withRel(order).withType(HttpMethod.GET.name()));
        }
    }

    private void initializeUserDTOWithLinks(UserDTO userDTO, Pageable pageable, PagedResourcesAssembler<OrderLazyDTO> resourcesOrderLazyDTO,
                                            PagedResourcesAssembler<OrderDTO> resourcesOrderDTO) throws ControllerException {
        String order = "order";
        linksForUserDTO(pageable, resourcesOrderDTO, resourcesOrderLazyDTO, order, userDTO);
    }

    private void linksForUserDTO(Pageable pageable, PagedResourcesAssembler<OrderDTO> resourcesOrderDTO, PagedResourcesAssembler<OrderLazyDTO> resourcesOrderLazyDTO, String order, UserDTO i) throws ControllerException {
        i.add(linkTo(methodOn(UserController.class).findById(i.getId(), pageable, resourcesOrderDTO, resourcesOrderLazyDTO)).withSelfRel().withType(HttpMethod.GET.name()))
                .add(linkTo(methodOn(UserController.class).deleteById(i.getId())).withSelfRel().withType(HttpMethod.DELETE.name()))
                //.add(linkTo(methodOn(UserController.class).findAllOrderByUserId(i.getId(), pageable, resourcesOrderLazyDTO, resourcesOrderDTO)).withRel(order).withType(HttpMethod.GET.name()))
                .add(linkTo(methodOn(UserController.class).createOrder(new OrderDTO(), i.getId())).withSelfRel().withType(HttpMethod.POST.name()));
        initializeOrderDTOListWithLinks(i.getOrderLazyDTOList());
    }

    private void initializePageModel(PagedModel<EntityModel<UserDTO>> pagedModel) {
        pagedModel.add(linkTo(UserController.class).withSelfRel().withType(HttpMethod.GET.name()))
                .add(linkTo(UserController.class).withSelfRel().withType(HttpMethod.POST.name()))
                .add(linkTo(UserController.class).slash("/search/name?name=name").withSelfRel().withType(HttpMethod.GET.name()));

    }

    private void initializeTagDTOWithLink(TagDTO tagDTO) throws ControllerException {
        tagDTO.add(linkTo(methodOn(TagController.class).findById(tagDTO.getId())).withSelfRel().withType(HttpMethod.GET.name()))
                .add(linkTo(methodOn(TagController.class).remove(tagDTO.getId())).withSelfRel().withType(HttpMethod.DELETE.name()));
    }

}
