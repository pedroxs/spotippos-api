package com.example.spotippos.config;

import com.github.mongobee.Mongobee;
import com.mongodb.MongoClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Slf4j
@Configuration
@Import(value = MongoAutoConfiguration.class)
@EnableMongoRepositories("com.example.spotippos.repository")
public class DatabaseConfig {

    @Bean
    public Mongobee mongobee(MongoClient mongo, MongoProperties mongoProperties, Environment environment) {
        log.debug("Configuring Mongobee");
        Mongobee mongobee = new Mongobee(mongo);
        mongobee.setDbName(mongoProperties.getDatabase());
        mongobee.setSpringEnvironment(environment);
        // package to scan for migrations
        mongobee.setChangeLogsScanPackage("com.example.spotippos.config.dbmigrations");
        mongobee.setEnabled(true);
        return mongobee;
    }
}
