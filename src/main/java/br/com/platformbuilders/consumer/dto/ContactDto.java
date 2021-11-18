package br.com.platformbuilders.consumer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="Contact", description="Model class for contact information")
public class ContactDto {

    @JsonProperty(value = "mobilePhoneNumber")
    @NotBlank(message = "Consumer mobile phone number is required")
    @ApiModelProperty(value = "Mobile phone number")
    private String mobilePhoneNumber;

    @JsonProperty(value = "residencePhoneNumber")
    @ApiModelProperty(value = "Residencial phone number")
    private String residencePhoneNumber;

    @JsonProperty(value = "email")
    @NotBlank(message = "Consumer email is required")
    @ApiModelProperty(value = "E-mail address")
    private String email;
}
