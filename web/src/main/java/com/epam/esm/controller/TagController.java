package com.epam.esm.controller;

import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ControllerException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.TagService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * The type Tag controller.
 */
@RestController
@RequestMapping(value = "/tag", consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class TagController {

    private final TagService tagService;

    /**
     * Instantiates a new Tag controller.
     *
     * @param tagService the tag service
     */
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    /**
     * Find all response entity.
     *
     * @return the response entity
     * @throws ControllerException the controller exception
     */
    @GetMapping()
    public ResponseEntity<List<Tag>> findAll() throws ControllerException {
        List<Tag> tagList = null;
        try {
            tagList = tagService.findAll();
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }
        return tagList.isEmpty() ? ResponseEntity.notFound().build() :
                ResponseEntity.status(HttpStatus.FOUND).body(tagList);
    }

    /**
     * Find by id response entity.
     *
     * @param id the id
     * @return the response entity
     * @throws ControllerException the controller exception
     */
    @GetMapping("/{id}")
    public ResponseEntity<Tag> findById(@PathVariable long id) throws ControllerException {
        Optional<Tag> tag = null;
        try {
            tag = tagService.findById(id);
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }
        return tag.map(value -> ResponseEntity.status(HttpStatus.OK).body(value)).
                orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Create response entity . body builder.
     *
     * @param tag the tag
     * @return the response entity . body builder
     * @throws ControllerException the controller exception
     */
    @PostMapping()
    public ResponseEntity.BodyBuilder create(@RequestBody Tag tag) throws ControllerException {
        try {
            return tagService.create(tag) ? ResponseEntity.status(HttpStatus.OK) :
                    ResponseEntity.status(HttpStatus.NO_CONTENT);
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }
    }

    /**
     * Remove response entity . body builder.
     *
     * @param id the id
     * @return the response entity . body builder
     * @throws ControllerException the controller exception
     */
    @DeleteMapping("/{id}")
    public ResponseEntity.BodyBuilder remove(@PathVariable long id) throws ControllerException {
        try {
            return tagService.removeById(id) ? ResponseEntity.status(HttpStatus.OK) :
                    ResponseEntity.status(HttpStatus.NO_CONTENT);
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }
    }
}
