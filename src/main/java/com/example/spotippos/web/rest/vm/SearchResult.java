package com.example.spotippos.web.rest.vm;

import com.example.spotippos.domain.Property;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class SearchResult {
    private Integer foundProperties;
    private List<Property> properties;

    public SearchResult(List<Property> properties) {
        this.properties = properties;
        this.foundProperties = properties.size();
    }
}
