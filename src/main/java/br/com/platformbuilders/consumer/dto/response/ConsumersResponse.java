package br.com.platformbuilders.consumer.dto.response;

import br.com.platformbuilders.consumer.dto.ConsumerDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsumersResponse {

    private List<ConsumerDto> consumers;

}
