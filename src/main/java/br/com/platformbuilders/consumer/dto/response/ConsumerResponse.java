package br.com.platformbuilders.consumer.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsumerResponse {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("status")
    private HttpStatus status;

    @JsonProperty("message")
    private String message;
}
