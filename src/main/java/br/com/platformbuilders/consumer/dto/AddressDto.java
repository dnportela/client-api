package br.com.platformbuilders.consumer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {

    @JsonProperty(value = "street")
    @ApiModelProperty(value = "Address street name")
    private String street;

    @JsonProperty(value = "number")
    @ApiModelProperty(value = "Address number")
    private String number;

    @JsonProperty(value = "city")
    @ApiModelProperty(value = "Address city name")
    private String city;

    @JsonProperty(value = "state")
    @ApiModelProperty(value = "Address state name")
    private String state;

    @JsonProperty(value = "postalCode")
    @ApiModelProperty(value = "Address postal code")
    private String postalCode;
}
