package com.itravel.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.itravel.app.domain.TypeVoyage;

import com.itravel.app.repository.TypeVoyageRepository;
import com.itravel.app.repository.search.TypeVoyageSearchRepository;
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
 * REST controller for managing TypeVoyage.
 */
@RestController
@RequestMapping("/api")
public class TypeVoyageResource {

    private final Logger log = LoggerFactory.getLogger(TypeVoyageResource.class);

    private static final String ENTITY_NAME = "typeVoyage";

    private final TypeVoyageRepository typeVoyageRepository;

    private final TypeVoyageSearchRepository typeVoyageSearchRepository;

    public TypeVoyageResource(TypeVoyageRepository typeVoyageRepository, TypeVoyageSearchRepository typeVoyageSearchRepository) {
        this.typeVoyageRepository = typeVoyageRepository;
        this.typeVoyageSearchRepository = typeVoyageSearchRepository;
    }

    /**
     * POST  /type-voyages : Create a new typeVoyage.
     *
     * @param typeVoyage the typeVoyage to create
     * @return the ResponseEntity with status 201 (Created) and with body the new typeVoyage, or with status 400 (Bad Request) if the typeVoyage has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/type-voyages")
    @Timed
    public ResponseEntity<TypeVoyage> createTypeVoyage(@Valid @RequestBody TypeVoyage typeVoyage) throws URISyntaxException {
        log.debug("REST request to save TypeVoyage : {}", typeVoyage);
        if (typeVoyage.getId() != null) {
            throw new BadRequestAlertException("A new typeVoyage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeVoyage result = typeVoyageRepository.save(typeVoyage);
        typeVoyageSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/type-voyages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /type-voyages : Updates an existing typeVoyage.
     *
     * @param typeVoyage the typeVoyage to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated typeVoyage,
     * or with status 400 (Bad Request) if the typeVoyage is not valid,
     * or with status 500 (Internal Server Error) if the typeVoyage couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/type-voyages")
    @Timed
    public ResponseEntity<TypeVoyage> updateTypeVoyage(@Valid @RequestBody TypeVoyage typeVoyage) throws URISyntaxException {
        log.debug("REST request to update TypeVoyage : {}", typeVoyage);
        if (typeVoyage.getId() == null) {
            return createTypeVoyage(typeVoyage);
        }
        TypeVoyage result = typeVoyageRepository.save(typeVoyage);
        typeVoyageSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, typeVoyage.getId().toString()))
            .body(result);
    }

    /**
     * GET  /type-voyages : get all the typeVoyages.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of typeVoyages in body
     */
    @GetMapping("/type-voyages")
    @Timed
    public List<TypeVoyage> getAllTypeVoyages() {
        log.debug("REST request to get all TypeVoyages");
        return typeVoyageRepository.findAll();
        }

    /**
     * GET  /type-voyages/:id : get the "id" typeVoyage.
     *
     * @param id the id of the typeVoyage to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the typeVoyage, or with status 404 (Not Found)
     */
    @GetMapping("/type-voyages/{id}")
    @Timed
    public ResponseEntity<TypeVoyage> getTypeVoyage(@PathVariable Long id) {
        log.debug("REST request to get TypeVoyage : {}", id);
        TypeVoyage typeVoyage = typeVoyageRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(typeVoyage));
    }

    /**
     * DELETE  /type-voyages/:id : delete the "id" typeVoyage.
     *
     * @param id the id of the typeVoyage to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/type-voyages/{id}")
    @Timed
    public ResponseEntity<Void> deleteTypeVoyage(@PathVariable Long id) {
        log.debug("REST request to delete TypeVoyage : {}", id);
        typeVoyageRepository.delete(id);
        typeVoyageSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/type-voyages?query=:query : search for the typeVoyage corresponding
     * to the query.
     *
     * @param query the query of the typeVoyage search
     * @return the result of the search
     */
    @GetMapping("/_search/type-voyages")
    @Timed
    public List<TypeVoyage> searchTypeVoyages(@RequestParam String query) {
        log.debug("REST request to search TypeVoyages for query {}", query);
        return StreamSupport
            .stream(typeVoyageSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
