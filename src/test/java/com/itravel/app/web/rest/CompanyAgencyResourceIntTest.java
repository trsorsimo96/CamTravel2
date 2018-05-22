package com.itravel.app.web.rest;

import com.itravel.app.CamTravel2App;

import com.itravel.app.domain.CompanyAgency;
import com.itravel.app.repository.CompanyAgencyRepository;
import com.itravel.app.repository.search.CompanyAgencySearchRepository;
import com.itravel.app.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.itravel.app.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CompanyAgencyResource REST controller.
 *
 * @see CompanyAgencyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CamTravel2App.class)
public class CompanyAgencyResourceIntTest {

    private static final String DEFAULT_COMPANY = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY = "BBBBBBBBBB";

    private static final String DEFAULT_AGENCY = "AAAAAAAAAA";
    private static final String UPDATED_AGENCY = "BBBBBBBBBB";

    private static final Integer DEFAULT_COMMISION = 1;
    private static final Integer UPDATED_COMMISION = 2;

    @Autowired
    private CompanyAgencyRepository companyAgencyRepository;

    @Autowired
    private CompanyAgencySearchRepository companyAgencySearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCompanyAgencyMockMvc;

    private CompanyAgency companyAgency;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CompanyAgencyResource companyAgencyResource = new CompanyAgencyResource(companyAgencyRepository, companyAgencySearchRepository);
        this.restCompanyAgencyMockMvc = MockMvcBuilders.standaloneSetup(companyAgencyResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompanyAgency createEntity(EntityManager em) {
        CompanyAgency companyAgency = new CompanyAgency()
            .company(DEFAULT_COMPANY)
            .agency(DEFAULT_AGENCY)
            .commision(DEFAULT_COMMISION);
        return companyAgency;
    }

    @Before
    public void initTest() {
        companyAgencySearchRepository.deleteAll();
        companyAgency = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompanyAgency() throws Exception {
        int databaseSizeBeforeCreate = companyAgencyRepository.findAll().size();

        // Create the CompanyAgency
        restCompanyAgencyMockMvc.perform(post("/api/company-agencies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyAgency)))
            .andExpect(status().isCreated());

        // Validate the CompanyAgency in the database
        List<CompanyAgency> companyAgencyList = companyAgencyRepository.findAll();
        assertThat(companyAgencyList).hasSize(databaseSizeBeforeCreate + 1);
        CompanyAgency testCompanyAgency = companyAgencyList.get(companyAgencyList.size() - 1);
        assertThat(testCompanyAgency.getCompany()).isEqualTo(DEFAULT_COMPANY);
        assertThat(testCompanyAgency.getAgency()).isEqualTo(DEFAULT_AGENCY);
        assertThat(testCompanyAgency.getCommision()).isEqualTo(DEFAULT_COMMISION);

        // Validate the CompanyAgency in Elasticsearch
        CompanyAgency companyAgencyEs = companyAgencySearchRepository.findOne(testCompanyAgency.getId());
        assertThat(companyAgencyEs).isEqualToIgnoringGivenFields(testCompanyAgency);
    }

    @Test
    @Transactional
    public void createCompanyAgencyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = companyAgencyRepository.findAll().size();

        // Create the CompanyAgency with an existing ID
        companyAgency.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompanyAgencyMockMvc.perform(post("/api/company-agencies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyAgency)))
            .andExpect(status().isBadRequest());

        // Validate the CompanyAgency in the database
        List<CompanyAgency> companyAgencyList = companyAgencyRepository.findAll();
        assertThat(companyAgencyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCompanyIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyAgencyRepository.findAll().size();
        // set the field null
        companyAgency.setCompany(null);

        // Create the CompanyAgency, which fails.

        restCompanyAgencyMockMvc.perform(post("/api/company-agencies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyAgency)))
            .andExpect(status().isBadRequest());

        List<CompanyAgency> companyAgencyList = companyAgencyRepository.findAll();
        assertThat(companyAgencyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAgencyIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyAgencyRepository.findAll().size();
        // set the field null
        companyAgency.setAgency(null);

        // Create the CompanyAgency, which fails.

        restCompanyAgencyMockMvc.perform(post("/api/company-agencies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyAgency)))
            .andExpect(status().isBadRequest());

        List<CompanyAgency> companyAgencyList = companyAgencyRepository.findAll();
        assertThat(companyAgencyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCompanyAgencies() throws Exception {
        // Initialize the database
        companyAgencyRepository.saveAndFlush(companyAgency);

        // Get all the companyAgencyList
        restCompanyAgencyMockMvc.perform(get("/api/company-agencies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companyAgency.getId().intValue())))
            .andExpect(jsonPath("$.[*].company").value(hasItem(DEFAULT_COMPANY.toString())))
            .andExpect(jsonPath("$.[*].agency").value(hasItem(DEFAULT_AGENCY.toString())))
            .andExpect(jsonPath("$.[*].commision").value(hasItem(DEFAULT_COMMISION)));
    }

    @Test
    @Transactional
    public void getCompanyAgency() throws Exception {
        // Initialize the database
        companyAgencyRepository.saveAndFlush(companyAgency);

        // Get the companyAgency
        restCompanyAgencyMockMvc.perform(get("/api/company-agencies/{id}", companyAgency.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(companyAgency.getId().intValue()))
            .andExpect(jsonPath("$.company").value(DEFAULT_COMPANY.toString()))
            .andExpect(jsonPath("$.agency").value(DEFAULT_AGENCY.toString()))
            .andExpect(jsonPath("$.commision").value(DEFAULT_COMMISION));
    }

    @Test
    @Transactional
    public void getNonExistingCompanyAgency() throws Exception {
        // Get the companyAgency
        restCompanyAgencyMockMvc.perform(get("/api/company-agencies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompanyAgency() throws Exception {
        // Initialize the database
        companyAgencyRepository.saveAndFlush(companyAgency);
        companyAgencySearchRepository.save(companyAgency);
        int databaseSizeBeforeUpdate = companyAgencyRepository.findAll().size();

        // Update the companyAgency
        CompanyAgency updatedCompanyAgency = companyAgencyRepository.findOne(companyAgency.getId());
        // Disconnect from session so that the updates on updatedCompanyAgency are not directly saved in db
        em.detach(updatedCompanyAgency);
        updatedCompanyAgency
            .company(UPDATED_COMPANY)
            .agency(UPDATED_AGENCY)
            .commision(UPDATED_COMMISION);

        restCompanyAgencyMockMvc.perform(put("/api/company-agencies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCompanyAgency)))
            .andExpect(status().isOk());

        // Validate the CompanyAgency in the database
        List<CompanyAgency> companyAgencyList = companyAgencyRepository.findAll();
        assertThat(companyAgencyList).hasSize(databaseSizeBeforeUpdate);
        CompanyAgency testCompanyAgency = companyAgencyList.get(companyAgencyList.size() - 1);
        assertThat(testCompanyAgency.getCompany()).isEqualTo(UPDATED_COMPANY);
        assertThat(testCompanyAgency.getAgency()).isEqualTo(UPDATED_AGENCY);
        assertThat(testCompanyAgency.getCommision()).isEqualTo(UPDATED_COMMISION);

        // Validate the CompanyAgency in Elasticsearch
        CompanyAgency companyAgencyEs = companyAgencySearchRepository.findOne(testCompanyAgency.getId());
        assertThat(companyAgencyEs).isEqualToIgnoringGivenFields(testCompanyAgency);
    }

    @Test
    @Transactional
    public void updateNonExistingCompanyAgency() throws Exception {
        int databaseSizeBeforeUpdate = companyAgencyRepository.findAll().size();

        // Create the CompanyAgency

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCompanyAgencyMockMvc.perform(put("/api/company-agencies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyAgency)))
            .andExpect(status().isCreated());

        // Validate the CompanyAgency in the database
        List<CompanyAgency> companyAgencyList = companyAgencyRepository.findAll();
        assertThat(companyAgencyList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCompanyAgency() throws Exception {
        // Initialize the database
        companyAgencyRepository.saveAndFlush(companyAgency);
        companyAgencySearchRepository.save(companyAgency);
        int databaseSizeBeforeDelete = companyAgencyRepository.findAll().size();

        // Get the companyAgency
        restCompanyAgencyMockMvc.perform(delete("/api/company-agencies/{id}", companyAgency.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean companyAgencyExistsInEs = companyAgencySearchRepository.exists(companyAgency.getId());
        assertThat(companyAgencyExistsInEs).isFalse();

        // Validate the database is empty
        List<CompanyAgency> companyAgencyList = companyAgencyRepository.findAll();
        assertThat(companyAgencyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchCompanyAgency() throws Exception {
        // Initialize the database
        companyAgencyRepository.saveAndFlush(companyAgency);
        companyAgencySearchRepository.save(companyAgency);

        // Search the companyAgency
        restCompanyAgencyMockMvc.perform(get("/api/_search/company-agencies?query=id:" + companyAgency.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companyAgency.getId().intValue())))
            .andExpect(jsonPath("$.[*].company").value(hasItem(DEFAULT_COMPANY.toString())))
            .andExpect(jsonPath("$.[*].agency").value(hasItem(DEFAULT_AGENCY.toString())))
            .andExpect(jsonPath("$.[*].commision").value(hasItem(DEFAULT_COMMISION)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanyAgency.class);
        CompanyAgency companyAgency1 = new CompanyAgency();
        companyAgency1.setId(1L);
        CompanyAgency companyAgency2 = new CompanyAgency();
        companyAgency2.setId(companyAgency1.getId());
        assertThat(companyAgency1).isEqualTo(companyAgency2);
        companyAgency2.setId(2L);
        assertThat(companyAgency1).isNotEqualTo(companyAgency2);
        companyAgency1.setId(null);
        assertThat(companyAgency1).isNotEqualTo(companyAgency2);
    }
}
