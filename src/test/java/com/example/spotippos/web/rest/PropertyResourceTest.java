package com.example.spotippos.web.rest;

import com.example.spotippos.domain.Property;
import com.example.spotippos.service.PropertyService;
import com.example.spotippos.web.rest.vm.SearchResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigInteger;
import java.util.Collections;
import java.util.Optional;

import static com.example.spotippos.domain.PropertyTest.getExpectedProperty;
import static org.hamcrest.Matchers.startsWith;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyInt;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(PropertyResource.class)
public class PropertyResourceTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private PropertyService propertyService;

    @Test
    public void should_create_property() throws Exception {
        Property property = getExpectedProperty();
        property.setProvinces(null);
        given(propertyService.save(property)).willAnswer(invocation -> {
            Property arg = invocation.getArgumentAt(0, Property.class);
            arg.fillLocation();
            arg.setProvinces(Collections.singletonList("Jaby"));
            return arg;
        });

        mvc.perform(post("/api/properties")
                .accept(APPLICATION_JSON_UTF8)
                .contentType(APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(property)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.title").value(startsWith("Imóvel")))
                .andExpect(jsonPath("$.price").value("643000"))
                .andExpect(jsonPath("$.description").value(startsWith("Laboris")))
                .andExpect(jsonPath("$.x").value("1257"))
                .andExpect(jsonPath("$.y").value("928"))
                .andExpect(jsonPath("$.beds").value("3"))
                .andExpect(jsonPath("$.baths").value("2"))
                .andExpect(jsonPath("$.squareMeters").value("61"))
                .andExpect(jsonPath("$.provinces").isArray())
                .andExpect(jsonPath("$.provinces[0]").value("Jaby"));
    }

    @Test
    public void should_validate_property_on_create() throws Exception {
        mvc.perform(post("/api/properties")
                .accept(APPLICATION_JSON_UTF8)
                .contentType(APPLICATION_JSON_UTF8)
                .content("{\"id\":1}"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void should_retrieve_property_by_id() throws Exception {
        given(propertyService.findOne(BigInteger.ONE)).willReturn(Optional.of(getExpectedProperty()));

        mvc.perform(get("/api/properties/{id}", 1)
                .accept(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.title").value(startsWith("Imóvel")))
                .andExpect(jsonPath("$.price").value("643000"))
                .andExpect(jsonPath("$.description").value(startsWith("Laboris")))
                .andExpect(jsonPath("$.x").value("1257"))
                .andExpect(jsonPath("$.y").value("928"))
                .andExpect(jsonPath("$.beds").value("3"))
                .andExpect(jsonPath("$.baths").value("2"))
                .andExpect(jsonPath("$.squareMeters").value("61"))
                .andExpect(jsonPath("$.provinces").isArray())
                .andExpect(jsonPath("$.provinces[0]").value("Jaby"));
    }

    @Test
    public void should_return_not_found_for_non_existing_property() throws Exception {
        given(propertyService.findOne(Matchers.any())).willReturn(Optional.empty());

        mvc.perform(get("/api/properties/{id}", 1))
                .andExpect(status().isNotFound());
    }

    @Test
    public void should_search_properties() throws Exception {
        given(propertyService.search(anyInt(), anyInt(), anyInt(), anyInt())).willReturn(new SearchResult(Collections.singletonList(getExpectedProperty())));

        mvc.perform(get("/api/properties?ax={ax}&ay={ay}&bx={bx}&by={by}", 1, 100, 2, 200)
                .accept(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.foundProperties").value("1"))
                .andExpect(jsonPath("$.properties").isArray())
                .andExpect(jsonPath("$.properties[0].id").value("1"))
                .andExpect(jsonPath("$.properties[0].title").value(startsWith("Imóvel")))
                .andExpect(jsonPath("$.properties[0].price").value("643000"))
                .andExpect(jsonPath("$.properties[0].description").value(startsWith("Laboris")))
                .andExpect(jsonPath("$.properties[0].x").value("1257"))
                .andExpect(jsonPath("$.properties[0].y").value("928"))
                .andExpect(jsonPath("$.properties[0].beds").value("3"))
                .andExpect(jsonPath("$.properties[0].baths").value("2"))
                .andExpect(jsonPath("$.properties[0].squareMeters").value("61"))
                .andExpect(jsonPath("$.properties[0].provinces").isArray())
                .andExpect(jsonPath("$.properties[0].provinces[0]").value("Jaby"));
    }

}