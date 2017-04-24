package com.example.spotippos.web.rest.vm;

import com.example.spotippos.domain.Property;
import lombok.Data;

import java.util.List;

/**
 * Created by pedroxs on 23/04/17.
 */
@Data
public class SearchResult {
    private Long foundProperties;
    private List<Property> properties;
}
