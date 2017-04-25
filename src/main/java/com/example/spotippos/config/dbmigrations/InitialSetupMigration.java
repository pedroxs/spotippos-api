package com.example.spotippos.config.dbmigrations;

import com.example.spotippos.domain.Boundaries;
import com.example.spotippos.domain.Boundary;
import com.example.spotippos.domain.Property;
import com.example.spotippos.domain.Province;
import com.example.spotippos.web.rest.vm.SearchResult;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ChangeLog(order = "001")
public class InitialSetupMigration {

    @ChangeSet(order = "01", author = "initiator", id = "01-addProvinces")
    public void addProvinces(MongoTemplate mongoTemplate) {
        Province.ProvinceBuilder provinceBuilder = Province.builder();
        Boundaries.BoundariesBuilder boundariesBuilder = Boundaries.builder();
        Boundary.BoundaryBuilder boundaryBuilder = Boundary.builder();

        List<Province> provinces = Arrays.asList(
                provinceBuilder
                        .name("Gode")
                        .boundaries(boundariesBuilder
                                .upperLeft(boundaryBuilder.x(0).y(1000).build())
                                .bottomRight(boundaryBuilder.x(600).y(500).build())
                                .build())
                        .build(),
                provinceBuilder
                        .name("Ruja")
                        .boundaries(boundariesBuilder
                                .upperLeft(boundaryBuilder.x(400).y(1000).build())
                                .bottomRight(boundaryBuilder.x(1100).y(500).build())
                                .build())
                        .build(),
                provinceBuilder
                        .name("Jaby")
                        .boundaries(boundariesBuilder
                                .upperLeft(boundaryBuilder.x(1100).y(1000).build())
                                .bottomRight(boundaryBuilder.x(1400).y(500).build())
                                .build())
                        .build(),
                provinceBuilder
                        .name("Scavy")
                        .boundaries(boundariesBuilder
                                .upperLeft(boundaryBuilder.x(0).y(500).build())
                                .bottomRight(boundaryBuilder.x(600).y(0).build())
                                .build())
                        .build(),
                provinceBuilder
                        .name("Groola")
                        .boundaries(boundariesBuilder
                                .upperLeft(boundaryBuilder.x(600).y(500).build())
                                .bottomRight(boundaryBuilder.x(800).y(0).build())
                                .build())
                        .build(),
                provinceBuilder
                        .name("Nova")
                        .boundaries(boundariesBuilder
                                .upperLeft(boundaryBuilder.x(800).y(500).build())
                                .bottomRight(boundaryBuilder.x(1400).y(0).build())
                                .build())
                        .build()
        );

        mongoTemplate.insertAll(provinces);
    }

    @Profile("dev")
    @ChangeSet(order = "02", author = "initiator", id = "02-addProperties")
    public void addProperties(MongoTemplate mongoTemplate) {
        SearchResult result = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            result = objectMapper.readValue(getClass().getClassLoader().getResourceAsStream("properties.json"), SearchResult.class);
        } catch (IOException e) {
            log.error("Error reading properties.json", e);
        }

        if (result != null) {
            mongoTemplate.insertAll(prepareProvinces(result.getProperties(), mongoTemplate));
        }
    }

    private List<Property> prepareProvinces(List<Property> properties, MongoTemplate mongoTemplate) {
        List<Province> provinces = mongoTemplate.findAll(Province.class);
        properties.forEach(property -> {
            property.fillLocation();
            property.setProvinces(findBoundaryProvinces(property, provinces));
        });
        return properties;
    }

    private List<String> findBoundaryProvinces(Property property, List<Province> provinces) {
        return provinces
                .stream()
                .filter(province -> {
                    Boundary upperLeft = province.getBoundaries().getUpperLeft();
                    Boundary bottomRight = province.getBoundaries().getBottomRight();
                    return upperLeft.getX().compareTo(property.getX()) <= 0
                            && upperLeft.getY().compareTo(property.getY()) >= 0
                            && bottomRight.getX().compareTo(property.getX()) >= 0
                            && bottomRight.getY().compareTo(property.getY()) <= 0;
                })
                .map(Province::getName).collect(Collectors.toList());
    }
}
