package com.example.spotippos.repository;

import com.example.spotippos.domain.Boundaries;
import com.example.spotippos.domain.Boundary;
import com.example.spotippos.domain.Province;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;


@DataMongoTest
@RunWith(SpringRunner.class)
public class ProvinceRepositoryTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ProvinceRepository repository;

    @Test
    public void should_find_province_by_boundaries() throws Exception {
        mongoTemplate.save(getExpectedProvince());
        List<Province> provinces = repository.findByBoundariesInRange(1257, 928);
        assertThat(provinces, is(not(empty())));
        assertThat(provinces, hasSize(1));
        assertThat(provinces.get(0).getName(), is("Jaby"));
    }

    private Province getExpectedProvince() {
        return Province.builder()
                .name("Jaby")
                .boundaries(Boundaries.builder()
                        .upperLeft(Boundary.builder().x(1100).y(1000).build())
                        .bottomRight(Boundary.builder().x(1400).y(500).build())
                        .build())
                .build();
    }


}