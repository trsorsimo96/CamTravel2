package com.itravel.app.web.rest;

import com.itravel.app.CamTravel2App;

import com.itravel.app.domain.TypeVoyage;
import com.itravel.app.repository.TypeVoyageRepository;
import com.itravel.app.repository.search.TypeVoyageSearchRepository;
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
 * Test class for the TypeVoyageResource REST controller.
 *
 * @see TypeVoyageResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CamTravel2App.class)
public class TypeVoyageResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private TypeVoyageRepository typeVoyageRepository;

    @Autowired
    private TypeVoyageSearchRepository typeVoyageSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTypeVoyageMockMvc;

    private TypeVoyage typeVoyage;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TypeVoyageResource typeVoyageResource = new TypeVoyageResource(typeVoyageRepository, typeVoyageSearchRepository);
        this.restTypeVoyageMockMvc = MockMvcBuilders.standaloneSetup(typeVoyageResource)
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
    public static TypeVoyage createEntity(EntityManager em) {
        TypeVoyage typeVoyage = new TypeVoyage()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME);
        return typeVoyage;
    }

    @Before
    public void initTest() {
        typeVoyageSearchRepository.deleteAll();
        typeVoyage = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeVoyage() throws Exception {
        int databaseSizeBeforeCreate = typeVoyageRepository.findAll().size();

        // Create the TypeVoyage
        restTypeVoyageMockMvc.perform(post("/api/type-voyages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeVoyage)))
            .andExpect(status().isCreated());

        // Validate the TypeVoyage in the database
        List<TypeVoyage> typeVoyageList = typeVoyageRepository.findAll();
        assertThat(typeVoyageList).hasSize(databaseSizeBeforeCreate + 1);
        TypeVoyage testTypeVoyage = typeVoyageList.get(typeVoyageList.size() - 1);
        assertThat(testTypeVoyage.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testTypeVoyage.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the TypeVoyage in Elasticsearch
        TypeVoyage typeVoyageEs = typeVoyageSearchRepository.findOne(testTypeVoyage.getId());
        assertThat(typeVoyageEs).isEqualToIgnoringGivenFields(testTypeVoyage);
    }

    @Test
    @Transactional
    public void createTypeVoyageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeVoyageRepository.findAll().size();

        // Create the TypeVoyage with an existing ID
        typeVoyage.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeVoyageMockMvc.perform(post("/api/type-voyages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeVoyage)))
            .andExpect(status().isBadRequest());

        // Validate the TypeVoyage in the database
        List<TypeVoyage> typeVoyageList = typeVoyageRepository.findAll();
        assertThat(typeVoyageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = typeVoyageRepository.findAll().size();
        // set the field null
        typeVoyage.setCode(null);

        // Create the TypeVoyage, which fails.

        restTypeVoyageMockMvc.perform(post("/api/type-voyages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeVoyage)))
            .andExpect(status().isBadRequest());

        List<TypeVoyage> typeVoyageList = typeVoyageRepository.findAll();
        assertThat(typeVoyageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = typeVoyageRepository.findAll().size();
        // set the field null
        typeVoyage.setName(null);

        // Create the TypeVoyage, which fails.

        restTypeVoyageMockMvc.perform(post("/api/type-voyages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeVoyage)))
            .andExpect(status().isBadRequest());

        List<TypeVoyage> typeVoyageList = typeVoyageRepository.findAll();
        assertThat(typeVoyageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTypeVoyages() throws Exception {
        // Initialize the database
        typeVoyageRepository.saveAndFlush(typeVoyage);

        // Get all the typeVoyageList
        restTypeVoyageMockMvc.perform(get("/api/type-voyages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeVoyage.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getTypeVoyage() throws Exception {
        // Initialize the database
        typeVoyageRepository.saveAndFlush(typeVoyage);

        // Get the typeVoyage
        restTypeVoyageMockMvc.perform(get("/api/type-voyages/{id}", typeVoyage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(typeVoyage.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTypeVoyage() throws Exception {
        // Get the typeVoyage
        restTypeVoyageMockMvc.perform(get("/api/type-voyages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeVoyage() throws Exception {
        // Initialize the database
        typeVoyageRepository.saveAndFlush(typeVoyage);
        typeVoyageSearchRepository.save(typeVoyage);
        int databaseSizeBeforeUpdate = typeVoyageRepository.findAll().size();

        // Update the typeVoyage
        TypeVoyage updatedTypeVoyage = typeVoyageRepository.findOne(typeVoyage.getId());
        // Disconnect from session so that the updates on updatedTypeVoyage are not directly saved in db
        em.detach(updatedTypeVoyage);
        updatedTypeVoyage
            .code(UPDATED_CODE)
            .name(UPDATED_NAME);

        restTypeVoyageMockMvc.perform(put("/api/type-voyages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTypeVoyage)))
            .andExpect(status().isOk());

        // Validate the TypeVoyage in the database
        List<TypeVoyage> typeVoyageList = typeVoyageRepository.findAll();
        assertThat(typeVoyageList).hasSize(databaseSizeBeforeUpdate);
        TypeVoyage testTypeVoyage = typeVoyageList.get(typeVoyageList.size() - 1);
        assertThat(testTypeVoyage.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTypeVoyage.getName()).isEqualTo(UPDATED_NAME);

        // Validate the TypeVoyage in Elasticsearch
        TypeVoyage typeVoyageEs = typeVoyageSearchRepository.findOne(testTypeVoyage.getId());
        assertThat(typeVoyageEs).isEqualToIgnoringGivenFields(testTypeVoyage);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeVoyage() throws Exception {
        int databaseSizeBeforeUpdate = typeVoyageRepository.findAll().size();

        // Create the TypeVoyage

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTypeVoyageMockMvc.perform(put("/api/type-voyages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeVoyage)))
            .andExpect(status().isCreated());

        // Validate the TypeVoyage in the database
        List<TypeVoyage> typeVoyageList = typeVoyageRepository.findAll();
        assertThat(typeVoyageList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTypeVoyage() throws Exception {
        // Initialize the database
        typeVoyageRepository.saveAndFlush(typeVoyage);
        typeVoyageSearchRepository.save(typeVoyage);
        int databaseSizeBeforeDelete = typeVoyageRepository.findAll().size();

        // Get the typeVoyage
        restTypeVoyageMockMvc.perform(delete("/api/type-voyages/{id}", typeVoyage.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean typeVoyageExistsInEs = typeVoyageSearchRepository.exists(typeVoyage.getId());
        assertThat(typeVoyageExistsInEs).isFalse();

        // Validate the database is empty
        List<TypeVoyage> typeVoyageList = typeVoyageRepository.findAll();
        assertThat(typeVoyageList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTypeVoyage() throws Exception {
        // Initialize the database
        typeVoyageRepository.saveAndFlush(typeVoyage);
        typeVoyageSearchRepository.save(typeVoyage);

        // Search the typeVoyage
        restTypeVoyageMockMvc.perform(get("/api/_search/type-voyages?query=id:" + typeVoyage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeVoyage.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeVoyage.class);
        TypeVoyage typeVoyage1 = new TypeVoyage();
        typeVoyage1.setId(1L);
        TypeVoyage typeVoyage2 = new TypeVoyage();
        typeVoyage2.setId(typeVoyage1.getId());
        assertThat(typeVoyage1).isEqualTo(typeVoyage2);
        typeVoyage2.setId(2L);
        assertThat(typeVoyage1).isNotEqualTo(typeVoyage2);
        typeVoyage1.setId(null);
        assertThat(typeVoyage1).isNotEqualTo(typeVoyage2);
    }
}
