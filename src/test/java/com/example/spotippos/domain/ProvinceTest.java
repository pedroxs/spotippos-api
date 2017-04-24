package com.example.spotippos.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@JsonTest
public class ProvinceTest {

    @Autowired
    private JacksonTester<Province> json;

    @Test
    public void testSerialize() throws Exception {
        Province province = getExpectedProvince();
        assertThat(json.write(province)).isEqualToJson("/province.json");
    }

    @Test
    public void testDeserialize() throws Exception {
        String content = "{\"name\":\"Gode\",\"boundaries\":{\"upperLeft\":{\"x\":0,\"y\":1000},\"bottomRight\":{\"x\":600,\"y\":500}}}";
        assertThat(this.json.parse(content)).isEqualTo(getExpectedProvince());
    }

    private Province getExpectedProvince() {
        Boundaries boundaries = Boundaries.builder()
                .upperLeft(Boundary.builder().x(0).y(1000).build())
                .bottomRight(Boundary.builder().x(600).y(500).build())
                .build();
        Province province = new Province("Gode", boundaries);
        return province;
    }
}