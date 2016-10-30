package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.TaagliApp;

import com.mycompany.myapp.domain.Entreprise;
import com.mycompany.myapp.repository.EntrepriseRepository;

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
 * Test class for the EntrepriseResource REST controller.
 *
 * @see EntrepriseResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TaagliApp.class)
public class EntrepriseResourceIntTest {

    private static final String DEFAULT_NOM = "AAAAA";
    private static final String UPDATED_NOM = "BBBBB";

    private static final Integer DEFAULT_SIRET = 1;
    private static final Integer UPDATED_SIRET = 2;

    private static final String DEFAULT_SERVICE = "AAAAA";
    private static final String UPDATED_SERVICE = "BBBBB";

    private static final String DEFAULT_U_RL = "AAAAA";
    private static final String UPDATED_U_RL = "BBBBB";

    private static final Integer DEFAULT_TEL = 1;
    private static final Integer UPDATED_TEL = 2;

    @Inject
    private EntrepriseRepository entrepriseRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restEntrepriseMockMvc;

    private Entreprise entreprise;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EntrepriseResource entrepriseResource = new EntrepriseResource();
        ReflectionTestUtils.setField(entrepriseResource, "entrepriseRepository", entrepriseRepository);
        this.restEntrepriseMockMvc = MockMvcBuilders.standaloneSetup(entrepriseResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Entreprise createEntity(EntityManager em) {
        Entreprise entreprise = new Entreprise()
                .nom(DEFAULT_NOM)
                .siret(DEFAULT_SIRET)
                .service(DEFAULT_SERVICE)
                .uRl(DEFAULT_U_RL)
                .tel(DEFAULT_TEL);
        return entreprise;
    }

    @Before
    public void initTest() {
        entreprise = createEntity(em);
    }

    @Test
    @Transactional
    public void createEntreprise() throws Exception {
        int databaseSizeBeforeCreate = entrepriseRepository.findAll().size();

        // Create the Entreprise

        restEntrepriseMockMvc.perform(post("/api/entreprises")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(entreprise)))
                .andExpect(status().isCreated());

        // Validate the Entreprise in the database
        List<Entreprise> entreprises = entrepriseRepository.findAll();
        assertThat(entreprises).hasSize(databaseSizeBeforeCreate + 1);
        Entreprise testEntreprise = entreprises.get(entreprises.size() - 1);
        assertThat(testEntreprise.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testEntreprise.getSiret()).isEqualTo(DEFAULT_SIRET);
        assertThat(testEntreprise.getService()).isEqualTo(DEFAULT_SERVICE);
        assertThat(testEntreprise.getuRl()).isEqualTo(DEFAULT_U_RL);
        assertThat(testEntreprise.getTel()).isEqualTo(DEFAULT_TEL);
    }

    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = entrepriseRepository.findAll().size();
        // set the field null
        entreprise.setNom(null);

        // Create the Entreprise, which fails.

        restEntrepriseMockMvc.perform(post("/api/entreprises")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(entreprise)))
                .andExpect(status().isBadRequest());

        List<Entreprise> entreprises = entrepriseRepository.findAll();
        assertThat(entreprises).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSiretIsRequired() throws Exception {
        int databaseSizeBeforeTest = entrepriseRepository.findAll().size();
        // set the field null
        entreprise.setSiret(null);

        // Create the Entreprise, which fails.

        restEntrepriseMockMvc.perform(post("/api/entreprises")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(entreprise)))
                .andExpect(status().isBadRequest());

        List<Entreprise> entreprises = entrepriseRepository.findAll();
        assertThat(entreprises).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkServiceIsRequired() throws Exception {
        int databaseSizeBeforeTest = entrepriseRepository.findAll().size();
        // set the field null
        entreprise.setService(null);

        // Create the Entreprise, which fails.

        restEntrepriseMockMvc.perform(post("/api/entreprises")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(entreprise)))
                .andExpect(status().isBadRequest());

        List<Entreprise> entreprises = entrepriseRepository.findAll();
        assertThat(entreprises).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEntreprises() throws Exception {
        // Initialize the database
        entrepriseRepository.saveAndFlush(entreprise);

        // Get all the entreprises
        restEntrepriseMockMvc.perform(get("/api/entreprises?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(entreprise.getId().intValue())))
                .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
                .andExpect(jsonPath("$.[*].siret").value(hasItem(DEFAULT_SIRET)))
                .andExpect(jsonPath("$.[*].service").value(hasItem(DEFAULT_SERVICE.toString())))
                .andExpect(jsonPath("$.[*].uRl").value(hasItem(DEFAULT_U_RL.toString())))
                .andExpect(jsonPath("$.[*].tel").value(hasItem(DEFAULT_TEL)));
    }

    @Test
    @Transactional
    public void getEntreprise() throws Exception {
        // Initialize the database
        entrepriseRepository.saveAndFlush(entreprise);

        // Get the entreprise
        restEntrepriseMockMvc.perform(get("/api/entreprises/{id}", entreprise.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(entreprise.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.siret").value(DEFAULT_SIRET))
            .andExpect(jsonPath("$.service").value(DEFAULT_SERVICE.toString()))
            .andExpect(jsonPath("$.uRl").value(DEFAULT_U_RL.toString()))
            .andExpect(jsonPath("$.tel").value(DEFAULT_TEL));
    }

    @Test
    @Transactional
    public void getNonExistingEntreprise() throws Exception {
        // Get the entreprise
        restEntrepriseMockMvc.perform(get("/api/entreprises/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEntreprise() throws Exception {
        // Initialize the database
        entrepriseRepository.saveAndFlush(entreprise);
        int databaseSizeBeforeUpdate = entrepriseRepository.findAll().size();

        // Update the entreprise
        Entreprise updatedEntreprise = entrepriseRepository.findOne(entreprise.getId());
        updatedEntreprise
                .nom(UPDATED_NOM)
                .siret(UPDATED_SIRET)
                .service(UPDATED_SERVICE)
                .uRl(UPDATED_U_RL)
                .tel(UPDATED_TEL);

        restEntrepriseMockMvc.perform(put("/api/entreprises")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedEntreprise)))
                .andExpect(status().isOk());

        // Validate the Entreprise in the database
        List<Entreprise> entreprises = entrepriseRepository.findAll();
        assertThat(entreprises).hasSize(databaseSizeBeforeUpdate);
        Entreprise testEntreprise = entreprises.get(entreprises.size() - 1);
        assertThat(testEntreprise.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testEntreprise.getSiret()).isEqualTo(UPDATED_SIRET);
        assertThat(testEntreprise.getService()).isEqualTo(UPDATED_SERVICE);
        assertThat(testEntreprise.getuRl()).isEqualTo(UPDATED_U_RL);
        assertThat(testEntreprise.getTel()).isEqualTo(UPDATED_TEL);
    }

    @Test
    @Transactional
    public void deleteEntreprise() throws Exception {
        // Initialize the database
        entrepriseRepository.saveAndFlush(entreprise);
        int databaseSizeBeforeDelete = entrepriseRepository.findAll().size();

        // Get the entreprise
        restEntrepriseMockMvc.perform(delete("/api/entreprises/{id}", entreprise.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Entreprise> entreprises = entrepriseRepository.findAll();
        assertThat(entreprises).hasSize(databaseSizeBeforeDelete - 1);
    }
}
