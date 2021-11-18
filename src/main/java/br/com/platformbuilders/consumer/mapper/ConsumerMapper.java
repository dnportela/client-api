package br.com.platformbuilders.consumer.mapper;

import br.com.platformbuilders.consumer.dto.ConsumerDto;
import br.com.platformbuilders.consumer.dto.response.ConsumersResponse;
import br.com.platformbuilders.consumer.entity.Consumer;
import br.com.platformbuilders.consumer.utils.ConsumerUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.Locale.US;
import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
public class ConsumerMapper {

    private final AddressMapper addressMapper;
    private final ContactMapper contactMapper;

    private DateTimeFormatter formatter = ofPattern("yyyy-MM-dd").withLocale(US);


    public ConsumersResponse listToResponse(List<Consumer> consumers){
        return ConsumersResponse.builder()
                .consumers(consumers.stream()
                        .map(consumer -> toDto(consumer))
                        .collect(Collectors.toList()))
                .build();
    }

    public ConsumerDto toDto(Consumer consumer){
        Integer age = ConsumerUtils.calculateAge(consumer.getBirthDate());
        String birthDate = isNull(consumer.getBirthDate()) ? null
                : consumer.getBirthDate().format(formatter);

        return ConsumerDto.builder()
                .id(consumer.getId())
                .name(consumer.getName())
                .birthDate(birthDate)
                .age(age)
                .documentNumber(consumer.getDocumentNumber())
                .address(addressMapper.toDto(consumer.getAddress()))
                .contact(contactMapper.toDto(consumer.getContact()))
                .build();
    }

    public Consumer toEntity(ConsumerDto consumerDto){
        LocalDate birthDate = isNull(consumerDto.getBirthDate()) ? null
                                    : LocalDate.parse(consumerDto.getBirthDate(), formatter);

        return Consumer.builder()
                .id(consumerDto.getId())
                .name(consumerDto.getName())
                .birthDate(birthDate)
                .documentNumber(consumerDto.getDocumentNumber())
                .address(addressMapper.toEntity(consumerDto.getAddress()))
                .contact(contactMapper.toEntity(consumerDto.getContact()))
                .build();
    }

    public Consumer merge(Consumer consumerDb, Consumer updatedConsumer) {
        consumerDb.setName(updatedConsumer.getName());
        consumerDb.setDocumentNumber(updatedConsumer.getDocumentNumber());
        consumerDb.setBirthDate(updatedConsumer.getBirthDate());
        consumerDb.setContact(updatedConsumer.getContact());

        contactMapper.merge(consumerDb.getContact(), updatedConsumer.getContact());
        addressMapper.merge(consumerDb.getAddress(), updatedConsumer.getAddress());
        return consumerDb;
    }
}
