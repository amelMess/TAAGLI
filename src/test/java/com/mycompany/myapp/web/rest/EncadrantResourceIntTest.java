package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.TaagliApp;

import com.mycompany.myapp.domain.Encadrant;
import com.mycompany.myapp.repository.EncadrantRepository;

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
 * Test class for the EncadrantResource REST controller.
 *
 * @see EncadrantResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TaagliApp.class)
public class EncadrantResourceIntTest {

    private static final String DEFAULT_NOM_ENC = "AAAAA";
    private static final String UPDATED_NOM_ENC = "BBBBB";

    private static final String DEFAULT_PRENOM_ENC = "AAAAA";
    private static final String UPDATED_PRENOM_ENC = "BBBBB";

    private static final String DEFAULT_TEL_ENC = "AAAAA";
    private static final String UPDATED_TEL_ENC = "BBBBB";

    private static final String DEFAULT_MAIL_ENC = "AAAAA";
    private static final String UPDATED_MAIL_ENC = "BBBBB";

    @Inject
    private EncadrantRepository encadrantRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restEncadrantMockMvc;

    private Encadrant encadrant;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EncadrantResource encadrantResource = new EncadrantResource();
        ReflectionTestUtils.setField(encadrantResource, "encadrantRepository", encadrantRepository);
        this.restEncadrantMockMvc = MockMvcBuilders.standaloneSetup(encadrantResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Encadrant createEntity(EntityManager em) {
        Encadrant encadrant = new Encadrant()
                .nomEnc(DEFAULT_NOM_ENC)
                .prenomEnc(DEFAULT_PRENOM_ENC)
                .telEnc(DEFAULT_TEL_ENC)
                .mailEnc(DEFAULT_MAIL_ENC);
        return encadrant;
    }

    @Before
    public void initTest() {
        encadrant = createEntity(em);
    }

    @Test
    @Transactional
    public void createEncadrant() throws Exception {
        int databaseSizeBeforeCreate = encadrantRepository.findAll().size();

        // Create the Encadrant

        restEncadrantMockMvc.perform(post("/api/encadrants")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(encadrant)))
                .andExpect(status().isCreated());

        // Validate the Encadrant in the database
        List<Encadrant> encadrants = encadrantRepository.findAll();
        assertThat(encadrants).hasSize(databaseSizeBeforeCreate + 1);
        Encadrant testEncadrant = encadrants.get(encadrants.size() - 1);
        assertThat(testEncadrant.getNomEnc()).isEqualTo(DEFAULT_NOM_ENC);
        assertThat(testEncadrant.getPrenomEnc()).isEqualTo(DEFAULT_PRENOM_ENC);
        assertThat(testEncadrant.getTelEnc()).isEqualTo(DEFAULT_TEL_ENC);
        assertThat(testEncadrant.getMailEnc()).isEqualTo(DEFAULT_MAIL_ENC);
    }

    @Test
    @Transactional
    public void getAllEncadrants() throws Exception {
        // Initialize the database
        encadrantRepository.saveAndFlush(encadrant);

        // Get all the encadrants
        restEncadrantMockMvc.perform(get("/api/encadrants?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(encadrant.getId().intValue())))
                .andExpect(jsonPath("$.[*].nomEnc").value(hasItem(DEFAULT_NOM_ENC.toString())))
                .andExpect(jsonPath("$.[*].prenomEnc").value(hasItem(DEFAULT_PRENOM_ENC.toString())))
                .andExpect(jsonPath("$.[*].telEnc").value(hasItem(DEFAULT_TEL_ENC.toString())))
                .andExpect(jsonPath("$.[*].mailEnc").value(hasItem(DEFAULT_MAIL_ENC.toString())));
    }

    @Test
    @Transactional
    public void getEncadrant() throws Exception {
        // Initialize the database
        encadrantRepository.saveAndFlush(encadrant);

        // Get the encadrant
        restEncadrantMockMvc.perform(get("/api/encadrants/{id}", encadrant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(encadrant.getId().intValue()))
            .andExpect(jsonPath("$.nomEnc").value(DEFAULT_NOM_ENC.toString()))
            .andExpect(jsonPath("$.prenomEnc").value(DEFAULT_PRENOM_ENC.toString()))
            .andExpect(jsonPath("$.telEnc").value(DEFAULT_TEL_ENC.toString()))
            .andExpect(jsonPath("$.mailEnc").value(DEFAULT_MAIL_ENC.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEncadrant() throws Exception {
        // Get the encadrant
        restEncadrantMockMvc.perform(get("/api/encadrants/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEncadrant() throws Exception {
        // Initialize the database
        encadrantRepository.saveAndFlush(encadrant);
        int databaseSizeBeforeUpdate = encadrantRepository.findAll().size();

        // Update the encadrant
        Encadrant updatedEncadrant = encadrantRepository.findOne(encadrant.getId());
        updatedEncadrant
                .nomEnc(UPDATED_NOM_ENC)
                .prenomEnc(UPDATED_PRENOM_ENC)
                .telEnc(UPDATED_TEL_ENC)
                .mailEnc(UPDATED_MAIL_ENC);

        restEncadrantMockMvc.perform(put("/api/encadrants")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedEncadrant)))
                .andExpect(status().isOk());

        // Validate the Encadrant in the database
        List<Encadrant> encadrants = encadrantRepository.findAll();
        assertThat(encadrants).hasSize(databaseSizeBeforeUpdate);
        Encadrant testEncadrant = encadrants.get(encadrants.size() - 1);
        assertThat(testEncadrant.getNomEnc()).isEqualTo(UPDATED_NOM_ENC);
        assertThat(testEncadrant.getPrenomEnc()).isEqualTo(UPDATED_PRENOM_ENC);
        assertThat(testEncadrant.getTelEnc()).isEqualTo(UPDATED_TEL_ENC);
        assertThat(testEncadrant.getMailEnc()).isEqualTo(UPDATED_MAIL_ENC);
    }

    @Test
    @Transactional
    public void deleteEncadrant() throws Exception {
        // Initialize the database
        encadrantRepository.saveAndFlush(encadrant);
        int databaseSizeBeforeDelete = encadrantRepository.findAll().size();

        // Get the encadrant
        restEncadrantMockMvc.perform(delete("/api/encadrants/{id}", encadrant.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Encadrant> encadrants = encadrantRepository.findAll();
        assertThat(encadrants).hasSize(databaseSizeBeforeDelete - 1);
    }
}
