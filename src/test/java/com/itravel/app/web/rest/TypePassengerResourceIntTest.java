package com.itravel.app.web.rest;

import com.itravel.app.CamTravel2App;

import com.itravel.app.domain.TypePassenger;
import com.itravel.app.repository.TypePassengerRepository;
import com.itravel.app.repository.search.TypePassengerSearchRepository;
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
 * Test class for the TypePassengerResource REST controller.
 *
 * @see TypePassengerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CamTravel2App.class)
public class TypePassengerResourceIntTest {

    private static final String DEFAULT_CODE = "AAA";
    private static final String UPDATED_CODE = "BBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private TypePassengerRepository typePassengerRepository;

    @Autowired
    private TypePassengerSearchRepository typePassengerSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTypePassengerMockMvc;

    private TypePassenger typePassenger;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TypePassengerResource typePassengerResource = new TypePassengerResource(typePassengerRepository, typePassengerSearchRepository);
        this.restTypePassengerMockMvc = MockMvcBuilders.standaloneSetup(typePassengerResource)
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
    public static TypePassenger createEntity(EntityManager em) {
        TypePassenger typePassenger = new TypePassenger()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return typePassenger;
    }

    @Before
    public void initTest() {
        typePassengerSearchRepository.deleteAll();
        typePassenger = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypePassenger() throws Exception {
        int databaseSizeBeforeCreate = typePassengerRepository.findAll().size();

        // Create the TypePassenger
        restTypePassengerMockMvc.perform(post("/api/type-passengers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typePassenger)))
            .andExpect(status().isCreated());

        // Validate the TypePassenger in the database
        List<TypePassenger> typePassengerList = typePassengerRepository.findAll();
        assertThat(typePassengerList).hasSize(databaseSizeBeforeCreate + 1);
        TypePassenger testTypePassenger = typePassengerList.get(typePassengerList.size() - 1);
        assertThat(testTypePassenger.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testTypePassenger.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTypePassenger.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the TypePassenger in Elasticsearch
        TypePassenger typePassengerEs = typePassengerSearchRepository.findOne(testTypePassenger.getId());
        assertThat(typePassengerEs).isEqualToIgnoringGivenFields(testTypePassenger);
    }

    @Test
    @Transactional
    public void createTypePassengerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typePassengerRepository.findAll().size();

        // Create the TypePassenger with an existing ID
        typePassenger.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypePassengerMockMvc.perform(post("/api/type-passengers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typePassenger)))
            .andExpect(status().isBadRequest());

        // Validate the TypePassenger in the database
        List<TypePassenger> typePassengerList = typePassengerRepository.findAll();
        assertThat(typePassengerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = typePassengerRepository.findAll().size();
        // set the field null
        typePassenger.setCode(null);

        // Create the TypePassenger, which fails.

        restTypePassengerMockMvc.perform(post("/api/type-passengers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typePassenger)))
            .andExpect(status().isBadRequest());

        List<TypePassenger> typePassengerList = typePassengerRepository.findAll();
        assertThat(typePassengerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = typePassengerRepository.findAll().size();
        // set the field null
        typePassenger.setName(null);

        // Create the TypePassenger, which fails.

        restTypePassengerMockMvc.perform(post("/api/type-passengers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typePassenger)))
            .andExpect(status().isBadRequest());

        List<TypePassenger> typePassengerList = typePassengerRepository.findAll();
        assertThat(typePassengerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTypePassengers() throws Exception {
        // Initialize the database
        typePassengerRepository.saveAndFlush(typePassenger);

        // Get all the typePassengerList
        restTypePassengerMockMvc.perform(get("/api/type-passengers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typePassenger.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getTypePassenger() throws Exception {
        // Initialize the database
        typePassengerRepository.saveAndFlush(typePassenger);

        // Get the typePassenger
        restTypePassengerMockMvc.perform(get("/api/type-passengers/{id}", typePassenger.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(typePassenger.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTypePassenger() throws Exception {
        // Get the typePassenger
        restTypePassengerMockMvc.perform(get("/api/type-passengers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypePassenger() throws Exception {
        // Initialize the database
        typePassengerRepository.saveAndFlush(typePassenger);
        typePassengerSearchRepository.save(typePassenger);
        int databaseSizeBeforeUpdate = typePassengerRepository.findAll().size();

        // Update the typePassenger
        TypePassenger updatedTypePassenger = typePassengerRepository.findOne(typePassenger.getId());
        // Disconnect from session so that the updates on updatedTypePassenger are not directly saved in db
        em.detach(updatedTypePassenger);
        updatedTypePassenger
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restTypePassengerMockMvc.perform(put("/api/type-passengers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTypePassenger)))
            .andExpect(status().isOk());

        // Validate the TypePassenger in the database
        List<TypePassenger> typePassengerList = typePassengerRepository.findAll();
        assertThat(typePassengerList).hasSize(databaseSizeBeforeUpdate);
        TypePassenger testTypePassenger = typePassengerList.get(typePassengerList.size() - 1);
        assertThat(testTypePassenger.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTypePassenger.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTypePassenger.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the TypePassenger in Elasticsearch
        TypePassenger typePassengerEs = typePassengerSearchRepository.findOne(testTypePassenger.getId());
        assertThat(typePassengerEs).isEqualToIgnoringGivenFields(testTypePassenger);
    }

    @Test
    @Transactional
    public void updateNonExistingTypePassenger() throws Exception {
        int databaseSizeBeforeUpdate = typePassengerRepository.findAll().size();

        // Create the TypePassenger

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTypePassengerMockMvc.perform(put("/api/type-passengers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typePassenger)))
            .andExpect(status().isCreated());

        // Validate the TypePassenger in the database
        List<TypePassenger> typePassengerList = typePassengerRepository.findAll();
        assertThat(typePassengerList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTypePassenger() throws Exception {
        // Initialize the database
        typePassengerRepository.saveAndFlush(typePassenger);
        typePassengerSearchRepository.save(typePassenger);
        int databaseSizeBeforeDelete = typePassengerRepository.findAll().size();

        // Get the typePassenger
        restTypePassengerMockMvc.perform(delete("/api/type-passengers/{id}", typePassenger.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean typePassengerExistsInEs = typePassengerSearchRepository.exists(typePassenger.getId());
        assertThat(typePassengerExistsInEs).isFalse();

        // Validate the database is empty
        List<TypePassenger> typePassengerList = typePassengerRepository.findAll();
        assertThat(typePassengerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTypePassenger() throws Exception {
        // Initialize the database
        typePassengerRepository.saveAndFlush(typePassenger);
        typePassengerSearchRepository.save(typePassenger);

        // Search the typePassenger
        restTypePassengerMockMvc.perform(get("/api/_search/type-passengers?query=id:" + typePassenger.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typePassenger.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypePassenger.class);
        TypePassenger typePassenger1 = new TypePassenger();
        typePassenger1.setId(1L);
        TypePassenger typePassenger2 = new TypePassenger();
        typePassenger2.setId(typePassenger1.getId());
        assertThat(typePassenger1).isEqualTo(typePassenger2);
        typePassenger2.setId(2L);
        assertThat(typePassenger1).isNotEqualTo(typePassenger2);
        typePassenger1.setId(null);
        assertThat(typePassenger1).isNotEqualTo(typePassenger2);
    }
}
