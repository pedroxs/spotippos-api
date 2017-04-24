package com.example.spotippos.service;

import com.example.spotippos.domain.Province;
import com.example.spotippos.repository.ProvinceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProvinceService {

    private final ProvinceRepository provinceRepository;

    public List<Province> findByBoundary(Integer x, Integer y) {
        return provinceRepository.findByBoundariesInRange(x, y);
    }
}
