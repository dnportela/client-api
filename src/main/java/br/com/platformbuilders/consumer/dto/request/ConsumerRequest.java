package br.com.platformbuilders.consumer.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsumerRequest {

    @JsonProperty("documentNumber")
    private String documentNumber;

    @JsonProperty("mobilePhoneNumber")
    private String mobilePhoneNumber;

    @JsonProperty("email")
    private String email;

}
