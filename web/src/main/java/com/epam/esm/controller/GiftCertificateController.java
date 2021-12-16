package com.epam.esm.controller;

import com.epam.esm.entity.CustomResponse;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ControllerException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.GiftCertificateService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Validated
@RestController
@RequestMapping(value = "/certificate",
produces = MediaType.APPLICATION_JSON_VALUE,
consumes = MediaType.APPLICATION_JSON_VALUE)
public class GiftCertificateController {

    private final GiftCertificateService giftCertificateService;

    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    @GetMapping()
    public ResponseEntity<List<GiftCertificate>> findAll(){
        List<GiftCertificate> giftCertificateList = giftCertificateService.findAll();
        return giftCertificateList.isEmpty() ? ResponseEntity.status(HttpStatus.NO_CONTENT).build() :
                ResponseEntity.status(HttpStatus.OK).body(giftCertificateList);
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<GiftCertificate> findById(@PathVariable("id") long id) throws ControllerException {
        Optional<GiftCertificate> giftCertificate;
        try {
            giftCertificate = giftCertificateService.findById(id);
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }
        return giftCertificate.map(certificate -> ResponseEntity.status(HttpStatus.OK).body(certificate))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NO_CONTENT).build());
    }

    @GetMapping(value = "/searchByTagName")
    public ResponseEntity<List<GiftCertificate>> findAllCertificateByTagName(@RequestBody @Valid Tag tag){
        List<GiftCertificate> giftCertificateList = giftCertificateService.findAllCertificateByTagName(tag.getName());
        return giftCertificateList.isEmpty() ? ResponseEntity.status(HttpStatus.NO_CONTENT).build() :
                ResponseEntity.status(HttpStatus.OK).body(giftCertificateList);
    }

    @GetMapping(value = "/searchByNameOrDescription")
    public ResponseEntity<List<GiftCertificate>> findAllCertificateByNameOrDescription(
            @RequestParam(value = "name", defaultValue = "-") String name,
            @RequestParam(value = "description", defaultValue = "-") String description){
        List<GiftCertificate> giftCertificateList =
                giftCertificateService.findAllCertificateByNameOrDescription(name, description);
        return giftCertificateList.isEmpty() ? ResponseEntity.status(HttpStatus.NO_CONTENT).build() :
                ResponseEntity.status(HttpStatus.OK).body(giftCertificateList);
    }

    // TODO: 13.12.2021 add handling exception
    @GetMapping("/sort/date/{type}")
    public ResponseEntity<List<GiftCertificate>> findAllSortedCertificateByDate(@PathVariable("type") String type) throws ServiceException {
       List<GiftCertificate> certificateList = giftCertificateService.findAllCertificateByDate(type);
        return certificateList.isEmpty() ? ResponseEntity.status(HttpStatus.NO_CONTENT).build() :
                ResponseEntity.status(HttpStatus.OK).body(certificateList);
    }

    // TODO: 13.12.2021 add handling exception
    @GetMapping("/sort/name/{type}")
    public ResponseEntity<List<GiftCertificate>> findAllSortedCertificateByName(@PathVariable("type") String type) throws ServiceException {
        List<GiftCertificate> certificateList = giftCertificateService.findAllCertificateByName(type);
        return certificateList.isEmpty() ? ResponseEntity.status(HttpStatus.NO_CONTENT).build() :
                ResponseEntity.status(HttpStatus.OK).body(certificateList);
    }

    @PostMapping()
    public ResponseEntity<Object> createGiftCertificate(@Valid @RequestBody GiftCertificate giftCertificate){
        return giftCertificateService.create(giftCertificate) ? ResponseEntity.status(HttpStatus.OK).build()
                : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // TODO: 15.12.2021 del
  /*  @PostMapping("/test")
    public ResponseEntity test(@Valid @RequestBody GiftCertificate giftCertificate*//*,
                                                            @RequestBody ArrayList<Tag> tagList*//*){
        System.out.println(giftCertificate.toString());
        return ResponseEntity.status(HttpStatus.OK).build();*/


    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> removeById(@PathVariable("id") long id){
        return giftCertificateService.removeById(id) ? ResponseEntity.status(HttpStatus.OK).build() :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateBiId(@PathVariable("id") long id,
                                             @RequestBody @Valid GiftCertificate giftCertificate){
        giftCertificate.setId(id);
        return giftCertificateService.updateById(giftCertificate) ? ResponseEntity.status(HttpStatus.OK).build() :
        ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }


}
