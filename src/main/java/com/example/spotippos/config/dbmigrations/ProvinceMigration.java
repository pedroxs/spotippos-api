package com.example.spotippos.config.dbmigrations;

import com.example.spotippos.domain.Boundaries;
import com.example.spotippos.domain.Boundary;
import com.example.spotippos.domain.Province;
import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Arrays;
import java.util.List;

@ChangeLog(order = "001")
public class ProvinceMigration {

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
}
