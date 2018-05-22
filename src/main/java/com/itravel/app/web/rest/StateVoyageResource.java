package com.itravel.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.itravel.app.domain.StateVoyage;

import com.itravel.app.repository.StateVoyageRepository;
import com.itravel.app.repository.search.StateVoyageSearchRepository;
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
 * REST controller for managing StateVoyage.
 */
@RestController
@RequestMapping("/api")
public class StateVoyageResource {

    private final Logger log = LoggerFactory.getLogger(StateVoyageResource.class);

    private static final String ENTITY_NAME = "stateVoyage";

    private final StateVoyageRepository stateVoyageRepository;

    private final StateVoyageSearchRepository stateVoyageSearchRepository;

    public StateVoyageResource(StateVoyageRepository stateVoyageRepository, StateVoyageSearchRepository stateVoyageSearchRepository) {
        this.stateVoyageRepository = stateVoyageRepository;
        this.stateVoyageSearchRepository = stateVoyageSearchRepository;
    }

    /**
     * POST  /state-voyages : Create a new stateVoyage.
     *
     * @param stateVoyage the stateVoyage to create
     * @return the ResponseEntity with status 201 (Created) and with body the new stateVoyage, or with status 400 (Bad Request) if the stateVoyage has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/state-voyages")
    @Timed
    public ResponseEntity<StateVoyage> createStateVoyage(@Valid @RequestBody StateVoyage stateVoyage) throws URISyntaxException {
        log.debug("REST request to save StateVoyage : {}", stateVoyage);
        if (stateVoyage.getId() != null) {
            throw new BadRequestAlertException("A new stateVoyage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StateVoyage result = stateVoyageRepository.save(stateVoyage);
        stateVoyageSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/state-voyages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /state-voyages : Updates an existing stateVoyage.
     *
     * @param stateVoyage the stateVoyage to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated stateVoyage,
     * or with status 400 (Bad Request) if the stateVoyage is not valid,
     * or with status 500 (Internal Server Error) if the stateVoyage couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/state-voyages")
    @Timed
    public ResponseEntity<StateVoyage> updateStateVoyage(@Valid @RequestBody StateVoyage stateVoyage) throws URISyntaxException {
        log.debug("REST request to update StateVoyage : {}", stateVoyage);
        if (stateVoyage.getId() == null) {
            return createStateVoyage(stateVoyage);
        }
        StateVoyage result = stateVoyageRepository.save(stateVoyage);
        stateVoyageSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, stateVoyage.getId().toString()))
            .body(result);
    }

    /**
     * GET  /state-voyages : get all the stateVoyages.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of stateVoyages in body
     */
    @GetMapping("/state-voyages")
    @Timed
    public List<StateVoyage> getAllStateVoyages() {
        log.debug("REST request to get all StateVoyages");
        return stateVoyageRepository.findAll();
        }

    /**
     * GET  /state-voyages/:id : get the "id" stateVoyage.
     *
     * @param id the id of the stateVoyage to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the stateVoyage, or with status 404 (Not Found)
     */
    @GetMapping("/state-voyages/{id}")
    @Timed
    public ResponseEntity<StateVoyage> getStateVoyage(@PathVariable Long id) {
        log.debug("REST request to get StateVoyage : {}", id);
        StateVoyage stateVoyage = stateVoyageRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(stateVoyage));
    }

    /**
     * DELETE  /state-voyages/:id : delete the "id" stateVoyage.
     *
     * @param id the id of the stateVoyage to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/state-voyages/{id}")
    @Timed
    public ResponseEntity<Void> deleteStateVoyage(@PathVariable Long id) {
        log.debug("REST request to delete StateVoyage : {}", id);
        stateVoyageRepository.delete(id);
        stateVoyageSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/state-voyages?query=:query : search for the stateVoyage corresponding
     * to the query.
     *
     * @param query the query of the stateVoyage search
     * @return the result of the search
     */
    @GetMapping("/_search/state-voyages")
    @Timed
    public List<StateVoyage> searchStateVoyages(@RequestParam String query) {
        log.debug("REST request to search StateVoyages for query {}", query);
        return StreamSupport
            .stream(stateVoyageSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
