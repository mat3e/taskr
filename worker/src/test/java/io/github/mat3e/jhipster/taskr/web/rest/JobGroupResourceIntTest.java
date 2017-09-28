package io.github.mat3e.jhipster.taskr.web.rest;

import io.github.mat3e.jhipster.taskr.WorkerApp;

import io.github.mat3e.jhipster.taskr.config.SecurityBeanOverrideConfiguration;

import io.github.mat3e.jhipster.taskr.domain.JobGroup;
import io.github.mat3e.jhipster.taskr.repository.JobGroupRepository;
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
 * Test class for the JobGroupResource REST controller.
 *
 * @see JobGroupResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {WorkerApp.class, SecurityBeanOverrideConfiguration.class})
public class JobGroupResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_AUTHORITY_LVL = 1;
    private static final Integer UPDATED_AUTHORITY_LVL = 2;

    @Autowired
    private JobGroupRepository jobGroupRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restJobGroupMockMvc;

    private JobGroup jobGroup;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final JobGroupResource jobGroupResource = new JobGroupResource(jobGroupRepository);
        this.restJobGroupMockMvc = MockMvcBuilders.standaloneSetup(jobGroupResource)
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
    public static JobGroup createEntity() {
        JobGroup jobGroup = new JobGroup()
            .name(DEFAULT_NAME)
            .authorityLvl(DEFAULT_AUTHORITY_LVL);
        return jobGroup;
    }

    @Before
    public void initTest() {
        jobGroupRepository.deleteAll();
        jobGroup = createEntity();
    }

    @Test
    public void createJobGroup() throws Exception {
        int databaseSizeBeforeCreate = jobGroupRepository.findAll().size();

        // Create the JobGroup
        restJobGroupMockMvc.perform(post("/api/job-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobGroup)))
            .andExpect(status().isCreated());

        // Validate the JobGroup in the database
        List<JobGroup> jobGroupList = jobGroupRepository.findAll();
        assertThat(jobGroupList).hasSize(databaseSizeBeforeCreate + 1);
        JobGroup testJobGroup = jobGroupList.get(jobGroupList.size() - 1);
        assertThat(testJobGroup.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testJobGroup.getAuthorityLvl()).isEqualTo(DEFAULT_AUTHORITY_LVL);
    }

    @Test
    public void createJobGroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = jobGroupRepository.findAll().size();

        // Create the JobGroup with an existing ID
        jobGroup.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restJobGroupMockMvc.perform(post("/api/job-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobGroup)))
            .andExpect(status().isBadRequest());

        // Validate the JobGroup in the database
        List<JobGroup> jobGroupList = jobGroupRepository.findAll();
        assertThat(jobGroupList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobGroupRepository.findAll().size();
        // set the field null
        jobGroup.setName(null);

        // Create the JobGroup, which fails.

        restJobGroupMockMvc.perform(post("/api/job-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobGroup)))
            .andExpect(status().isBadRequest());

        List<JobGroup> jobGroupList = jobGroupRepository.findAll();
        assertThat(jobGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkAuthorityLvlIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobGroupRepository.findAll().size();
        // set the field null
        jobGroup.setAuthorityLvl(null);

        // Create the JobGroup, which fails.

        restJobGroupMockMvc.perform(post("/api/job-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobGroup)))
            .andExpect(status().isBadRequest());

        List<JobGroup> jobGroupList = jobGroupRepository.findAll();
        assertThat(jobGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllJobGroups() throws Exception {
        // Initialize the database
        jobGroupRepository.save(jobGroup);

        // Get all the jobGroupList
        restJobGroupMockMvc.perform(get("/api/job-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobGroup.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].authorityLvl").value(hasItem(DEFAULT_AUTHORITY_LVL)));
    }

    @Test
    public void getJobGroup() throws Exception {
        // Initialize the database
        jobGroupRepository.save(jobGroup);

        // Get the jobGroup
        restJobGroupMockMvc.perform(get("/api/job-groups/{id}", jobGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(jobGroup.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.authorityLvl").value(DEFAULT_AUTHORITY_LVL));
    }

    @Test
    public void getNonExistingJobGroup() throws Exception {
        // Get the jobGroup
        restJobGroupMockMvc.perform(get("/api/job-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateJobGroup() throws Exception {
        // Initialize the database
        jobGroupRepository.save(jobGroup);
        int databaseSizeBeforeUpdate = jobGroupRepository.findAll().size();

        // Update the jobGroup
        JobGroup updatedJobGroup = jobGroupRepository.findOne(jobGroup.getId());
        updatedJobGroup
            .name(UPDATED_NAME)
            .authorityLvl(UPDATED_AUTHORITY_LVL);

        restJobGroupMockMvc.perform(put("/api/job-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedJobGroup)))
            .andExpect(status().isOk());

        // Validate the JobGroup in the database
        List<JobGroup> jobGroupList = jobGroupRepository.findAll();
        assertThat(jobGroupList).hasSize(databaseSizeBeforeUpdate);
        JobGroup testJobGroup = jobGroupList.get(jobGroupList.size() - 1);
        assertThat(testJobGroup.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testJobGroup.getAuthorityLvl()).isEqualTo(UPDATED_AUTHORITY_LVL);
    }

    @Test
    public void updateNonExistingJobGroup() throws Exception {
        int databaseSizeBeforeUpdate = jobGroupRepository.findAll().size();

        // Create the JobGroup

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restJobGroupMockMvc.perform(put("/api/job-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobGroup)))
            .andExpect(status().isCreated());

        // Validate the JobGroup in the database
        List<JobGroup> jobGroupList = jobGroupRepository.findAll();
        assertThat(jobGroupList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteJobGroup() throws Exception {
        // Initialize the database
        jobGroupRepository.save(jobGroup);
        int databaseSizeBeforeDelete = jobGroupRepository.findAll().size();

        // Get the jobGroup
        restJobGroupMockMvc.perform(delete("/api/job-groups/{id}", jobGroup.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<JobGroup> jobGroupList = jobGroupRepository.findAll();
        assertThat(jobGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(JobGroup.class);
        JobGroup jobGroup1 = new JobGroup();
        jobGroup1.setId("id1");
        JobGroup jobGroup2 = new JobGroup();
        jobGroup2.setId(jobGroup1.getId());
        assertThat(jobGroup1).isEqualTo(jobGroup2);
        jobGroup2.setId("id2");
        assertThat(jobGroup1).isNotEqualTo(jobGroup2);
        jobGroup1.setId(null);
        assertThat(jobGroup1).isNotEqualTo(jobGroup2);
    }
}
