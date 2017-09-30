package io.github.mat3e.jhipster.taskr.web.rest;

import io.github.mat3e.jhipster.taskr.WorkerApp;

import io.github.mat3e.jhipster.taskr.config.SecurityBeanOverrideConfiguration;

import io.github.mat3e.jhipster.taskr.domain.Worker;
import io.github.mat3e.jhipster.taskr.repository.WorkerRepository;
import io.github.mat3e.jhipster.taskr.web.rest.errors.ExceptionTranslator;

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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the WorkerResource REST controller.
 *
 * @see WorkerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {WorkerApp.class, SecurityBeanOverrideConfiguration.class})
public class WorkerResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SURNAME = "AAAAAAAAAA";
    private static final String UPDATED_SURNAME = "BBBBBBBBBB";

    private static final String DEFAULT_USER_LOGIN = "AAAAAAAAAA";
    private static final String UPDATED_USER_LOGIN = "BBBBBBBBBB";

    private static final String DEFAULT_USER_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_USER_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_JOB_ID = "AAAAAAAAAA";
    private static final String UPDATED_JOB_ID = "BBBBBBBBBB";

    private static final String DEFAULT_JOB_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_JOB_TITLE = "BBBBBBBBBB";

    private static final Integer DEFAULT_AUTHORITY_LVL = 1;
    private static final Integer UPDATED_AUTHORITY_LVL = 2;

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_STREET = "AAAAAAAAAA";
    private static final String UPDATED_STREET = "BBBBBBBBBB";

    private static final String DEFAULT_HOUSE = "AAAAAAAAAA";
    private static final String UPDATED_HOUSE = "BBBBBBBBBB";

    private static final String DEFAULT_APARTMENT = "AAAAAAAAAA";
    private static final String UPDATED_APARTMENT = "BBBBBBBBBB";

    private static final String DEFAULT_POSTAL_CODE = "AAAAAA";
    private static final String UPDATED_POSTAL_CODE = "BBBBBB";

    private static final String DEFAULT_POST = "AAAAAAAAAA";
    private static final String UPDATED_POST = "BBBBBBBBBB";

    @Autowired
    private WorkerRepository workerRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restWorkerMockMvc;

    private Worker worker;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WorkerResource workerResource = new WorkerResource(workerRepository);
        this.restWorkerMockMvc = MockMvcBuilders.standaloneSetup(workerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Worker createEntity() {
        Worker worker = new Worker()
            .name(DEFAULT_NAME)
            .surname(DEFAULT_SURNAME)
            .userLogin(DEFAULT_USER_LOGIN)
            .userEmail(DEFAULT_USER_EMAIL)
            .jobId(DEFAULT_JOB_ID)
            .jobTitle(DEFAULT_JOB_TITLE)
            .authorityLvl(DEFAULT_AUTHORITY_LVL)
            .city(DEFAULT_CITY)
            .street(DEFAULT_STREET)
            .house(DEFAULT_HOUSE)
            .apartment(DEFAULT_APARTMENT)
            .postalCode(DEFAULT_POSTAL_CODE)
            .post(DEFAULT_POST);
        return worker;
    }

    @Before
    public void initTest() {
        workerRepository.deleteAll();
        worker = createEntity();
    }

    @Test
    public void createWorker() throws Exception {
        int databaseSizeBeforeCreate = workerRepository.findAll().size();

        // Create the Worker
        restWorkerMockMvc.perform(post("/api/workers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(worker)))
            .andExpect(status().isCreated());

        // Validate the Worker in the database
        List<Worker> workerList = workerRepository.findAll();
        assertThat(workerList).hasSize(databaseSizeBeforeCreate + 1);
        Worker testWorker = workerList.get(workerList.size() - 1);
        assertThat(testWorker.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testWorker.getSurname()).isEqualTo(DEFAULT_SURNAME);
        assertThat(testWorker.getUserLogin()).isEqualTo(DEFAULT_USER_LOGIN);
        assertThat(testWorker.getUserEmail()).isEqualTo(DEFAULT_USER_EMAIL);
        assertThat(testWorker.getJobId()).isEqualTo(DEFAULT_JOB_ID);
        assertThat(testWorker.getJobTitle()).isEqualTo(DEFAULT_JOB_TITLE);
        assertThat(testWorker.getAuthorityLvl()).isEqualTo(DEFAULT_AUTHORITY_LVL);
        assertThat(testWorker.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testWorker.getStreet()).isEqualTo(DEFAULT_STREET);
        assertThat(testWorker.getHouse()).isEqualTo(DEFAULT_HOUSE);
        assertThat(testWorker.getApartment()).isEqualTo(DEFAULT_APARTMENT);
        assertThat(testWorker.getPostalCode()).isEqualTo(DEFAULT_POSTAL_CODE);
        assertThat(testWorker.getPost()).isEqualTo(DEFAULT_POST);
    }

    @Test
    public void createWorkerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = workerRepository.findAll().size();

        // Create the Worker with an existing ID
        worker.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkerMockMvc.perform(post("/api/workers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(worker)))
            .andExpect(status().isBadRequest());

        // Validate the Worker in the database
        List<Worker> workerList = workerRepository.findAll();
        assertThat(workerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = workerRepository.findAll().size();
        // set the field null
        worker.setName(null);

        // Create the Worker, which fails.

        restWorkerMockMvc.perform(post("/api/workers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(worker)))
            .andExpect(status().isBadRequest());

        List<Worker> workerList = workerRepository.findAll();
        assertThat(workerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkSurnameIsRequired() throws Exception {
        int databaseSizeBeforeTest = workerRepository.findAll().size();
        // set the field null
        worker.setSurname(null);

        // Create the Worker, which fails.

        restWorkerMockMvc.perform(post("/api/workers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(worker)))
            .andExpect(status().isBadRequest());

        List<Worker> workerList = workerRepository.findAll();
        assertThat(workerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkUserLoginIsRequired() throws Exception {
        int databaseSizeBeforeTest = workerRepository.findAll().size();
        // set the field null
        worker.setUserLogin(null);

        // Create the Worker, which fails.

        restWorkerMockMvc.perform(post("/api/workers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(worker)))
            .andExpect(status().isBadRequest());

        List<Worker> workerList = workerRepository.findAll();
        assertThat(workerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkUserEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = workerRepository.findAll().size();
        // set the field null
        worker.setUserEmail(null);

        // Create the Worker, which fails.

        restWorkerMockMvc.perform(post("/api/workers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(worker)))
            .andExpect(status().isBadRequest());

        List<Worker> workerList = workerRepository.findAll();
        assertThat(workerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkJobIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = workerRepository.findAll().size();
        // set the field null
        worker.setJobId(null);

        // Create the Worker, which fails.

        restWorkerMockMvc.perform(post("/api/workers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(worker)))
            .andExpect(status().isBadRequest());

        List<Worker> workerList = workerRepository.findAll();
        assertThat(workerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkJobTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = workerRepository.findAll().size();
        // set the field null
        worker.setJobTitle(null);

        // Create the Worker, which fails.

        restWorkerMockMvc.perform(post("/api/workers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(worker)))
            .andExpect(status().isBadRequest());

        List<Worker> workerList = workerRepository.findAll();
        assertThat(workerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkCityIsRequired() throws Exception {
        int databaseSizeBeforeTest = workerRepository.findAll().size();
        // set the field null
        worker.setCity(null);

        // Create the Worker, which fails.

        restWorkerMockMvc.perform(post("/api/workers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(worker)))
            .andExpect(status().isBadRequest());

        List<Worker> workerList = workerRepository.findAll();
        assertThat(workerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkStreetIsRequired() throws Exception {
        int databaseSizeBeforeTest = workerRepository.findAll().size();
        // set the field null
        worker.setStreet(null);

        // Create the Worker, which fails.

        restWorkerMockMvc.perform(post("/api/workers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(worker)))
            .andExpect(status().isBadRequest());

        List<Worker> workerList = workerRepository.findAll();
        assertThat(workerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkHouseIsRequired() throws Exception {
        int databaseSizeBeforeTest = workerRepository.findAll().size();
        // set the field null
        worker.setHouse(null);

        // Create the Worker, which fails.

        restWorkerMockMvc.perform(post("/api/workers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(worker)))
            .andExpect(status().isBadRequest());

        List<Worker> workerList = workerRepository.findAll();
        assertThat(workerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkPostalCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = workerRepository.findAll().size();
        // set the field null
        worker.setPostalCode(null);

        // Create the Worker, which fails.

        restWorkerMockMvc.perform(post("/api/workers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(worker)))
            .andExpect(status().isBadRequest());

        List<Worker> workerList = workerRepository.findAll();
        assertThat(workerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkPostIsRequired() throws Exception {
        int databaseSizeBeforeTest = workerRepository.findAll().size();
        // set the field null
        worker.setPost(null);

        // Create the Worker, which fails.

        restWorkerMockMvc.perform(post("/api/workers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(worker)))
            .andExpect(status().isBadRequest());

        List<Worker> workerList = workerRepository.findAll();
        assertThat(workerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllWorkersWhenAnonymous() throws Exception {
        // Initialize the database
        workerRepository.save(worker);

        // Get all the workerList
        restWorkerMockMvc.perform(get("/api/workers?sort=id,desc")).andExpect(status().isOk())
                         .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                         .andExpect(content().json("[]"));
    }

    @Test
    public void getWorker() throws Exception {
        // Initialize the database
        workerRepository.save(worker);

        // Get the worker
        restWorkerMockMvc.perform(get("/api/workers/{id}", worker.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(worker.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.surname").value(DEFAULT_SURNAME.toString()))
            .andExpect(jsonPath("$.userLogin").value(DEFAULT_USER_LOGIN.toString()))
            .andExpect(jsonPath("$.userEmail").value(DEFAULT_USER_EMAIL.toString()))
            .andExpect(jsonPath("$.jobId").value(DEFAULT_JOB_ID.toString()))
            .andExpect(jsonPath("$.jobTitle").value(DEFAULT_JOB_TITLE.toString()))
            .andExpect(jsonPath("$.authorityLvl").value(DEFAULT_AUTHORITY_LVL))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.street").value(DEFAULT_STREET.toString()))
            .andExpect(jsonPath("$.house").value(DEFAULT_HOUSE.toString()))
            .andExpect(jsonPath("$.apartment").value(DEFAULT_APARTMENT.toString()))
            .andExpect(jsonPath("$.postalCode").value(DEFAULT_POSTAL_CODE.toString()))
            .andExpect(jsonPath("$.post").value(DEFAULT_POST.toString()));
    }

    @Test
    public void getNonExistingWorker() throws Exception {
        // Get the worker
        restWorkerMockMvc.perform(get("/api/workers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateWorker() throws Exception {
        // Initialize the database
        workerRepository.save(worker);
        int databaseSizeBeforeUpdate = workerRepository.findAll().size();

        // Update the worker
        Worker updatedWorker = workerRepository.findOne(worker.getId());
        updatedWorker
            .name(UPDATED_NAME)
            .surname(UPDATED_SURNAME)
            .userLogin(UPDATED_USER_LOGIN)
            .userEmail(UPDATED_USER_EMAIL)
            .jobId(UPDATED_JOB_ID)
            .jobTitle(UPDATED_JOB_TITLE)
            .authorityLvl(UPDATED_AUTHORITY_LVL)
            .city(UPDATED_CITY)
            .street(UPDATED_STREET)
            .house(UPDATED_HOUSE)
            .apartment(UPDATED_APARTMENT)
            .postalCode(UPDATED_POSTAL_CODE)
            .post(UPDATED_POST);

        restWorkerMockMvc.perform(put("/api/workers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedWorker)))
            .andExpect(status().isOk());

        // Validate the Worker in the database
        List<Worker> workerList = workerRepository.findAll();
        assertThat(workerList).hasSize(databaseSizeBeforeUpdate);
        Worker testWorker = workerList.get(workerList.size() - 1);
        assertThat(testWorker.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testWorker.getSurname()).isEqualTo(UPDATED_SURNAME);
        assertThat(testWorker.getUserLogin()).isEqualTo(UPDATED_USER_LOGIN);
        assertThat(testWorker.getUserEmail()).isEqualTo(UPDATED_USER_EMAIL);
        assertThat(testWorker.getJobId()).isEqualTo(UPDATED_JOB_ID);
        assertThat(testWorker.getJobTitle()).isEqualTo(UPDATED_JOB_TITLE);
        assertThat(testWorker.getAuthorityLvl()).isEqualTo(UPDATED_AUTHORITY_LVL);
        assertThat(testWorker.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testWorker.getStreet()).isEqualTo(UPDATED_STREET);
        assertThat(testWorker.getHouse()).isEqualTo(UPDATED_HOUSE);
        assertThat(testWorker.getApartment()).isEqualTo(UPDATED_APARTMENT);
        assertThat(testWorker.getPostalCode()).isEqualTo(UPDATED_POSTAL_CODE);
        assertThat(testWorker.getPost()).isEqualTo(UPDATED_POST);
    }

    @Test
    public void updateNonExistingWorker() throws Exception {
        int databaseSizeBeforeUpdate = workerRepository.findAll().size();

        // Create the Worker

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restWorkerMockMvc.perform(put("/api/workers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(worker)))
            .andExpect(status().isCreated());

        // Validate the Worker in the database
        List<Worker> workerList = workerRepository.findAll();
        assertThat(workerList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteWorker() throws Exception {
        // Initialize the database
        workerRepository.save(worker);
        int databaseSizeBeforeDelete = workerRepository.findAll().size();

        // Get the worker
        restWorkerMockMvc.perform(delete("/api/workers/{id}", worker.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Worker> workerList = workerRepository.findAll();
        assertThat(workerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Worker.class);
        Worker worker1 = new Worker();
        worker1.setId("id1");
        Worker worker2 = new Worker();
        worker2.setId(worker1.getId());
        assertThat(worker1).isEqualTo(worker2);
        worker2.setId("id2");
        assertThat(worker1).isNotEqualTo(worker2);
        worker1.setId(null);
        assertThat(worker1).isNotEqualTo(worker2);
    }
}
