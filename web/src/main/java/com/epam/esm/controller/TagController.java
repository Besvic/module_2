package com.epam.esm.controller;

import com.epam.esm.entity.Tag;
import com.epam.esm.service.TagService;
import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/tag", consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping()
    public ResponseEntity<List<Tag>> findAll(){
        List<Tag> tagList = tagService.findAll();
        return tagList.isEmpty() ? ResponseEntity.notFound().build() :
                ResponseEntity.status(HttpStatus.FOUND).body(tagList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tag> findById(@PathVariable long id){
        Optional<Tag> tag = tagService.findById(id);
        return tag.map(value -> ResponseEntity.status(HttpStatus.OK).body(value)).
                orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping()
    public ResponseEntity.BodyBuilder create(@RequestBody Tag tag){
        return tagService.create(tag) ? ResponseEntity.status(HttpStatus.OK) :
                ResponseEntity.status(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity.BodyBuilder remove(@PathVariable long id){
        return tagService.removeById(id) ? ResponseEntity.status(HttpStatus.OK) :
                ResponseEntity.status(HttpStatus.NO_CONTENT);
    }





}
