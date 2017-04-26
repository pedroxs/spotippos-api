package com.example.spotippos.web.rest;

import com.example.spotippos.domain.Property;
import com.example.spotippos.service.PropertyService;
import com.example.spotippos.web.rest.vm.SearchResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigInteger;
import java.net.URI;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class PropertyResource {

    private final PropertyService propertyService;

    @PostMapping(path = "properties",
            consumes = APPLICATION_JSON_UTF8_VALUE,
            produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Property> createProperty(@Valid @RequestBody Property property) {
        Property result = propertyService.save(property);
        return ResponseEntity.created(URI.create("/api/properties/" + result.getId().toString())).body(result);
    }

    @GetMapping(path = "properties/{id}", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Property> getProperty(@PathVariable BigInteger id) {
        return propertyService.findOne(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(path = "properties", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<SearchResult> searchProperty(@RequestParam("ax") Integer ax,
                                                       @RequestParam("ay") Integer ay,
                                                       @RequestParam("bx") Integer bx,
                                                       @RequestParam("by") Integer by) {
        return ResponseEntity.ok(propertyService.search(ax, ay, bx, by));
    }
}
