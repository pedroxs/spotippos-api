package com.example.spotippos.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.List;

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
    @Max(5) @Min(1)
    private Integer beds;
    @Max(4) @Min(1)
    private Integer baths;
    @ApiModelProperty(readOnly = true)
    private List<String> provinces;
    @Max(240) @Min(20)
    private Integer squareMeters;
}
