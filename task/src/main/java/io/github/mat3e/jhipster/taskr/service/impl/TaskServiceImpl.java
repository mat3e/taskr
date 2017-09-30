package io.github.mat3e.jhipster.taskr.service.impl;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import io.github.mat3e.jhipster.taskr.domain.Task;
import io.github.mat3e.jhipster.taskr.domain.enumeration.TaskStatus;
import io.github.mat3e.jhipster.taskr.queue.AuthorityMessage;
import io.github.mat3e.jhipster.taskr.queue.CheckValidityMessage;
import io.github.mat3e.jhipster.taskr.repository.TaskRepository;
import io.github.mat3e.jhipster.taskr.security.SecurityUtils;
import io.github.mat3e.jhipster.taskr.service.TaskService;
import io.github.mat3e.jhipster.taskr.web.rest.errors.CustomParameterizedException;

/**
 * Service Implementation for managing Task.
 */
@Service
public class TaskServiceImpl implements TaskService {

    private final Logger log = LoggerFactory.getLogger(TaskServiceImpl.class);

    private MessageChannel producerChannel;
    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository, Source source) {
        this.taskRepository = taskRepository;
        this.producerChannel = source.output();
    }

    // TODO: new service - notification and passing messages to it through Processor?
    @StreamListener(value = Sink.INPUT)
    public void consume(AuthorityMessage authorityMessage) {
        String taskId = authorityMessage.getContext();

        log.debug("Received message for task: {}.", taskId);
        Task task = taskRepository.findOne(taskId);

        if (authorityMessage.isAuthorityOk()) {
            taskRepository.save(task.status(TaskStatus.SENT));
        } else {
            log.error("Task {} by user {} was about being assigned to the user with the higher authority level: {}",
                taskId, authorityMessage.getRequester(), authorityMessage.getResponder());
            taskRepository.save(task.status(TaskStatus.ERROR));
        }
    }

    /**
     * Save a task.
     *
     * @param task
     *         the entity to save
     * @return the persisted entity
     */
    @Override
    public Task save(Task task) {
        log.debug("Request to save Task : {}", task);

        Task result = task;

        if (task.getId() != null) {
            // update
            if (taskRepository.findOne(task.getId()).getStatus() == TaskStatus.DRAFT) {
                // everything (but dates and status) can be edited by the author
                assertUserTheSenderOf(task);

                task.status(TaskStatus.DRAFT).readDate(null).dispatchDate(null);

                result = taskRepository.save(task);
            }
        } else {
            // create
            task.status(TaskStatus.DRAFT).readDate(null).dispatchDate(null);

            // to change an original reference as a normal save do
            result = taskRepository.save(task);
        }

        return result;
    }

    /**
     * Get all the tasks.
     *
     * @return the list of entities
     */
    @Override
    public List<Task> findAll(boolean sentByCurrUser) {
        log.debug("Request to get all Tasks");
        if (sentByCurrUser) {
            return findAllByCurrentUser();
        } else {
            return findAllForCurrentUser();
        }
    }

    /**
     * Get one task by id.
     *
     * @param id
     *         the id of the entity
     * @return the entity
     */
    @Override
    public Task findOne(String id) {
        log.debug("Request to get Task : {}", id);
        return taskRepository.findOne(id);
    }

    /**
     * Delete the  task by id.
     *
     * @param id
     *         the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Task : {}", id);
        taskRepository.delete(id);
    }

    @Override
    public Task updateStatus(String id) {
        Task task = taskRepository.findOne(id);

        switch (task.getStatus()) {
        case DRAFT:
            task = dispatch(task);
            break;
        case SENT:
            task = markAsRead(task);
            break;
        case READ:
            task = start(task);
            break;
        case IN_PROGRESS:
            task = end(task);
            break;
        }

        return task;
    }

    /**
     * Send the task to the target user. It changes Task status to PENDING.
     * If OK - status will change to SENT. Otherwise - ERROR.
     *
     * @param task
     *         the entity to be updated
     */
    private Task dispatch(Task task) {
        assertUserTheSenderOf(task);

        log.debug("Task {} triggered for dispatch", task.getId());
        task.setStatus(TaskStatus.PENDING);
        task.setDispatchDate(ZonedDateTime.now());

        // check if sender has a proper authority
        CheckValidityMessage msg = new CheckValidityMessage();

        msg.setContext(task.getId());
        msg.setRequester(task.getCreatedBy());
        msg.setResponder(task.getUserLogin());

        producerChannel.send(MessageBuilder.withPayload(msg).build());

        return taskRepository.save(task);
    }

    /**
     * Change status to READ. It should be triggered e.g. after getting the Task details.
     *
     * @param task
     *         the entity to be updated
     */
    private Task markAsRead(Task task) {
        assertUserTheRecipientOf(task);

        log.debug("Task {} was read", task.getId());
        // TODO: message to the queue?
        task.setStatus(TaskStatus.READ);
        task.setReadDate(ZonedDateTime.now());

        return taskRepository.save(task);
    }

    /**
     * Start the progress of the task. Changes the status to IN_PROGRESS.
     *
     * @param task
     *         the entity to be updated
     */
    private Task start(Task task) {
        assertUserTheRecipientOf(task);

        log.debug("Progress in task {} was started", task.getId());
        task.setStatus(TaskStatus.IN_PROGRESS);

        return taskRepository.save(task);
    }

    /**
     * The last step for the task. Changes the status to DONE.
     *
     * @param task
     *         the entity to be updated
     */
    private Task end(Task task) {
        assertUserTheRecipientOf(task);

        log.debug("Task {} got done", task.getId());
        task.setStatus(TaskStatus.DONE);

        return taskRepository.save(task);
    }

    /**
     * Get all the tasks addressed to the current user.
     *
     * @return the list of entities
     */
    private List<Task> findAllForCurrentUser() {
        return taskRepository.findByUserLoginAndStatusNotIn(SecurityUtils.getCurrentUserLogin(),
            Stream.of(TaskStatus.DRAFT, TaskStatus.PENDING, TaskStatus.ERROR).toArray(TaskStatus[]::new));
    }

    /**
     * Get all the tasks created by the current user.
     *
     * @return the list of entities
     */
    private List<Task> findAllByCurrentUser() {
        return taskRepository.findByCreatedBy(SecurityUtils.getCurrentUserLogin());
    }

    /**
     * Exception when user didn't create a given task.
     *
     * @param task
     *         entity to be checked
     */
    private void assertUserTheSenderOf(Task task) {
        assertUserMatches(task.getId(), task::getCreatedBy, "User is not the sender of the task");
    }

    /**
     * Exception when user isn't the recipient of a given task.
     *
     * @param task
     *         entity to be checked
     */
    private void assertUserTheRecipientOf(Task task) {
        assertUserMatches(task.getId(), task::getUserLogin, "User is not the recipient of the task");
    }

    /**
     * Common assertion logic.
     *
     * @param id
     *         id of task to be checked
     * @param getter
     *         condition
     * @param message
     *         message for the exception (when assertion fails)
     */
    private void assertUserMatches(String id, Supplier<String> getter, String message) {
        String currLogin = SecurityUtils.getCurrentUserLogin();

        if (!currLogin.equals(getter.get())) {

            Map<String, Object> params = new HashMap<>();
            params.put("task", id);
            params.put("user", currLogin);

            throw new CustomParameterizedException(message, params);
        }
    }
}
