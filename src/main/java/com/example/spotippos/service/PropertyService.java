package com.example.spotippos.service;

import com.example.spotippos.domain.Property;
import com.example.spotippos.domain.Province;
import com.example.spotippos.repository.PropertyRepository;
import com.example.spotippos.repository.ProvinceRepository;
import com.example.spotippos.web.rest.vm.SearchResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PropertyService {

    private final PropertyRepository propertyRepository;
    private final ProvinceService provinceService;

    public Property save(Property property) {
        return propertyRepository.save(fillInProvinces(property));
    }

    private Property fillInProvinces(Property property) {
        List<String> provinces = provinceService
                .findByBoundary(property.getX(), property.getY())
                .stream().map(Province::getName).collect(Collectors.toList());
        property.setProvinces(provinces);
        return property;
    }

    public Optional<Property> findOne(Long id) {
        return Optional.ofNullable(propertyRepository.findOne(id));
    }

    public SearchResult search(Integer ax, Integer ay, Integer bx, Integer by) {
        return null;
    }
}
