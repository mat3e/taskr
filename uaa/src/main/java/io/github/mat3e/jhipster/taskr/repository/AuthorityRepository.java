package io.github.mat3e.jhipster.taskr.repository;

import io.github.mat3e.jhipster.taskr.domain.Authority;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Authority entity.
 */
public interface AuthorityRepository extends MongoRepository<Authority, String> {
}
