package io.github.mat3e.jhipster.taskr.repository;

import io.github.mat3e.jhipster.taskr.domain.Job;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Job entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JobRepository extends MongoRepository<Job, String> {

}
