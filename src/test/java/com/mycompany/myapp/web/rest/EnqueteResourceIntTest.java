package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.TaagliApp;

import com.mycompany.myapp.domain.Enquete;
import com.mycompany.myapp.repository.EnqueteRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the EnqueteResource REST controller.
 *
 * @see EnqueteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TaagliApp.class)
public class EnqueteResourceIntTest {

    private static final String DEFAULT_SUJET = "AAAAA";
    private static final String UPDATED_SUJET = "BBBBB";

    private static final String DEFAULT_DETAILS = "AAAAA";
    private static final String UPDATED_DETAILS = "BBBBB";

    private static final String DEFAULT_REPONSE = "AAAAA";
    private static final String UPDATED_REPONSE = "BBBBB";

    @Inject
    private EnqueteRepository enqueteRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restEnqueteMockMvc;

    private Enquete enquete;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EnqueteResource enqueteResource = new EnqueteResource();
        ReflectionTestUtils.setField(enqueteResource, "enqueteRepository", enqueteRepository);
        this.restEnqueteMockMvc = MockMvcBuilders.standaloneSetup(enqueteResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Enquete createEntity(EntityManager em) {
        Enquete enquete = new Enquete()
                .sujet(DEFAULT_SUJET)
                .details(DEFAULT_DETAILS)
                .reponse(DEFAULT_REPONSE);
        return enquete;
    }

    @Before
    public void initTest() {
        enquete = createEntity(em);
    }

    @Test
    @Transactional
    public void createEnquete() throws Exception {
        int databaseSizeBeforeCreate = enqueteRepository.findAll().size();

        // Create the Enquete

        restEnqueteMockMvc.perform(post("/api/enquetes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(enquete)))
                .andExpect(status().isCreated());

        // Validate the Enquete in the database
        List<Enquete> enquetes = enqueteRepository.findAll();
        assertThat(enquetes).hasSize(databaseSizeBeforeCreate + 1);
        Enquete testEnquete = enquetes.get(enquetes.size() - 1);
        assertThat(testEnquete.getSujet()).isEqualTo(DEFAULT_SUJET);
        assertThat(testEnquete.getDetails()).isEqualTo(DEFAULT_DETAILS);
        assertThat(testEnquete.getReponse()).isEqualTo(DEFAULT_REPONSE);
    }

    @Test
    @Transactional
    public void checkSujetIsRequired() throws Exception {
        int databaseSizeBeforeTest = enqueteRepository.findAll().size();
        // set the field null
        enquete.setSujet(null);

        // Create the Enquete, which fails.

        restEnqueteMockMvc.perform(post("/api/enquetes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(enquete)))
                .andExpect(status().isBadRequest());

        List<Enquete> enquetes = enqueteRepository.findAll();
        assertThat(enquetes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDetailsIsRequired() throws Exception {
        int databaseSizeBeforeTest = enqueteRepository.findAll().size();
        // set the field null
        enquete.setDetails(null);

        // Create the Enquete, which fails.

        restEnqueteMockMvc.perform(post("/api/enquetes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(enquete)))
                .andExpect(status().isBadRequest());

        List<Enquete> enquetes = enqueteRepository.findAll();
        assertThat(enquetes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEnquetes() throws Exception {
        // Initialize the database
        enqueteRepository.saveAndFlush(enquete);

        // Get all the enquetes
        restEnqueteMockMvc.perform(get("/api/enquetes?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(enquete.getId().intValue())))
                .andExpect(jsonPath("$.[*].sujet").value(hasItem(DEFAULT_SUJET.toString())))
                .andExpect(jsonPath("$.[*].details").value(hasItem(DEFAULT_DETAILS.toString())))
                .andExpect(jsonPath("$.[*].reponse").value(hasItem(DEFAULT_REPONSE.toString())));
    }

    @Test
    @Transactional
    public void getEnquete() throws Exception {
        // Initialize the database
        enqueteRepository.saveAndFlush(enquete);

        // Get the enquete
        restEnqueteMockMvc.perform(get("/api/enquetes/{id}", enquete.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(enquete.getId().intValue()))
            .andExpect(jsonPath("$.sujet").value(DEFAULT_SUJET.toString()))
            .andExpect(jsonPath("$.details").value(DEFAULT_DETAILS.toString()))
            .andExpect(jsonPath("$.reponse").value(DEFAULT_REPONSE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEnquete() throws Exception {
        // Get the enquete
        restEnqueteMockMvc.perform(get("/api/enquetes/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEnquete() throws Exception {
        // Initialize the database
        enqueteRepository.saveAndFlush(enquete);
        int databaseSizeBeforeUpdate = enqueteRepository.findAll().size();

        // Update the enquete
        Enquete updatedEnquete = enqueteRepository.findOne(enquete.getId());
        updatedEnquete
                .sujet(UPDATED_SUJET)
                .details(UPDATED_DETAILS)
                .reponse(UPDATED_REPONSE);

        restEnqueteMockMvc.perform(put("/api/enquetes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedEnquete)))
                .andExpect(status().isOk());

        // Validate the Enquete in the database
        List<Enquete> enquetes = enqueteRepository.findAll();
        assertThat(enquetes).hasSize(databaseSizeBeforeUpdate);
        Enquete testEnquete = enquetes.get(enquetes.size() - 1);
        assertThat(testEnquete.getSujet()).isEqualTo(UPDATED_SUJET);
        assertThat(testEnquete.getDetails()).isEqualTo(UPDATED_DETAILS);
        assertThat(testEnquete.getReponse()).isEqualTo(UPDATED_REPONSE);
    }

    @Test
    @Transactional
    public void deleteEnquete() throws Exception {
        // Initialize the database
        enqueteRepository.saveAndFlush(enquete);
        int databaseSizeBeforeDelete = enqueteRepository.findAll().size();

        // Get the enquete
        restEnqueteMockMvc.perform(delete("/api/enquetes/{id}", enquete.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Enquete> enquetes = enqueteRepository.findAll();
        assertThat(enquetes).hasSize(databaseSizeBeforeDelete - 1);
    }
}
