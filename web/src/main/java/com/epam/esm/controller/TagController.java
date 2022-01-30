package com.epam.esm.controller;

import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ControllerException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.TagService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * The type Tag controller.
 */
@RestController
@RequestMapping(value = "/tags", consumes = MediaType.APPLICATION_JSON_VALUE,
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
        List<Tag> tagList;
        try {
            tagList = tagService.findAll();
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }
        return tagList.isEmpty() ? ResponseEntity.notFound().build() :
                ResponseEntity.status(HttpStatus.OK).body(tagList);
    }

    @GetMapping(value = "/search")
    public ResponseEntity<Tag> findAllMostlyUsedTagByOrderPrice() throws ControllerException {
        try {
            Tag tag = tagService.findAllMostlyUsedTagByOrderPrice().get();
            return new ResponseEntity<>(tag, HttpStatus.OK);
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }
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
        Optional<Tag> tag;
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
    public ResponseEntity<Tag> create(@RequestBody Tag tag) throws ControllerException {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(tagService.create(tag));
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
    public ResponseEntity<Long> remove(@PathVariable long id) throws ControllerException {
        try {
            return tagService.removeById(id) ? ResponseEntity.status(HttpStatus.OK).body(id)
                    : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }
    }
}
