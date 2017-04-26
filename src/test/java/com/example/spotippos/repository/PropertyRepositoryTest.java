package com.example.spotippos.repository;

import com.example.spotippos.domain.Property;
import com.example.spotippos.domain.PropertyTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.geo.Box;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigInteger;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@DataMongoTest
@RunWith(SpringRunner.class)
public class PropertyRepositoryTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private PropertyRepository repository;

    @Test
    public void should_find_properties_within_box() throws Exception {
        Property expectedProperty = PropertyTest.getExpectedProperty();
        expectedProperty.fillLocation();
        mongoTemplate.save(expectedProperty);
        List<Property> properties = repository.findByLocationWithin(new Box(new Point(1250, 920), new Point(1260, 930)));

        assertThat(properties, is(notNullValue()));
        assertThat(properties, is(not(empty())));
        assertThat(properties.get(0).getId(), is(BigInteger.ONE));
        assertThat(properties.get(0).getLocation(), is(notNullValue()));
    }
}