package com.itravel.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.itravel.app.domain.CompanyAgency;

import com.itravel.app.repository.CompanyAgencyRepository;
import com.itravel.app.repository.search.CompanyAgencySearchRepository;
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
 * REST controller for managing CompanyAgency.
 */
@RestController
@RequestMapping("/api")
public class CompanyAgencyResource {

    private final Logger log = LoggerFactory.getLogger(CompanyAgencyResource.class);

    private static final String ENTITY_NAME = "companyAgency";

    private final CompanyAgencyRepository companyAgencyRepository;

    private final CompanyAgencySearchRepository companyAgencySearchRepository;

    public CompanyAgencyResource(CompanyAgencyRepository companyAgencyRepository, CompanyAgencySearchRepository companyAgencySearchRepository) {
        this.companyAgencyRepository = companyAgencyRepository;
        this.companyAgencySearchRepository = companyAgencySearchRepository;
    }

    /**
     * POST  /company-agencies : Create a new companyAgency.
     *
     * @param companyAgency the companyAgency to create
     * @return the ResponseEntity with status 201 (Created) and with body the new companyAgency, or with status 400 (Bad Request) if the companyAgency has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/company-agencies")
    @Timed
    public ResponseEntity<CompanyAgency> createCompanyAgency(@Valid @RequestBody CompanyAgency companyAgency) throws URISyntaxException {
        log.debug("REST request to save CompanyAgency : {}", companyAgency);
        if (companyAgency.getId() != null) {
            throw new BadRequestAlertException("A new companyAgency cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompanyAgency result = companyAgencyRepository.save(companyAgency);
        companyAgencySearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/company-agencies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /company-agencies : Updates an existing companyAgency.
     *
     * @param companyAgency the companyAgency to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated companyAgency,
     * or with status 400 (Bad Request) if the companyAgency is not valid,
     * or with status 500 (Internal Server Error) if the companyAgency couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/company-agencies")
    @Timed
    public ResponseEntity<CompanyAgency> updateCompanyAgency(@Valid @RequestBody CompanyAgency companyAgency) throws URISyntaxException {
        log.debug("REST request to update CompanyAgency : {}", companyAgency);
        if (companyAgency.getId() == null) {
            return createCompanyAgency(companyAgency);
        }
        CompanyAgency result = companyAgencyRepository.save(companyAgency);
        companyAgencySearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, companyAgency.getId().toString()))
            .body(result);
    }

    /**
     * GET  /company-agencies : get all the companyAgencies.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of companyAgencies in body
     */
    @GetMapping("/company-agencies")
    @Timed
    public List<CompanyAgency> getAllCompanyAgencies() {
        log.debug("REST request to get all CompanyAgencies");
        return companyAgencyRepository.findAll();
        }

    /**
     * GET  /company-agencies/:id : get the "id" companyAgency.
     *
     * @param id the id of the companyAgency to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the companyAgency, or with status 404 (Not Found)
     */
    @GetMapping("/company-agencies/{id}")
    @Timed
    public ResponseEntity<CompanyAgency> getCompanyAgency(@PathVariable Long id) {
        log.debug("REST request to get CompanyAgency : {}", id);
        CompanyAgency companyAgency = companyAgencyRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(companyAgency));
    }

    /**
     * DELETE  /company-agencies/:id : delete the "id" companyAgency.
     *
     * @param id the id of the companyAgency to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/company-agencies/{id}")
    @Timed
    public ResponseEntity<Void> deleteCompanyAgency(@PathVariable Long id) {
        log.debug("REST request to delete CompanyAgency : {}", id);
        companyAgencyRepository.delete(id);
        companyAgencySearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/company-agencies?query=:query : search for the companyAgency corresponding
     * to the query.
     *
     * @param query the query of the companyAgency search
     * @return the result of the search
     */
    @GetMapping("/_search/company-agencies")
    @Timed
    public List<CompanyAgency> searchCompanyAgencies(@RequestParam String query) {
        log.debug("REST request to search CompanyAgencies for query {}", query);
        return StreamSupport
            .stream(companyAgencySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
