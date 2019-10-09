package org.bookshare.web.rest;

import org.bookshare.service.GoodreadsService;
import org.bookshare.web.rest.errors.BadRequestAlertException;
import org.bookshare.service.dto.GoodreadsDTO;
import org.bookshare.service.dto.GoodreadsCriteria;
import org.bookshare.service.GoodreadsQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link org.bookshare.domain.Goodreads}.
 */
@RestController
@RequestMapping("/api")
public class GoodreadsResource {

    private final Logger log = LoggerFactory.getLogger(GoodreadsResource.class);

    private static final String ENTITY_NAME = "goodreads";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GoodreadsService goodreadsService;

    private final GoodreadsQueryService goodreadsQueryService;

    public GoodreadsResource(GoodreadsService goodreadsService, GoodreadsQueryService goodreadsQueryService) {
        this.goodreadsService = goodreadsService;
        this.goodreadsQueryService = goodreadsQueryService;
    }

    /**
     * {@code POST  /goodreads} : Create a new goodreads.
     *
     * @param goodreadsDTO the goodreadsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new goodreadsDTO, or with status {@code 400 (Bad Request)} if the goodreads has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/goodreads")
    public ResponseEntity<GoodreadsDTO> createGoodreads(@RequestBody GoodreadsDTO goodreadsDTO) throws URISyntaxException {
        log.debug("REST request to save Goodreads : {}", goodreadsDTO);
        if (goodreadsDTO.getId() != null) {
            throw new BadRequestAlertException("A new goodreads cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GoodreadsDTO result = goodreadsService.save(goodreadsDTO);
        return ResponseEntity.created(new URI("/api/goodreads/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /goodreads} : Updates an existing goodreads.
     *
     * @param goodreadsDTO the goodreadsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated goodreadsDTO,
     * or with status {@code 400 (Bad Request)} if the goodreadsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the goodreadsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/goodreads")
    public ResponseEntity<GoodreadsDTO> updateGoodreads(@RequestBody GoodreadsDTO goodreadsDTO) throws URISyntaxException {
        log.debug("REST request to update Goodreads : {}", goodreadsDTO);
        if (goodreadsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GoodreadsDTO result = goodreadsService.save(goodreadsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, goodreadsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /goodreads} : get all the goodreads.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of goodreads in body.
     */
    @GetMapping("/goodreads")
    public ResponseEntity<List<GoodreadsDTO>> getAllGoodreads(GoodreadsCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Goodreads by criteria: {}", criteria);
        Page<GoodreadsDTO> page = goodreadsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /goodreads/count} : count all the goodreads.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/goodreads/count")
    public ResponseEntity<Long> countGoodreads(GoodreadsCriteria criteria) {
        log.debug("REST request to count Goodreads by criteria: {}", criteria);
        return ResponseEntity.ok().body(goodreadsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /goodreads/:id} : get the "id" goodreads.
     *
     * @param id the id of the goodreadsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the goodreadsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/goodreads/{id}")
    public ResponseEntity<GoodreadsDTO> getGoodreads(@PathVariable Long id) {
        log.debug("REST request to get Goodreads : {}", id);
        Optional<GoodreadsDTO> goodreadsDTO = goodreadsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(goodreadsDTO);
    }

    /**
     * {@code DELETE  /goodreads/:id} : delete the "id" goodreads.
     *
     * @param id the id of the goodreadsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/goodreads/{id}")
    public ResponseEntity<Void> deleteGoodreads(@PathVariable Long id) {
        log.debug("REST request to delete Goodreads : {}", id);
        goodreadsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
