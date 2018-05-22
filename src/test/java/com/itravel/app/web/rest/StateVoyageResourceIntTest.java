package com.itravel.app.web.rest;

import com.itravel.app.CamTravel2App;

import com.itravel.app.domain.StateVoyage;
import com.itravel.app.repository.StateVoyageRepository;
import com.itravel.app.repository.search.StateVoyageSearchRepository;
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
 * Test class for the StateVoyageResource REST controller.
 *
 * @see StateVoyageResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CamTravel2App.class)
public class StateVoyageResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private StateVoyageRepository stateVoyageRepository;

    @Autowired
    private StateVoyageSearchRepository stateVoyageSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restStateVoyageMockMvc;

    private StateVoyage stateVoyage;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StateVoyageResource stateVoyageResource = new StateVoyageResource(stateVoyageRepository, stateVoyageSearchRepository);
        this.restStateVoyageMockMvc = MockMvcBuilders.standaloneSetup(stateVoyageResource)
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
    public static StateVoyage createEntity(EntityManager em) {
        StateVoyage stateVoyage = new StateVoyage()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return stateVoyage;
    }

    @Before
    public void initTest() {
        stateVoyageSearchRepository.deleteAll();
        stateVoyage = createEntity(em);
    }

    @Test
    @Transactional
    public void createStateVoyage() throws Exception {
        int databaseSizeBeforeCreate = stateVoyageRepository.findAll().size();

        // Create the StateVoyage
        restStateVoyageMockMvc.perform(post("/api/state-voyages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stateVoyage)))
            .andExpect(status().isCreated());

        // Validate the StateVoyage in the database
        List<StateVoyage> stateVoyageList = stateVoyageRepository.findAll();
        assertThat(stateVoyageList).hasSize(databaseSizeBeforeCreate + 1);
        StateVoyage testStateVoyage = stateVoyageList.get(stateVoyageList.size() - 1);
        assertThat(testStateVoyage.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testStateVoyage.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testStateVoyage.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the StateVoyage in Elasticsearch
        StateVoyage stateVoyageEs = stateVoyageSearchRepository.findOne(testStateVoyage.getId());
        assertThat(stateVoyageEs).isEqualToIgnoringGivenFields(testStateVoyage);
    }

    @Test
    @Transactional
    public void createStateVoyageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = stateVoyageRepository.findAll().size();

        // Create the StateVoyage with an existing ID
        stateVoyage.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStateVoyageMockMvc.perform(post("/api/state-voyages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stateVoyage)))
            .andExpect(status().isBadRequest());

        // Validate the StateVoyage in the database
        List<StateVoyage> stateVoyageList = stateVoyageRepository.findAll();
        assertThat(stateVoyageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = stateVoyageRepository.findAll().size();
        // set the field null
        stateVoyage.setCode(null);

        // Create the StateVoyage, which fails.

        restStateVoyageMockMvc.perform(post("/api/state-voyages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stateVoyage)))
            .andExpect(status().isBadRequest());

        List<StateVoyage> stateVoyageList = stateVoyageRepository.findAll();
        assertThat(stateVoyageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = stateVoyageRepository.findAll().size();
        // set the field null
        stateVoyage.setName(null);

        // Create the StateVoyage, which fails.

        restStateVoyageMockMvc.perform(post("/api/state-voyages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stateVoyage)))
            .andExpect(status().isBadRequest());

        List<StateVoyage> stateVoyageList = stateVoyageRepository.findAll();
        assertThat(stateVoyageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStateVoyages() throws Exception {
        // Initialize the database
        stateVoyageRepository.saveAndFlush(stateVoyage);

        // Get all the stateVoyageList
        restStateVoyageMockMvc.perform(get("/api/state-voyages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stateVoyage.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getStateVoyage() throws Exception {
        // Initialize the database
        stateVoyageRepository.saveAndFlush(stateVoyage);

        // Get the stateVoyage
        restStateVoyageMockMvc.perform(get("/api/state-voyages/{id}", stateVoyage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(stateVoyage.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingStateVoyage() throws Exception {
        // Get the stateVoyage
        restStateVoyageMockMvc.perform(get("/api/state-voyages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStateVoyage() throws Exception {
        // Initialize the database
        stateVoyageRepository.saveAndFlush(stateVoyage);
        stateVoyageSearchRepository.save(stateVoyage);
        int databaseSizeBeforeUpdate = stateVoyageRepository.findAll().size();

        // Update the stateVoyage
        StateVoyage updatedStateVoyage = stateVoyageRepository.findOne(stateVoyage.getId());
        // Disconnect from session so that the updates on updatedStateVoyage are not directly saved in db
        em.detach(updatedStateVoyage);
        updatedStateVoyage
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restStateVoyageMockMvc.perform(put("/api/state-voyages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedStateVoyage)))
            .andExpect(status().isOk());

        // Validate the StateVoyage in the database
        List<StateVoyage> stateVoyageList = stateVoyageRepository.findAll();
        assertThat(stateVoyageList).hasSize(databaseSizeBeforeUpdate);
        StateVoyage testStateVoyage = stateVoyageList.get(stateVoyageList.size() - 1);
        assertThat(testStateVoyage.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testStateVoyage.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testStateVoyage.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the StateVoyage in Elasticsearch
        StateVoyage stateVoyageEs = stateVoyageSearchRepository.findOne(testStateVoyage.getId());
        assertThat(stateVoyageEs).isEqualToIgnoringGivenFields(testStateVoyage);
    }

    @Test
    @Transactional
    public void updateNonExistingStateVoyage() throws Exception {
        int databaseSizeBeforeUpdate = stateVoyageRepository.findAll().size();

        // Create the StateVoyage

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restStateVoyageMockMvc.perform(put("/api/state-voyages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stateVoyage)))
            .andExpect(status().isCreated());

        // Validate the StateVoyage in the database
        List<StateVoyage> stateVoyageList = stateVoyageRepository.findAll();
        assertThat(stateVoyageList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteStateVoyage() throws Exception {
        // Initialize the database
        stateVoyageRepository.saveAndFlush(stateVoyage);
        stateVoyageSearchRepository.save(stateVoyage);
        int databaseSizeBeforeDelete = stateVoyageRepository.findAll().size();

        // Get the stateVoyage
        restStateVoyageMockMvc.perform(delete("/api/state-voyages/{id}", stateVoyage.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean stateVoyageExistsInEs = stateVoyageSearchRepository.exists(stateVoyage.getId());
        assertThat(stateVoyageExistsInEs).isFalse();

        // Validate the database is empty
        List<StateVoyage> stateVoyageList = stateVoyageRepository.findAll();
        assertThat(stateVoyageList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchStateVoyage() throws Exception {
        // Initialize the database
        stateVoyageRepository.saveAndFlush(stateVoyage);
        stateVoyageSearchRepository.save(stateVoyage);

        // Search the stateVoyage
        restStateVoyageMockMvc.perform(get("/api/_search/state-voyages?query=id:" + stateVoyage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stateVoyage.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StateVoyage.class);
        StateVoyage stateVoyage1 = new StateVoyage();
        stateVoyage1.setId(1L);
        StateVoyage stateVoyage2 = new StateVoyage();
        stateVoyage2.setId(stateVoyage1.getId());
        assertThat(stateVoyage1).isEqualTo(stateVoyage2);
        stateVoyage2.setId(2L);
        assertThat(stateVoyage1).isNotEqualTo(stateVoyage2);
        stateVoyage1.setId(null);
        assertThat(stateVoyage1).isNotEqualTo(stateVoyage2);
    }
}
