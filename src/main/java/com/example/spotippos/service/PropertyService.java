package com.example.spotippos.service;

import com.example.spotippos.domain.Property;
import com.example.spotippos.repository.PropertyRepository;
import com.example.spotippos.web.rest.vm.SearchResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.geo.Box;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PropertyService {

    private final PropertyRepository propertyRepository;

    public Property save(Property property) {
        return propertyRepository.save(property);
    }

    public Optional<Property> findOne(BigInteger id) {
        return Optional.ofNullable(propertyRepository.findOne(id));
    }

    /**
     * Search for properties in enclosing rectangle.
     * <p/>
     *
     * Business requisition specified to request for upper-left and lower-right rectangle dots, but
     * according to Spring Data Mongo <a href="http://docs.spring.io/spring-data/data-mongo/docs/current/reference/html/#mongo.geospatial">docs</a>
     * the rectangle dots are lower-left then upper-right, so they are switched here before passing to repository.
     *
     * @param ax upper left x
     * @param ay upper left y
     * @param bx lower right x
     * @param by lower right y
     * @return the search result
     */
    public SearchResult search(Integer ax, Integer ay, Integer bx, Integer by) {
        Point lowerLeft = new Point(ax, ay);
        Point upperRight = new Point(bx, by);
        return new SearchResult(propertyRepository.findByLocationWithin(new Box(lowerLeft, upperRight)));
    }
}
