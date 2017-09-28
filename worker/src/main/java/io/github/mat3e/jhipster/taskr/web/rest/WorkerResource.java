package io.github.mat3e.jhipster.taskr.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.mat3e.jhipster.taskr.domain.Worker;

import io.github.mat3e.jhipster.taskr.repository.WorkerRepository;
import io.github.mat3e.jhipster.taskr.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Worker.
 */
@RestController
@RequestMapping("/api")
public class WorkerResource {

    private final Logger log = LoggerFactory.getLogger(WorkerResource.class);

    private static final String ENTITY_NAME = "worker";

    private final WorkerRepository workerRepository;

    public WorkerResource(WorkerRepository workerRepository) {
        this.workerRepository = workerRepository;
    }

    /**
     * POST  /workers : Create a new worker.
     *
     * @param worker the worker to create
     * @return the ResponseEntity with status 201 (Created) and with body the new worker, or with status 400 (Bad Request) if the worker has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/workers")
    @Timed
    public ResponseEntity<Worker> createWorker(@Valid @RequestBody Worker worker) throws URISyntaxException {
        log.debug("REST request to save Worker : {}", worker);
        if (worker.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new worker cannot already have an ID")).body(null);
        }
        Worker result = workerRepository.save(worker);
        return ResponseEntity.created(new URI("/api/workers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /workers : Updates an existing worker.
     *
     * @param worker the worker to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated worker,
     * or with status 400 (Bad Request) if the worker is not valid,
     * or with status 500 (Internal Server Error) if the worker couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/workers")
    @Timed
    public ResponseEntity<Worker> updateWorker(@Valid @RequestBody Worker worker) throws URISyntaxException {
        log.debug("REST request to update Worker : {}", worker);
        if (worker.getId() == null) {
            return createWorker(worker);
        }
        Worker result = workerRepository.save(worker);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, worker.getId().toString()))
            .body(result);
    }

    /**
     * GET  /workers : get all the workers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of workers in body
     */
    @GetMapping("/workers")
    @Timed
    public List<Worker> getAllWorkers() {
        log.debug("REST request to get all Workers");
        return workerRepository.findAll();
        }

    /**
     * GET  /workers/:id : get the "id" worker.
     *
     * @param id the id of the worker to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the worker, or with status 404 (Not Found)
     */
    @GetMapping("/workers/{id}")
    @Timed
    public ResponseEntity<Worker> getWorker(@PathVariable String id) {
        log.debug("REST request to get Worker : {}", id);
        Worker worker = workerRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(worker));
    }

    /**
     * DELETE  /workers/:id : delete the "id" worker.
     *
     * @param id the id of the worker to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/workers/{id}")
    @Timed
    public ResponseEntity<Void> deleteWorker(@PathVariable String id) {
        log.debug("REST request to delete Worker : {}", id);
        workerRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
