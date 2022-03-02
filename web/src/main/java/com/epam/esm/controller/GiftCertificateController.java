package com.epam.esm.controller;

import com.epam.esm.util.MapperUtil;
import com.epam.esm.dto.converter.GiftCertificateConverter;
import com.epam.esm.dto.entity.GiftCertificateDTO;
import com.epam.esm.dto.entity.TagDTO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.ControllerException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.GiftCertificateService;
import lombok.var;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import java.util.List;

import static com.epam.esm.util.LocalizedMessage.getMessageForLocale;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * The type Gift certificate controller.
 */
@Validated
@RestController
@RequestMapping(value = "/certificates",
produces = MediaType.APPLICATION_JSON_VALUE,
consumes = MediaType.APPLICATION_JSON_VALUE)
public class GiftCertificateController {

    private final GiftCertificateService giftCertificateService;
    private final GiftCertificateConverter certificateConverter;

    /**
     * Instantiates a new Gift certificate controller.
     *
     * @param giftCertificateService the gift certificate service
     * @param certificateConverter   the certificate converter
     */
    public GiftCertificateController(GiftCertificateService giftCertificateService,
                                     GiftCertificateConverter certificateConverter) {
        this.giftCertificateService = giftCertificateService;
        this.certificateConverter = certificateConverter;
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
    public ResponseEntity<PagedModel<EntityModel<GiftCertificateDTO>>> findAll(Pageable pageable, PagedResourcesAssembler<GiftCertificateDTO> resourcesAssembler)
            throws ControllerException {
        Page<GiftCertificateDTO> giftCertificateDTOS;
        PagedModel<EntityModel<GiftCertificateDTO>> pagedModel;
        try {
            giftCertificateDTOS = MapperUtil
                    .convertList(giftCertificateService.findAll(pageable),
                            certificateConverter::convertToGiftCertificateDTO);
            initializeGiftCertificateDTOListWithLinks(giftCertificateDTOS);
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }
        pagedModel = resourcesAssembler.toModel(giftCertificateDTOS);
        initializePageModel(pagedModel, pageable, resourcesAssembler);
        return giftCertificateDTOS.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(pagedModel, HttpStatus.OK);
    }


    /**
     * Find by id response entity.
     *
     * @param certificateId the certificate id
     * @return the response entity
     * @throws ControllerException the controller exception
     */
    @GetMapping(value = "/{certificate_id}")
    public ResponseEntity<GiftCertificateDTO> findById(@PathVariable("certificate_id") long certificateId) throws ControllerException {
        GiftCertificateDTO giftCertificateDTO;
        try {
            giftCertificateDTO = certificateConverter
                    .convertToGiftCertificateDTO(giftCertificateService.findById(certificateId));
            initializeGiftCertificateDTOWithLinks(giftCertificateDTO);
            initializeTagDTOListWithLink(giftCertificateDTO.getTagDTOList());
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }
        return new ResponseEntity<>(giftCertificateDTO, HttpStatus.OK);
    }

    /**
     * Search all certificate by tag name response entity.
     *
     * @param tagName            the tag name
     * @param pageable           the pageable
     * @param resourcesAssembler the resources assembler
     * @return the response entity
     * @throws ControllerException the controller exception
     */
    @GetMapping(value = "/search/by/tag/name")
    public ResponseEntity<PagedModel<EntityModel<GiftCertificateDTO>>> searchAllCertificateByTagName(@RequestParam("tag_name") String tagName, Pageable pageable,
                                                                                                     PagedResourcesAssembler<GiftCertificateDTO> resourcesAssembler) throws ControllerException {
        Page<GiftCertificateDTO> giftCertificateDTOS;
        PagedModel<EntityModel<GiftCertificateDTO>> pagedModel;
        try {
            giftCertificateDTOS = MapperUtil
                    .convertList(giftCertificateService.findAllCertificateByTagName(tagName, pageable),
                            certificateConverter::convertToGiftCertificateDTO);
            initializeGiftCertificateDTOListWithLinks(giftCertificateDTOS);
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }
        pagedModel = resourcesAssembler.toModel(giftCertificateDTOS);
        initializePageModel(pagedModel, pageable, resourcesAssembler);
        return giftCertificateDTOS.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(pagedModel, HttpStatus.OK);
    }

    /**
     * Search certificate by name or description response entity.
     *
     * @param name               the name
     * @param description        the description
     * @param pageable           the pageable
     * @param resourcesAssembler the resources assembler
     * @return the response entity
     * @throws ControllerException the controller exception
     */
    @GetMapping(value = "/search/by/name/description")
    public ResponseEntity<PagedModel<EntityModel<GiftCertificateDTO>>> searchCertificateByNameOrDescription(
                        @RequestParam(value = "name", defaultValue = "-") String name,
                        @RequestParam(value = "description", defaultValue = "-") String description,
                        Pageable pageable, PagedResourcesAssembler<GiftCertificateDTO> resourcesAssembler) throws ControllerException {
        Page<GiftCertificateDTO> giftCertificateDTOS;
        PagedModel<EntityModel<GiftCertificateDTO>> pagedModel;
        try {
            giftCertificateDTOS = MapperUtil
                    .convertList(giftCertificateService.findAllCertificateByNameOrDescription(name, description, pageable),
                    certificateConverter::convertToGiftCertificateDTO);
            initializeGiftCertificateDTOListWithLinks(giftCertificateDTOS);
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }
        pagedModel = resourcesAssembler.toModel(giftCertificateDTOS);
        initializePageModel(pagedModel, pageable, resourcesAssembler);
        return giftCertificateDTOS.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(pagedModel, HttpStatus.OK);
    }

    /**
     * Change locale response entity.
     *
     * @return the response entity
     */
    @GetMapping("/locale")
    public ResponseEntity<String> changeLocale(){
        return new ResponseEntity<>(getMessageForLocale("locale.change.successful"), HttpStatus.OK);
    }

    /**
     * Find all sorted certificate by date response entity.
     *
     * @param orderBy            the order by
     * @param pageable           the pageable
     * @param resourcesAssembler the resources assembler
     * @return the response entity
     * @throws ControllerException the controller exception
     */
    @GetMapping("/sort/date")
    public ResponseEntity<PagedModel<EntityModel<GiftCertificateDTO>>> findAllSortedCertificateByDate(@RequestParam("order_by") String orderBy,
                                    Pageable pageable, PagedResourcesAssembler<GiftCertificateDTO> resourcesAssembler) throws ControllerException {
        Page<GiftCertificateDTO> giftCertificateDTOS;
        PagedModel<EntityModel<GiftCertificateDTO>> pagedModel;
        try {
            giftCertificateDTOS = MapperUtil
                    .convertList(giftCertificateService.findAllCertificateByDate(orderBy, pageable),
                            certificateConverter::convertToGiftCertificateDTO);
            initializeGiftCertificateDTOListWithLinks(giftCertificateDTOS);
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }
        pagedModel = resourcesAssembler.toModel(giftCertificateDTOS);
        initializePageModel(pagedModel, pageable, resourcesAssembler);
        return giftCertificateDTOS.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(pagedModel, HttpStatus.OK);
    }

    /**
     * Find all sorted certificate by name response entity.
     *
     * @param orderBy            the order by
     * @param pageable           the pageable
     * @param resourcesAssembler the resources assembler
     * @return the response entity
     * @throws ControllerException the controller exception
     */
    @GetMapping("/sort/name")
    public ResponseEntity<PagedModel<EntityModel<GiftCertificateDTO>>> findAllSortedCertificateByName(@RequestParam("order_by") String orderBy,
                                                      Pageable pageable, PagedResourcesAssembler<GiftCertificateDTO> resourcesAssembler) throws ControllerException {
        Page<GiftCertificateDTO> giftCertificateDTOS;
        PagedModel<EntityModel<GiftCertificateDTO>> pagedModel;
        try {
            giftCertificateDTOS = MapperUtil
                    .convertList(giftCertificateService.findAllCertificateByName(orderBy, pageable),
                            certificateConverter::convertToGiftCertificateDTO);
            initializeGiftCertificateDTOListWithLinks(giftCertificateDTOS);
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }
        pagedModel = resourcesAssembler.toModel(giftCertificateDTOS);
        initializePageModel(pagedModel, pageable, resourcesAssembler);
        return giftCertificateDTOS.isEmpty() ? ResponseEntity.status(HttpStatus.NO_CONTENT).build() :
                new ResponseEntity<>(pagedModel, HttpStatus.OK);
    }

    /**
     * Search by tags response entity.
     *
     * @param strTagId           the str tag id
     * @param pageable           the pageable
     * @param resourcesAssembler the resources assembler
     * @return the response entity
     * @throws ControllerException the controller exception
     */
    @GetMapping("/search/by/tags")
    public ResponseEntity<PagedModel<EntityModel<GiftCertificateDTO>>> searchByTags(@RequestParam("tag_ids") String strTagId,
                                                                                    Pageable pageable,
                                                                                    PagedResourcesAssembler<GiftCertificateDTO> resourcesAssembler) throws ControllerException {
        Page<GiftCertificateDTO> giftCertificateDTOS;
        PagedModel<EntityModel<GiftCertificateDTO>> pagedModel;
        try {
            giftCertificateDTOS = MapperUtil
                    .convertList(giftCertificateService.findAllByTagIdList(strTagId, pageable),
                            certificateConverter::convertToGiftCertificateDTO);
            initializeGiftCertificateDTOListWithLinks(giftCertificateDTOS);
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }
        pagedModel = resourcesAssembler.toModel(giftCertificateDTOS);
        initializePageModel(pagedModel, pageable, resourcesAssembler);
        return giftCertificateDTOS.isEmpty() ? ResponseEntity.status(HttpStatus.NO_CONTENT).build() :
                new ResponseEntity<>(pagedModel, HttpStatus.OK);
    }

    /**
     * Create gift certificate response entity.
     *
     * @param giftCertificateDTO the gift certificate dto
     * @return the response entity
     * @throws ControllerException the controller exception
     */
    @PostMapping()
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<GiftCertificateDTO> createGiftCertificate(@Valid @RequestBody GiftCertificateDTO giftCertificateDTO)
            throws ControllerException {
        GiftCertificate certificate = certificateConverter.convertToGiftCertificate(giftCertificateDTO);
        try {
            certificate = giftCertificateService.create(certificate);
            giftCertificateDTO = certificateConverter.convertToGiftCertificateDTO(certificate);
            initializeTagDTOListWithLink(giftCertificateDTO.getTagDTOList());
            initializeGiftCertificateDTOWithLinks(giftCertificateDTO);
            return  new ResponseEntity<>(giftCertificateDTO, HttpStatus.CREATED);
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
    @DeleteMapping(value = "/{certificate_id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Long> removeById(@PathVariable("certificate_id") long id) throws ControllerException {
        try {
            return giftCertificateService.removeById(id) ? new ResponseEntity<>(id, HttpStatus.OK) :
                    new ResponseEntity<>(HttpStatus.OK);
        }catch (ServiceException e) {
            throw new ControllerException(e);
        }
    }

    /**
     * Update by id response entity.
     *
     * @param id                 the id
     * @param giftCertificateDTO the gift certificate dto
     * @return the response entity
     * @throws ControllerException the controller exception
     */
    @PatchMapping("/{certificate_id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<GiftCertificateDTO> updateById(@PathVariable("certificate_id") long id,
                                                      @RequestBody GiftCertificateDTO giftCertificateDTO) throws ControllerException {
        GiftCertificate giftCertificate = certificateConverter.convertToGiftCertificate(giftCertificateDTO);
        giftCertificate.setId(id);
        try {
            giftCertificate = giftCertificateService.updateById(giftCertificate);
            giftCertificateDTO = certificateConverter.convertToGiftCertificateDTO(giftCertificate);
            initializeTagDTOListWithLink(giftCertificateDTO.getTagDTOList());
            initializeGiftCertificateDTOWithLinks(giftCertificateDTO);
            return new ResponseEntity<>(giftCertificateDTO, HttpStatus.OK);
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }
    }

    private void initializeGiftCertificateDTOWithLinks(GiftCertificateDTO giftCertificateDTO) throws ControllerException {
        giftCertificateDTO.add(linkTo(methodOn(GiftCertificateController.class).removeById(giftCertificateDTO.getId())).withSelfRel().withType(HttpMethod.DELETE.name()))
                .add(linkTo(GiftCertificateController.class).slash(giftCertificateDTO.getId()).withSelfRel().withType(HttpMethod.PATCH.name()));
    }

    private void initializeGiftCertificateDTOListWithLinks(Page<GiftCertificateDTO> certificatePage) throws ControllerException {
        for (var i: certificatePage) {
            i.add(linkTo(methodOn(GiftCertificateController.class).findById(i.getId())).withSelfRel().withType(HttpMethod.GET.name()))
                    .add(linkTo(methodOn(GiftCertificateController.class).removeById(i.getId())).withSelfRel().withType(HttpMethod.DELETE.name()))
                    .add(linkTo(GiftCertificateController.class).slash(i.getId()).withSelfRel().withType(HttpMethod.PATCH.name()));
            initializeTagDTOListWithLink(i.getTagDTOList());
        }
    }

    private void initializePageModel(PagedModel<EntityModel<GiftCertificateDTO>> pagedModel, Pageable pageable, PagedResourcesAssembler<GiftCertificateDTO> resourcesAssembler) throws ControllerException {
        String name = "name";
        String description = "description";
        pagedModel.add(linkTo(methodOn(GiftCertificateController.class).findAllSortedCertificateByName(Sort.Direction.ASC.name(), pageable, resourcesAssembler)).withSelfRel().withType(HttpMethod.GET.name()))
                .add(linkTo(methodOn(GiftCertificateController.class).findAllSortedCertificateByDate(Sort.Direction.ASC.name(), pageable, resourcesAssembler)).withSelfRel().withType(HttpMethod.GET.name()))
                .add(linkTo(methodOn(GiftCertificateController.class).searchCertificateByNameOrDescription(name, description, pageable, resourcesAssembler)).withSelfRel().withType(HttpMethod.GET.name()))
                .add(linkTo(methodOn(GiftCertificateController.class).searchAllCertificateByTagName(name, pageable, resourcesAssembler)).withSelfRel().withType(HttpMethod.GET.name()))
                .add(linkTo(GiftCertificateController.class).withSelfRel().withType(HttpMethod.POST.name()))
                .add(linkTo(methodOn(GiftCertificateController.class).findAll(pageable, resourcesAssembler)).withSelfRel().withType(HttpMethod.GET.name()));
    }

    private void initializeTagDTOListWithLink(List<TagDTO> tagDTOPage) throws ControllerException {
        String tag = "tag";
        for (var i: tagDTOPage) {
            i.add(linkTo(methodOn(TagController.class).findById(i.getId())).withRel(tag).withType(HttpMethod.GET.name()))
                    .add(linkTo(methodOn(TagController.class).remove(i.getId())).withRel(tag).withType(HttpMethod.DELETE.name()));
        }
    }
}
