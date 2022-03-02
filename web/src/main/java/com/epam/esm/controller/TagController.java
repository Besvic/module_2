package com.epam.esm.controller;

import com.epam.esm.util.MapperUtil;
import com.epam.esm.dto.converter.TagConverter;
import com.epam.esm.dto.entity.TagDTO;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ControllerException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.TagService;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * The type Tag controller.
 */
@RestController
@RequestMapping(value = "/tags", consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class TagController {

    private final TagService tagService;
    private final TagConverter tagConverter;

    /**
     * Instantiates a new Tag controller.
     *
     * @param tagService   the tag service
     * @param tagConverter the tag converter
     */
    public TagController(TagService tagService,
                         TagConverter tagConverter) {
        this.tagService = tagService;
        this.tagConverter = tagConverter;
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
    @PreAuthorize("hasAnyRole('GUEST', 'USER', 'ADMIN')")
    public ResponseEntity<PagedModel<EntityModel<TagDTO>>> findAll(Pageable pageable,
                       PagedResourcesAssembler<TagDTO> resourcesAssembler) throws ControllerException {
        Page<TagDTO> tagDTOS;
        PagedModel<EntityModel<TagDTO>> pagedModel;
        try {
            tagDTOS = MapperUtil.convertList(tagService.findAll(pageable), tagConverter::convertToTagDTO);
            for (var i : tagDTOS) {
               initializeTagDTOWithLink(i);
            }
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }
        pagedModel = resourcesAssembler.toModel(tagDTOS);
        pagedModel
                .add(linkTo(methodOn(TagController.class).findAll(pageable, resourcesAssembler)).withSelfRel().withType(HttpMethod.GET.name()))
                .add(linkTo(methodOn(TagController.class)).withSelfRel().withType(HttpMethod.POST.name()));
        return tagDTOS.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(pagedModel, HttpStatus.OK);
    }


    /**
     * Find by id response entity.
     *
     * @param tagId the tag id
     * @return the response entity
     * @throws ControllerException the controller exception
     */
    @GetMapping("/{tag_id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<TagDTO> findById(@PathVariable("tag_id") long tagId) throws ControllerException {
        TagDTO tagDTO;
        try {
            tagDTO = tagConverter.convertToTagDTO(tagService.findById(tagId));
            initializeTagDTOWithLink(tagDTO);
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }
        return new ResponseEntity<>(tagDTO, HttpStatus.OK);
    }

    /**
     * Create response entity.
     *
     * @param tagDTO the tag dto
     * @return the response entity
     * @throws ControllerException the controller exception
     */
    @PostMapping()
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<TagDTO> create(@RequestBody TagDTO tagDTO) throws ControllerException {
        Tag tag = tagConverter.converterToTag(tagDTO);
        try {
            tagDTO = tagConverter.convertToTagDTO(tagService.create(tag));
            initializeTagDTOWithLink(tagDTO);
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }
        return new ResponseEntity<>(tagDTO, HttpStatus.CREATED);
    }

    /**
     * Remove response entity.
     *
     * @param tagId the tag id
     * @return the response entity
     * @throws ControllerException the controller exception
     */
    @DeleteMapping("/{tag_id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Long> remove(@PathVariable("tag_id") long tagId) throws ControllerException {
        try {
            return tagService.removeById(tagId) ? new ResponseEntity<>(tagId, HttpStatus.OK)
                    : new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }
    }

    private void initializeTagDTOWithLink(TagDTO tagDTO) throws ControllerException {
        tagDTO.add(linkTo(methodOn(TagController.class).findById(tagDTO.getId())).withSelfRel().withType(HttpMethod.GET.name()))
                .add(linkTo(methodOn(TagController.class).remove(tagDTO.getId())).withSelfRel().withType(HttpMethod.DELETE.name()));
    }
}
