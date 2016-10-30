package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.TaagliApp;

import com.mycompany.myapp.domain.Enseignant;
import com.mycompany.myapp.repository.EnseignantRepository;

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
 * Test class for the EnseignantResource REST controller.
 *
 * @see EnseignantResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TaagliApp.class)
public class EnseignantResourceIntTest {

    private static final String DEFAULT_NOM_ENS = "AAAAA";
    private static final String UPDATED_NOM_ENS = "BBBBB";

    private static final String DEFAULT_PRENOM_ENS = "AAAAA";
    private static final String UPDATED_PRENOM_ENS = "BBBBB";

    private static final String DEFAULT_TEL_ENS = "AAAAA";
    private static final String UPDATED_TEL_ENS = "BBBBB";

    private static final String DEFAULT_MAIL_ENS = "AAAAA";
    private static final String UPDATED_MAIL_ENS = "BBBBB";

    @Inject
    private EnseignantRepository enseignantRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restEnseignantMockMvc;

    private Enseignant enseignant;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EnseignantResource enseignantResource = new EnseignantResource();
        ReflectionTestUtils.setField(enseignantResource, "enseignantRepository", enseignantRepository);
        this.restEnseignantMockMvc = MockMvcBuilders.standaloneSetup(enseignantResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Enseignant createEntity(EntityManager em) {
        Enseignant enseignant = new Enseignant()
                .nomEns(DEFAULT_NOM_ENS)
                .prenomEns(DEFAULT_PRENOM_ENS)
                .telEns(DEFAULT_TEL_ENS)
                .mailEns(DEFAULT_MAIL_ENS);
        return enseignant;
    }

    @Before
    public void initTest() {
        enseignant = createEntity(em);
    }

    @Test
    @Transactional
    public void createEnseignant() throws Exception {
        int databaseSizeBeforeCreate = enseignantRepository.findAll().size();

        // Create the Enseignant

        restEnseignantMockMvc.perform(post("/api/enseignants")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(enseignant)))
                .andExpect(status().isCreated());

        // Validate the Enseignant in the database
        List<Enseignant> enseignants = enseignantRepository.findAll();
        assertThat(enseignants).hasSize(databaseSizeBeforeCreate + 1);
        Enseignant testEnseignant = enseignants.get(enseignants.size() - 1);
        assertThat(testEnseignant.getNomEns()).isEqualTo(DEFAULT_NOM_ENS);
        assertThat(testEnseignant.getPrenomEns()).isEqualTo(DEFAULT_PRENOM_ENS);
        assertThat(testEnseignant.getTelEns()).isEqualTo(DEFAULT_TEL_ENS);
        assertThat(testEnseignant.getMailEns()).isEqualTo(DEFAULT_MAIL_ENS);
    }

    @Test
    @Transactional
    public void getAllEnseignants() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);

        // Get all the enseignants
        restEnseignantMockMvc.perform(get("/api/enseignants?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(enseignant.getId().intValue())))
                .andExpect(jsonPath("$.[*].nomEns").value(hasItem(DEFAULT_NOM_ENS.toString())))
                .andExpect(jsonPath("$.[*].prenomEns").value(hasItem(DEFAULT_PRENOM_ENS.toString())))
                .andExpect(jsonPath("$.[*].telEns").value(hasItem(DEFAULT_TEL_ENS.toString())))
                .andExpect(jsonPath("$.[*].mailEns").value(hasItem(DEFAULT_MAIL_ENS.toString())));
    }

    @Test
    @Transactional
    public void getEnseignant() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);

        // Get the enseignant
        restEnseignantMockMvc.perform(get("/api/enseignants/{id}", enseignant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(enseignant.getId().intValue()))
            .andExpect(jsonPath("$.nomEns").value(DEFAULT_NOM_ENS.toString()))
            .andExpect(jsonPath("$.prenomEns").value(DEFAULT_PRENOM_ENS.toString()))
            .andExpect(jsonPath("$.telEns").value(DEFAULT_TEL_ENS.toString()))
            .andExpect(jsonPath("$.mailEns").value(DEFAULT_MAIL_ENS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEnseignant() throws Exception {
        // Get the enseignant
        restEnseignantMockMvc.perform(get("/api/enseignants/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEnseignant() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);
        int databaseSizeBeforeUpdate = enseignantRepository.findAll().size();

        // Update the enseignant
        Enseignant updatedEnseignant = enseignantRepository.findOne(enseignant.getId());
        updatedEnseignant
                .nomEns(UPDATED_NOM_ENS)
                .prenomEns(UPDATED_PRENOM_ENS)
                .telEns(UPDATED_TEL_ENS)
                .mailEns(UPDATED_MAIL_ENS);

        restEnseignantMockMvc.perform(put("/api/enseignants")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedEnseignant)))
                .andExpect(status().isOk());

        // Validate the Enseignant in the database
        List<Enseignant> enseignants = enseignantRepository.findAll();
        assertThat(enseignants).hasSize(databaseSizeBeforeUpdate);
        Enseignant testEnseignant = enseignants.get(enseignants.size() - 1);
        assertThat(testEnseignant.getNomEns()).isEqualTo(UPDATED_NOM_ENS);
        assertThat(testEnseignant.getPrenomEns()).isEqualTo(UPDATED_PRENOM_ENS);
        assertThat(testEnseignant.getTelEns()).isEqualTo(UPDATED_TEL_ENS);
        assertThat(testEnseignant.getMailEns()).isEqualTo(UPDATED_MAIL_ENS);
    }

    @Test
    @Transactional
    public void deleteEnseignant() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);
        int databaseSizeBeforeDelete = enseignantRepository.findAll().size();

        // Get the enseignant
        restEnseignantMockMvc.perform(delete("/api/enseignants/{id}", enseignant.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Enseignant> enseignants = enseignantRepository.findAll();
        assertThat(enseignants).hasSize(databaseSizeBeforeDelete - 1);
    }
}
