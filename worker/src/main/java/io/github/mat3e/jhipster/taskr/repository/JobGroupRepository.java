package io.github.mat3e.jhipster.taskr.repository;

import io.github.mat3e.jhipster.taskr.domain.JobGroup;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the JobGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JobGroupRepository extends MongoRepository<JobGroup, String> {

}
