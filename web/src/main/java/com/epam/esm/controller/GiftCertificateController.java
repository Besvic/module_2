package com.epam.esm.controller;

import com.epam.esm.config.LocalizedMessage;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ControllerException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static com.epam.esm.config.LocalizedMessage.getMessageForLocale;

/**
 * The type Gift certificate controller.
 */
@Validated
@RestController
@RequestMapping(value = "/certificate",
produces = MediaType.APPLICATION_JSON_VALUE,
consumes = MediaType.APPLICATION_JSON_VALUE)
public class GiftCertificateController {

    private final GiftCertificateService giftCertificateService;

    /**
     * Instantiates a new Gift certificate controller.
     *
     * @param giftCertificateService the gift certificate service
     */
    public GiftCertificateController(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    /**
     * Find all response entity.
     *
     * @return the response entity
     * @throws ControllerException the controller exception
     */
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


    /**
     * Find by id response entity.
     *
     * @param id the id
     * @return the response entity
     * @throws ControllerException the controller exception
     */
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

    /**
     * Find all certificate by tag name response entity.
     *
     * @param tag the tag
     * @return the response entity
     * @throws ControllerException the controller exception
     */
    @GetMapping(value = "/search/by/tag/name")
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

    /**
     * Find all certificate by name or description response entity.
     *
     * @param name        the name
     * @param description the description
     * @return the response entity
     * @throws ControllerException the controller exception
     */
    @GetMapping(value = "/search/by/name/description")
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

    /**
     * Change locale response entity.
     *
     * @param locale the locale
     * @return the response entity
     */
    @GetMapping("/locale")
    public ResponseEntity<String> changeLocale(@RequestParam("type") String locale){
        LocalizedMessage.setLocale(locale.equalsIgnoreCase(Locale.US.toString()) ?
                Locale.US : new Locale("ru_RU"));
        return new ResponseEntity<>(getMessageForLocale("locale.change.successful"), HttpStatus.OK);
    }

    /**
     * Find all sorted certificate by date response entity.
     *
     * @param type the type
     * @return the response entity
     * @throws ServiceException the service exception
     */
    @GetMapping("/sort/date/{type}")
    public ResponseEntity<List<GiftCertificate>> findAllSortedCertificateByDate(@PathVariable("type") String type)
            throws ServiceException {
       List<GiftCertificate> certificateList = giftCertificateService.findAllCertificateByDate(type);
        return certificateList.isEmpty() ? ResponseEntity.status(HttpStatus.NO_CONTENT).build() :
                ResponseEntity.status(HttpStatus.OK).body(certificateList);
    }

    /**
     * Find all sorted certificate by name response entity.
     *
     * @param type the type
     * @return the response entity
     * @throws ServiceException the service exception
     */
    @GetMapping("/sort/name/{type}")
    public ResponseEntity<List<GiftCertificate>> findAllSortedCertificateByName(@PathVariable("type") String type)
            throws ServiceException {
        List<GiftCertificate> certificateList = giftCertificateService.findAllCertificateByName(type);
        return certificateList.isEmpty() ? ResponseEntity.status(HttpStatus.NO_CONTENT).build() :
                ResponseEntity.status(HttpStatus.OK).body(certificateList);
    }

    /**
     * Create gift certificate response entity.
     *
     * @param giftCertificate the gift certificate
     * @return the response entity
     * @throws ControllerException the controller exception
     */
    @PostMapping()
    public ResponseEntity<Object> createGiftCertificate(@Valid @RequestBody GiftCertificate giftCertificate)
            throws ControllerException {
        try {
            long certificateId = giftCertificateService.create(giftCertificate);
            return  ResponseEntity.status(HttpStatus.CREATED).body(certificateId);
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }
    }

    /**
     * Remove by id response entity.
     *
     * @param id the id
     * @return the response entity
     * @throws ControllerException the controller exception
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> removeById(@PathVariable("id") long id) throws ControllerException {
        try {
            return giftCertificateService.removeById(id) ? ResponseEntity.status(HttpStatus.OK).build() :
                    ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }
    }

    /**
     * Update bi id response entity.
     *
     * @param id              the id
     * @param giftCertificate the gift certificate
     * @return the response entity
     * @throws ControllerException the controller exception
     */
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
