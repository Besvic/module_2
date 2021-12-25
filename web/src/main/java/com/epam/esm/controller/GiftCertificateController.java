package com.epam.esm.controller;

import com.epam.esm.database.Messages;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ControllerException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static com.epam.esm.database.Messages.getMessageForLocale;

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
    public ResponseEntity<List<GiftCertificate>> findAll() throws ControllerException {
        List<GiftCertificate> giftCertificateList;
        try {
            giftCertificateList = giftCertificateService.findAll();
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }
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
    public ResponseEntity<List<GiftCertificate>> findAllCertificateByTagName(@RequestBody @Valid Tag tag) throws ControllerException {
        List<GiftCertificate> giftCertificateList;
        try {
            giftCertificateList = giftCertificateService.findAllCertificateByTagName(tag.getName());
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }
        return giftCertificateList.isEmpty() ? ResponseEntity.status(HttpStatus.NO_CONTENT).build() :
                ResponseEntity.status(HttpStatus.OK).body(giftCertificateList);
    }

    @GetMapping(value = "/searchByNameOrDescription")
    public ResponseEntity<List<GiftCertificate>> findAllCertificateByNameOrDescription(
            @RequestParam(value = "name", defaultValue = "-") String name,
            @RequestParam(value = "description", defaultValue = "-") String description) throws ControllerException {
        List<GiftCertificate> giftCertificateList;
        try {
            giftCertificateList = giftCertificateService.findAllCertificateByNameOrDescription(name, description);
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }
        return giftCertificateList.isEmpty() ? ResponseEntity.status(HttpStatus.NO_CONTENT).build() :
                ResponseEntity.status(HttpStatus.OK).body(giftCertificateList);
    }

    @GetMapping("/locale")
    public ResponseEntity<String> changeLocale(@RequestParam("type") String locale){
        Messages.setLocale(locale.equalsIgnoreCase(Locale.US.toString()) ?
                Locale.US : new Locale("ru_RU"));
        return new ResponseEntity<>(getMessageForLocale("locale.change.successful"), HttpStatus.OK);
    }

    @GetMapping("/sort/date/{type}")
    public ResponseEntity<List<GiftCertificate>> findAllSortedCertificateByDate(@PathVariable("type") String type)
            throws ServiceException {
       List<GiftCertificate> certificateList = giftCertificateService.findAllCertificateByDate(type);
        return certificateList.isEmpty() ? ResponseEntity.status(HttpStatus.NO_CONTENT).build() :
                ResponseEntity.status(HttpStatus.OK).body(certificateList);
    }

    @GetMapping("/sort/name/{type}")
    public ResponseEntity<List<GiftCertificate>> findAllSortedCertificateByName(@PathVariable("type") String type)
            throws ServiceException {
        List<GiftCertificate> certificateList = giftCertificateService.findAllCertificateByName(type);
        return certificateList.isEmpty() ? ResponseEntity.status(HttpStatus.NO_CONTENT).build() :
                ResponseEntity.status(HttpStatus.OK).body(certificateList);
    }

    @PostMapping()
    public ResponseEntity<Object> createGiftCertificate(@Valid @RequestBody GiftCertificate giftCertificate)
            throws ControllerException {
        try {
            return giftCertificateService.create(giftCertificate) ? ResponseEntity.status(HttpStatus.OK).build()
                    : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> removeById(@PathVariable("id") long id) throws ControllerException {
        try {
            return giftCertificateService.removeById(id) ? ResponseEntity.status(HttpStatus.OK).build() :
                    ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateBiId(@PathVariable("id") long id,
                                             @RequestBody @Valid GiftCertificate giftCertificate) throws ControllerException {
        giftCertificate.setId(id);
        try {
            return giftCertificateService.updateById(giftCertificate) ? ResponseEntity.status(HttpStatus.OK).build() :
            ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }
    }


}
