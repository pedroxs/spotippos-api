package com.example.spotippos.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * The type Province.
 */
@Data
@Builder
@Document
//@NoArgsConstructor
public class Province {

    @Id
    private String name;
    private Boundaries boundaries;
}
