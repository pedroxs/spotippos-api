package com.example.spotippos.service;

import com.example.spotippos.domain.Property;
import com.example.spotippos.domain.PropertyTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigInteger;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PropertyServiceTest {

    @Autowired
    private PropertyService propertyService;

    @Test
    public void should_populate_location_and_provinces_on_save() throws Exception {
        Property expectedProperty = PropertyTest.getExpectedProperty();
        expectedProperty.setProvinces(null);
        propertyService.save(expectedProperty);

        Optional<Property> property = propertyService.findOne(BigInteger.ONE);
        assertThat(property.isPresent(), is(true));
        assertThat(property.get().getId(), is(BigInteger.ONE));
        assertThat(property.get().getLocation(), is(notNullValue()));
        assertThat(property.get().getProvinces(), is(not(empty())));
    }
}