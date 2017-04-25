package com.example.spotippos.repository.listener;

import com.example.spotippos.domain.Property;
import com.example.spotippos.domain.Province;
import com.example.spotippos.service.ProvinceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BeforePropertySaveListener extends AbstractMongoEventListener<Property> {

    private final ProvinceService provinceService;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Property> event) {
        super.onBeforeConvert(event);
        event.getSource().fillLocation();
        fillInProvinces(event.getSource());
    }

    private void fillInProvinces(Property property) {
        List<String> provinces = provinceService
                .findByBoundary(property.getX(), property.getY())
                .stream().map(Province::getName).collect(Collectors.toList());
        property.setProvinces(provinces);
    }
}
