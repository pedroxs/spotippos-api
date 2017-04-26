package com.example.spotippos.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigInteger;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@JsonTest
public class PropertyTest {

    @Autowired
    private JacksonTester<Property> json;

    @Test
    public void testSerialize() throws Exception {
        Property property = getExpectedProperty();
        assertThat(json.write(property)).isEqualToJson("/property.json");
    }

    @Test
    public void testDeserialize() throws Exception {
        String content = "{\"id\":1,\"title\":\"Imóvel código 1, com 3 quartos e 2 banheiros.\",\"price\":643000,\"description\":\"Laboris quis quis elit commodo eiusmod qui exercitation. In laborum fugiat quis minim occaecat id.\",\"x\":1257,\"y\":928,\"beds\":3,\"baths\":2,\"provinces\":[\"Jaby\"],\"squareMeters\":61\n}";
        assertThat(json.parse(content)).isEqualTo(getExpectedProperty());
    }

    public static Property getExpectedProperty() {
        Property property = new Property();
        property.setId(BigInteger.valueOf(1));
        property.setTitle("Imóvel código 1, com 3 quartos e 2 banheiros.");
        property.setPrice(643000L);
        property.setDescription("Laboris quis quis elit commodo eiusmod qui exercitation. In laborum fugiat quis minim occaecat id.");
        property.setX(1257);
        property.setY(928);
        property.setBeds(3);
        property.setBaths(2);
        property.setProvinces(Collections.singletonList("Jaby"));
        property.setSquareMeters(61);
        return property;
    }
}