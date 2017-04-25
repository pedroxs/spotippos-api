package com.example.spotippos.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.ObjectContent;
import org.springframework.data.geo.Point;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@JsonTest
public class PropertyTest {

    @Autowired
    private JacksonTester<Property> json;

    @Test
    public void testSerialize() throws Exception {
        Property property = getExpectedProperty();
        System.out.println(json.write(property));
    }

    @Test
    public void testDeserialize() throws Exception {
        String content = "{\"x\":0,\"y\":1000}";
        ObjectContent<Property> property = json.parse(content);
        System.out.println(property);
    }

    private Property getExpectedProperty() {
        Property property = new Property();
        property.setLocation(new Point(1, 3));
        return property;
    }
}