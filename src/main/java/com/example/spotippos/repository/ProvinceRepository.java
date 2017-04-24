package com.example.spotippos.repository;

import com.example.spotippos.domain.Province;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * Spring Data MongoDB repository for the Province entity.
 */
public interface ProvinceRepository extends MongoRepository<Province, String> {

    @Query("{boundaries.upperLeft.x:{$lte:?0}, boundaries.upperLeft.y:{$gte:?1}, boundaries.bottomRight.x:{$gte:?0}, boundaries.bottomRight.y:{$lte:?1}}")
    List<Province> findByBoundariesInRange(Integer x, Integer y);
}
