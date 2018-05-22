package com.itravel.app.web.rest;

import com.itravel.app.CamTravel2App;

import com.itravel.app.domain.ModePayment;
import com.itravel.app.repository.ModePaymentRepository;
import com.itravel.app.repository.search.ModePaymentSearchRepository;
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
 * Test class for the ModePaymentResource REST controller.
 *
 * @see ModePaymentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CamTravel2App.class)
public class ModePaymentResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private ModePaymentRepository modePaymentRepository;

    @Autowired
    private ModePaymentSearchRepository modePaymentSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restModePaymentMockMvc;

    private ModePayment modePayment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ModePaymentResource modePaymentResource = new ModePaymentResource(modePaymentRepository, modePaymentSearchRepository);
        this.restModePaymentMockMvc = MockMvcBuilders.standaloneSetup(modePaymentResource)
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
    public static ModePayment createEntity(EntityManager em) {
        ModePayment modePayment = new ModePayment()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME);
        return modePayment;
    }

    @Before
    public void initTest() {
        modePaymentSearchRepository.deleteAll();
        modePayment = createEntity(em);
    }

    @Test
    @Transactional
    public void createModePayment() throws Exception {
        int databaseSizeBeforeCreate = modePaymentRepository.findAll().size();

        // Create the ModePayment
        restModePaymentMockMvc.perform(post("/api/mode-payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modePayment)))
            .andExpect(status().isCreated());

        // Validate the ModePayment in the database
        List<ModePayment> modePaymentList = modePaymentRepository.findAll();
        assertThat(modePaymentList).hasSize(databaseSizeBeforeCreate + 1);
        ModePayment testModePayment = modePaymentList.get(modePaymentList.size() - 1);
        assertThat(testModePayment.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testModePayment.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the ModePayment in Elasticsearch
        ModePayment modePaymentEs = modePaymentSearchRepository.findOne(testModePayment.getId());
        assertThat(modePaymentEs).isEqualToIgnoringGivenFields(testModePayment);
    }

    @Test
    @Transactional
    public void createModePaymentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = modePaymentRepository.findAll().size();

        // Create the ModePayment with an existing ID
        modePayment.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restModePaymentMockMvc.perform(post("/api/mode-payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modePayment)))
            .andExpect(status().isBadRequest());

        // Validate the ModePayment in the database
        List<ModePayment> modePaymentList = modePaymentRepository.findAll();
        assertThat(modePaymentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = modePaymentRepository.findAll().size();
        // set the field null
        modePayment.setCode(null);

        // Create the ModePayment, which fails.

        restModePaymentMockMvc.perform(post("/api/mode-payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modePayment)))
            .andExpect(status().isBadRequest());

        List<ModePayment> modePaymentList = modePaymentRepository.findAll();
        assertThat(modePaymentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = modePaymentRepository.findAll().size();
        // set the field null
        modePayment.setName(null);

        // Create the ModePayment, which fails.

        restModePaymentMockMvc.perform(post("/api/mode-payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modePayment)))
            .andExpect(status().isBadRequest());

        List<ModePayment> modePaymentList = modePaymentRepository.findAll();
        assertThat(modePaymentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllModePayments() throws Exception {
        // Initialize the database
        modePaymentRepository.saveAndFlush(modePayment);

        // Get all the modePaymentList
        restModePaymentMockMvc.perform(get("/api/mode-payments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(modePayment.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getModePayment() throws Exception {
        // Initialize the database
        modePaymentRepository.saveAndFlush(modePayment);

        // Get the modePayment
        restModePaymentMockMvc.perform(get("/api/mode-payments/{id}", modePayment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(modePayment.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingModePayment() throws Exception {
        // Get the modePayment
        restModePaymentMockMvc.perform(get("/api/mode-payments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateModePayment() throws Exception {
        // Initialize the database
        modePaymentRepository.saveAndFlush(modePayment);
        modePaymentSearchRepository.save(modePayment);
        int databaseSizeBeforeUpdate = modePaymentRepository.findAll().size();

        // Update the modePayment
        ModePayment updatedModePayment = modePaymentRepository.findOne(modePayment.getId());
        // Disconnect from session so that the updates on updatedModePayment are not directly saved in db
        em.detach(updatedModePayment);
        updatedModePayment
            .code(UPDATED_CODE)
            .name(UPDATED_NAME);

        restModePaymentMockMvc.perform(put("/api/mode-payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedModePayment)))
            .andExpect(status().isOk());

        // Validate the ModePayment in the database
        List<ModePayment> modePaymentList = modePaymentRepository.findAll();
        assertThat(modePaymentList).hasSize(databaseSizeBeforeUpdate);
        ModePayment testModePayment = modePaymentList.get(modePaymentList.size() - 1);
        assertThat(testModePayment.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testModePayment.getName()).isEqualTo(UPDATED_NAME);

        // Validate the ModePayment in Elasticsearch
        ModePayment modePaymentEs = modePaymentSearchRepository.findOne(testModePayment.getId());
        assertThat(modePaymentEs).isEqualToIgnoringGivenFields(testModePayment);
    }

    @Test
    @Transactional
    public void updateNonExistingModePayment() throws Exception {
        int databaseSizeBeforeUpdate = modePaymentRepository.findAll().size();

        // Create the ModePayment

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restModePaymentMockMvc.perform(put("/api/mode-payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modePayment)))
            .andExpect(status().isCreated());

        // Validate the ModePayment in the database
        List<ModePayment> modePaymentList = modePaymentRepository.findAll();
        assertThat(modePaymentList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteModePayment() throws Exception {
        // Initialize the database
        modePaymentRepository.saveAndFlush(modePayment);
        modePaymentSearchRepository.save(modePayment);
        int databaseSizeBeforeDelete = modePaymentRepository.findAll().size();

        // Get the modePayment
        restModePaymentMockMvc.perform(delete("/api/mode-payments/{id}", modePayment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean modePaymentExistsInEs = modePaymentSearchRepository.exists(modePayment.getId());
        assertThat(modePaymentExistsInEs).isFalse();

        // Validate the database is empty
        List<ModePayment> modePaymentList = modePaymentRepository.findAll();
        assertThat(modePaymentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchModePayment() throws Exception {
        // Initialize the database
        modePaymentRepository.saveAndFlush(modePayment);
        modePaymentSearchRepository.save(modePayment);

        // Search the modePayment
        restModePaymentMockMvc.perform(get("/api/_search/mode-payments?query=id:" + modePayment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(modePayment.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ModePayment.class);
        ModePayment modePayment1 = new ModePayment();
        modePayment1.setId(1L);
        ModePayment modePayment2 = new ModePayment();
        modePayment2.setId(modePayment1.getId());
        assertThat(modePayment1).isEqualTo(modePayment2);
        modePayment2.setId(2L);
        assertThat(modePayment1).isNotEqualTo(modePayment2);
        modePayment1.setId(null);
        assertThat(modePayment1).isNotEqualTo(modePayment2);
    }
}
