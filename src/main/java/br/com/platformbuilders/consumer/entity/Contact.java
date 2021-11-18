package br.com.platformbuilders.consumer.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Embeddable
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Contact implements Serializable {

    @Column(name = "MOBILE_PHONE_NUMBER")
    @NotBlank(message = "Mobile phone number is required")
    private String mobilePhoneNumber;

    @Column(name = "RESIDENCE_PHONE_NUMBER")
    private String residencePhoneNumber;

    @Column(name = "EMAIL")
    @NotBlank(message = "Email is required")
    private String email;
}
