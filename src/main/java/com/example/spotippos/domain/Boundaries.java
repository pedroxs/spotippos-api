package com.example.spotippos.domain;

import lombok.Builder;
import lombok.Data;

/**
 * The type Boundaries.
 */
@Data
@Builder
public class Boundaries {
    private Boundary upperLeft;
    private Boundary bottomRight;
}
