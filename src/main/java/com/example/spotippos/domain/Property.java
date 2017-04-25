package com.example.spotippos.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.List;

//import com.mongodb.client.model.geojson.Point;
//import com.mongodb.client.model.geojson.Position;

/**
 * The type Property.
 */
@Data
@Document
@ApiModel
public class Property {
    @Id
    @ApiModelProperty(readOnly = true)
    private BigInteger id;
    @NotBlank
    private String title;
    @NotNull
    private Long price;
    @NotBlank
    private String description;
    @Max(1400) @Min(0)
    private Integer x;
    @Max(1000) @Min(0)
    private Integer y;
    @JsonIgnore
    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2D, min = 0, max = 1400)
    private Point location;
    @Max(5) @Min(1)
    private Integer beds;
    @Max(4) @Min(1)
    private Integer baths;
    @ApiModelProperty(readOnly = true)
    private List<String> provinces;
    @Max(240) @Min(20)
    private Integer squareMeters;

    @JsonIgnore
    public void fillLocation() {
        this.location = new Point(x, y);
    }
}
