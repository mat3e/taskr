package io.github.mat3e.jhipster.taskr.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import io.github.mat3e.jhipster.taskr.domain.Task;
import io.github.mat3e.jhipster.taskr.domain.enumeration.TaskStatus;

/**
 * Spring Data MongoDB repository for the Task entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaskRepository extends MongoRepository<Task, String> {
    /**
     * Returns tasks assigned to the given user.
     *
     * @param userLogin
     *         login of the user
     * @return list of tasks where user is assigned
     */
    List<Task> findByUserLoginAndStatusNotIn(String userLogin, TaskStatus[] wrongStatuses);

    /**
     * Returns tasks created by the given user.
     *
     * @param userLogin
     *         id of the user
     * @return list of tasks by user
     */
    List<Task> findByCreatedBy(String userLogin);
}
