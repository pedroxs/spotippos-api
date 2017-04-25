package com.example.spotippos.repository;

import com.example.spotippos.domain.Property;
import org.springframework.data.geo.Box;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.math.BigInteger;
import java.util.List;

/**
 * Spring Data MongoDB repository for the Property entity.
 */
public interface PropertyRepository extends MongoRepository<Property, BigInteger> {

    List<Property> findByLocationWithin(Box box);

}
