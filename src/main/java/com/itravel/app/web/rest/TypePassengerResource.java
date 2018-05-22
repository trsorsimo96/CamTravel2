package com.itravel.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.itravel.app.domain.TypePassenger;

import com.itravel.app.repository.TypePassengerRepository;
import com.itravel.app.repository.search.TypePassengerSearchRepository;
import com.itravel.app.web.rest.errors.BadRequestAlertException;
import com.itravel.app.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing TypePassenger.
 */
@RestController
@RequestMapping("/api")
public class TypePassengerResource {

    private final Logger log = LoggerFactory.getLogger(TypePassengerResource.class);

    private static final String ENTITY_NAME = "typePassenger";

    private final TypePassengerRepository typePassengerRepository;

    private final TypePassengerSearchRepository typePassengerSearchRepository;

    public TypePassengerResource(TypePassengerRepository typePassengerRepository, TypePassengerSearchRepository typePassengerSearchRepository) {
        this.typePassengerRepository = typePassengerRepository;
        this.typePassengerSearchRepository = typePassengerSearchRepository;
    }

    /**
     * POST  /type-passengers : Create a new typePassenger.
     *
     * @param typePassenger the typePassenger to create
     * @return the ResponseEntity with status 201 (Created) and with body the new typePassenger, or with status 400 (Bad Request) if the typePassenger has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/type-passengers")
    @Timed
    public ResponseEntity<TypePassenger> createTypePassenger(@Valid @RequestBody TypePassenger typePassenger) throws URISyntaxException {
        log.debug("REST request to save TypePassenger : {}", typePassenger);
        if (typePassenger.getId() != null) {
            throw new BadRequestAlertException("A new typePassenger cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypePassenger result = typePassengerRepository.save(typePassenger);
        typePassengerSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/type-passengers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /type-passengers : Updates an existing typePassenger.
     *
     * @param typePassenger the typePassenger to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated typePassenger,
     * or with status 400 (Bad Request) if the typePassenger is not valid,
     * or with status 500 (Internal Server Error) if the typePassenger couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/type-passengers")
    @Timed
    public ResponseEntity<TypePassenger> updateTypePassenger(@Valid @RequestBody TypePassenger typePassenger) throws URISyntaxException {
        log.debug("REST request to update TypePassenger : {}", typePassenger);
        if (typePassenger.getId() == null) {
            return createTypePassenger(typePassenger);
        }
        TypePassenger result = typePassengerRepository.save(typePassenger);
        typePassengerSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, typePassenger.getId().toString()))
            .body(result);
    }

    /**
     * GET  /type-passengers : get all the typePassengers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of typePassengers in body
     */
    @GetMapping("/type-passengers")
    @Timed
    public List<TypePassenger> getAllTypePassengers() {
        log.debug("REST request to get all TypePassengers");
        return typePassengerRepository.findAll();
        }

    /**
     * GET  /type-passengers/:id : get the "id" typePassenger.
     *
     * @param id the id of the typePassenger to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the typePassenger, or with status 404 (Not Found)
     */
    @GetMapping("/type-passengers/{id}")
    @Timed
    public ResponseEntity<TypePassenger> getTypePassenger(@PathVariable Long id) {
        log.debug("REST request to get TypePassenger : {}", id);
        TypePassenger typePassenger = typePassengerRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(typePassenger));
    }

    /**
     * DELETE  /type-passengers/:id : delete the "id" typePassenger.
     *
     * @param id the id of the typePassenger to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/type-passengers/{id}")
    @Timed
    public ResponseEntity<Void> deleteTypePassenger(@PathVariable Long id) {
        log.debug("REST request to delete TypePassenger : {}", id);
        typePassengerRepository.delete(id);
        typePassengerSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/type-passengers?query=:query : search for the typePassenger corresponding
     * to the query.
     *
     * @param query the query of the typePassenger search
     * @return the result of the search
     */
    @GetMapping("/_search/type-passengers")
    @Timed
    public List<TypePassenger> searchTypePassengers(@RequestParam String query) {
        log.debug("REST request to search TypePassengers for query {}", query);
        return StreamSupport
            .stream(typePassengerSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
