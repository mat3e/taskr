package io.github.mat3e.jhipster.taskr.service.impl;

import io.github.mat3e.jhipster.taskr.service.TaskService;
import io.github.mat3e.jhipster.taskr.domain.Task;
import io.github.mat3e.jhipster.taskr.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing Task.
 */
@Service
public class TaskServiceImpl implements TaskService{

    private final Logger log = LoggerFactory.getLogger(TaskServiceImpl.class);

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    /**
     * Save a task.
     *
     * @param task the entity to save
     * @return the persisted entity
     */
    @Override
    public Task save(Task task) {
        log.debug("Request to save Task : {}", task);
        return taskRepository.save(task);
    }

    /**
     *  Get all the tasks.
     *
     *  @return the list of entities
     */
    @Override
    public List<Task> findAll() {
        log.debug("Request to get all Tasks");
        return taskRepository.findAll();
    }

    /**
     *  Get one task by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    public Task findOne(String id) {
        log.debug("Request to get Task : {}", id);
        return taskRepository.findOne(id);
    }

    /**
     *  Delete the  task by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Task : {}", id);
        taskRepository.delete(id);
    }
}
