package br.com.platformbuilders.consumer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="Consumer", description="Model class for consumers")
public class ConsumerDto {

    @JsonProperty(value = "id")
    private Integer id;

    @JsonProperty(value = "name")
    @NotBlank(message = "Consumer name is required")
    @ApiModelProperty(value = "Consumer's name")
    private String name;

    @JsonProperty(value = "documentNumber")
    @NotBlank(message = "Consumer document number is required")
    @ApiModelProperty(value = "Consumer's document number")
    private String documentNumber;

    @JsonProperty(value = "birthDate")
    @ApiModelProperty(value = "Consumer's date of birth", example = "2000-1-1")
    private String birthDate;

    @JsonProperty(value = "age")
    @ApiModelProperty(value = "Consumer's age")
    private Integer age;

    @JsonProperty(value = "contact")
    @ApiModelProperty(value = "Consumer's contact information")
    private ContactDto contact;

    @JsonProperty(value = "address")
    @ApiModelProperty(value = "Consumer's address information")
    private AddressDto address;

}
