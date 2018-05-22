package com.itravel.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.itravel.app.domain.ModePayment;

import com.itravel.app.repository.ModePaymentRepository;
import com.itravel.app.repository.search.ModePaymentSearchRepository;
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
 * REST controller for managing ModePayment.
 */
@RestController
@RequestMapping("/api")
public class ModePaymentResource {

    private final Logger log = LoggerFactory.getLogger(ModePaymentResource.class);

    private static final String ENTITY_NAME = "modePayment";

    private final ModePaymentRepository modePaymentRepository;

    private final ModePaymentSearchRepository modePaymentSearchRepository;

    public ModePaymentResource(ModePaymentRepository modePaymentRepository, ModePaymentSearchRepository modePaymentSearchRepository) {
        this.modePaymentRepository = modePaymentRepository;
        this.modePaymentSearchRepository = modePaymentSearchRepository;
    }

    /**
     * POST  /mode-payments : Create a new modePayment.
     *
     * @param modePayment the modePayment to create
     * @return the ResponseEntity with status 201 (Created) and with body the new modePayment, or with status 400 (Bad Request) if the modePayment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mode-payments")
    @Timed
    public ResponseEntity<ModePayment> createModePayment(@Valid @RequestBody ModePayment modePayment) throws URISyntaxException {
        log.debug("REST request to save ModePayment : {}", modePayment);
        if (modePayment.getId() != null) {
            throw new BadRequestAlertException("A new modePayment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ModePayment result = modePaymentRepository.save(modePayment);
        modePaymentSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/mode-payments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /mode-payments : Updates an existing modePayment.
     *
     * @param modePayment the modePayment to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated modePayment,
     * or with status 400 (Bad Request) if the modePayment is not valid,
     * or with status 500 (Internal Server Error) if the modePayment couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mode-payments")
    @Timed
    public ResponseEntity<ModePayment> updateModePayment(@Valid @RequestBody ModePayment modePayment) throws URISyntaxException {
        log.debug("REST request to update ModePayment : {}", modePayment);
        if (modePayment.getId() == null) {
            return createModePayment(modePayment);
        }
        ModePayment result = modePaymentRepository.save(modePayment);
        modePaymentSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, modePayment.getId().toString()))
            .body(result);
    }

    /**
     * GET  /mode-payments : get all the modePayments.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of modePayments in body
     */
    @GetMapping("/mode-payments")
    @Timed
    public List<ModePayment> getAllModePayments() {
        log.debug("REST request to get all ModePayments");
        return modePaymentRepository.findAll();
        }

    /**
     * GET  /mode-payments/:id : get the "id" modePayment.
     *
     * @param id the id of the modePayment to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the modePayment, or with status 404 (Not Found)
     */
    @GetMapping("/mode-payments/{id}")
    @Timed
    public ResponseEntity<ModePayment> getModePayment(@PathVariable Long id) {
        log.debug("REST request to get ModePayment : {}", id);
        ModePayment modePayment = modePaymentRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(modePayment));
    }

    /**
     * DELETE  /mode-payments/:id : delete the "id" modePayment.
     *
     * @param id the id of the modePayment to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mode-payments/{id}")
    @Timed
    public ResponseEntity<Void> deleteModePayment(@PathVariable Long id) {
        log.debug("REST request to delete ModePayment : {}", id);
        modePaymentRepository.delete(id);
        modePaymentSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/mode-payments?query=:query : search for the modePayment corresponding
     * to the query.
     *
     * @param query the query of the modePayment search
     * @return the result of the search
     */
    @GetMapping("/_search/mode-payments")
    @Timed
    public List<ModePayment> searchModePayments(@RequestParam String query) {
        log.debug("REST request to search ModePayments for query {}", query);
        return StreamSupport
            .stream(modePaymentSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
