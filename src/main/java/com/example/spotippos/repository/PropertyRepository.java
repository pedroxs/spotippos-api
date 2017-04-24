package com.example.spotippos.repository;

import com.example.spotippos.domain.Property;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Property entity.
 */
public interface PropertyRepository extends MongoRepository<Property, Long> {
}
