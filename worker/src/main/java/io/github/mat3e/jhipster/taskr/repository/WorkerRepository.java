package io.github.mat3e.jhipster.taskr.repository;

import java.util.List;

import io.github.mat3e.jhipster.taskr.domain.Worker;

import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Worker entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WorkerRepository extends MongoRepository<Worker, String> {
    Worker findByUserLogin(String login);

    List<Worker> findByAuthorityLvlLessThanEqual(int lvl);
}
