package io.github.mat3e.jhipster.taskr.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

import io.github.jhipster.web.util.ResponseUtil;
import io.github.mat3e.jhipster.taskr.domain.JobGroup;
import io.github.mat3e.jhipster.taskr.repository.JobGroupRepository;
import io.github.mat3e.jhipster.taskr.security.AuthoritiesConstants;
import io.github.mat3e.jhipster.taskr.web.rest.util.HeaderUtil;

/**
 * REST controller for managing JobGroup.
 */
@RestController
@RequestMapping("/api")
@Secured(AuthoritiesConstants.ADMIN)
public class JobGroupResource {

    private final Logger log = LoggerFactory.getLogger(JobGroupResource.class);

    private static final String ENTITY_NAME = "jobGroup";

    private final JobGroupRepository jobGroupRepository;

    public JobGroupResource(JobGroupRepository jobGroupRepository) {
        this.jobGroupRepository = jobGroupRepository;
    }

    /**
     * POST  /job-groups : Create a new jobGroup.
     *
     * @param jobGroup the jobGroup to create
     * @return the ResponseEntity with status 201 (Created) and with body the new jobGroup, or with status 400 (Bad Request) if the jobGroup has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/job-groups")
    @Timed
    public ResponseEntity<JobGroup> createJobGroup(@Valid @RequestBody JobGroup jobGroup) throws URISyntaxException {
        log.debug("REST request to save JobGroup : {}", jobGroup);
        if (jobGroup.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new jobGroup cannot already have an ID")).body(null);
        }
        JobGroup result = jobGroupRepository.save(jobGroup);
        return ResponseEntity.created(new URI("/api/job-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /job-groups : Updates an existing jobGroup.
     *
     * @param jobGroup the jobGroup to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated jobGroup,
     * or with status 400 (Bad Request) if the jobGroup is not valid,
     * or with status 500 (Internal Server Error) if the jobGroup couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/job-groups")
    @Timed
    public ResponseEntity<JobGroup> updateJobGroup(@Valid @RequestBody JobGroup jobGroup) throws URISyntaxException {
        log.debug("REST request to update JobGroup : {}", jobGroup);
        if (jobGroup.getId() == null) {
            return createJobGroup(jobGroup);
        }
        JobGroup result = jobGroupRepository.save(jobGroup);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, jobGroup.getId().toString()))
            .body(result);
    }

    /**
     * GET  /job-groups : get all the jobGroups.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of jobGroups in body
     */
    @GetMapping("/job-groups")
    @Timed
    public List<JobGroup> getAllJobGroups() {
        log.debug("REST request to get all JobGroups");
        return jobGroupRepository.findAll();
        }

    /**
     * GET  /job-groups/:id : get the "id" jobGroup.
     *
     * @param id the id of the jobGroup to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the jobGroup, or with status 404 (Not Found)
     */
    @GetMapping("/job-groups/{id}")
    @Timed
    public ResponseEntity<JobGroup> getJobGroup(@PathVariable String id) {
        log.debug("REST request to get JobGroup : {}", id);
        JobGroup jobGroup = jobGroupRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(jobGroup));
    }

    /**
     * DELETE  /job-groups/:id : delete the "id" jobGroup.
     *
     * @param id the id of the jobGroup to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/job-groups/{id}")
    @Timed
    public ResponseEntity<Void> deleteJobGroup(@PathVariable String id) {
        log.debug("REST request to delete JobGroup : {}", id);
        jobGroupRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
