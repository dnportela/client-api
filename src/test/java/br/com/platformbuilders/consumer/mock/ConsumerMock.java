package br.com.platformbuilders.consumer.mock;

import br.com.platformbuilders.consumer.dto.AddressDto;
import br.com.platformbuilders.consumer.dto.ConsumerDto;
import br.com.platformbuilders.consumer.dto.ContactDto;
import br.com.platformbuilders.consumer.dto.request.ConsumerRequest;
import br.com.platformbuilders.consumer.dto.response.ConsumerResponse;
import br.com.platformbuilders.consumer.dto.response.ConsumersResponse;
import br.com.platformbuilders.consumer.entity.Address;
import br.com.platformbuilders.consumer.entity.Consumer;
import br.com.platformbuilders.consumer.entity.Contact;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static br.com.platformbuilders.consumer.utils.ConsumerUtils.calculateAge;
import static java.time.LocalDate.of;
import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.Locale.US;
import static java.util.Objects.isNull;

public class ConsumerMock {

    private static DateTimeFormatter formatter = ofPattern("yyyy-MM-dd").withLocale(US);

    public static final String RESIDENCE_PHONE_NUMBER = "11 99999-9999";
    public static final String MOBILE_PHONE_NUMBER = "11 5555-5555";
    public static final String EMAIL = "consumer@platformbuilders.com";

    public static final String STATE = "São Paulo";
    public static final String CITY = "São Paulo";
    public static final String STREET = "Av Paulista";
    public static final String ADDRESS_NUMBER = "1000";
    public static final String POSTAL_CODE = "11111-111";

    public static final int CONSUMER1_ID = 1;
    public static final String CONSUMER1_NAME = "Consumer Test";
    public static final String CONSUMER1_DOCUMENT_NUMBER = "123456789";
    public static final LocalDate CONSUMER1_BIRTHDATE = of(1990, 6, 5);

    public static final int CONSUMER2_ID = 2;
    public static final String CONSUMER2_NAME = "Consumer Test 2";
    public static final String CONSUMER2_DOCUMENT_NUMBER = "987654321";
    public static final LocalDate CONSUMER2_BIRTHDATE = of(1995, 2, 20);



    public static ConsumerResponse mockConsumerCreateResponse(Integer id, HttpStatus status, String message){
        return new ConsumerResponse(id, status, message);
    }

    public static ConsumersResponse mockConsumersResponse() {
        return ConsumersResponse.builder()
                .consumers(mockConsumerDtoList())
                .build();
    }

    public static ConsumerRequest mockConsumerRequest(String documentNumber, String mobilePhoneNumber, String email) {
        return ConsumerRequest.builder()
                            .documentNumber(documentNumber)
                            .mobilePhoneNumber(mobilePhoneNumber)
                            .email(email)
                            .build();
    }

    public static List<Consumer> mockConsumerList(){
        Consumer consumer1 = mockConsumer(CONSUMER1_ID, CONSUMER1_NAME, CONSUMER1_DOCUMENT_NUMBER, CONSUMER1_BIRTHDATE);
        Consumer consumer2 = mockConsumer(CONSUMER2_ID, CONSUMER2_NAME, CONSUMER2_DOCUMENT_NUMBER, CONSUMER2_BIRTHDATE);
        return List.of(consumer1, consumer2);
    }

    public static Consumer mockConsumer(Integer id, String name, String documentNumber, LocalDate birthDate) {
        return Consumer.builder()
                .id(id)
                .name(name)
                .documentNumber(documentNumber)
                .birthDate(birthDate)
                .contact(mockContact())
                .address(mockAddress())
                .build();
    }

    public static Contact mockContact(String mobilePhone, String residencePhone, String email) {
        return Contact.builder()
                .email(email)
                .residencePhoneNumber(residencePhone)
                .mobilePhoneNumber(mobilePhone)
                .build();
    }

    private static Contact mockContact() {
        return mockContact(EMAIL, RESIDENCE_PHONE_NUMBER, MOBILE_PHONE_NUMBER);
    }

    private static Address mockAddress() {
        return Address.builder()
                .state(STATE)
                .city(CITY)
                .street(STREET)
                .number(ADDRESS_NUMBER)
                .postalCode(POSTAL_CODE)
                .build();
    }



    public static List<ConsumerDto> mockConsumerDtoList(){
        ConsumerDto consumer1 = mockConsumerDto(CONSUMER1_ID, CONSUMER1_NAME, CONSUMER1_DOCUMENT_NUMBER, CONSUMER1_BIRTHDATE);
        ConsumerDto consumer2 = mockConsumerDto(CONSUMER2_ID,CONSUMER2_NAME, CONSUMER2_DOCUMENT_NUMBER, CONSUMER2_BIRTHDATE);
        return List.of(consumer1, consumer2);
    }

    public static ConsumerDto mockConsumerDto(Integer id, String name, String documentNumber, LocalDate birthDate) {
        String birthDateString = isNull(birthDate) ? null : birthDate.format(formatter);
        Integer age = isNull(birthDate) ? null : calculateAge(birthDate);

        return ConsumerDto.builder()
                .id(id)
                .name(name)
                .documentNumber(documentNumber)
                .age(age)
                .birthDate(birthDateString)
                .contact(mockContactDto())
                .address(mockAddressDto())
                .build();
    }

    private static ContactDto mockContactDto() {
        return ContactDto.builder()
                .email(EMAIL)
                .residencePhoneNumber(RESIDENCE_PHONE_NUMBER)
                .mobilePhoneNumber(MOBILE_PHONE_NUMBER)
                .build();
    }

    private static AddressDto mockAddressDto() {
        return AddressDto.builder()
                .state(STATE)
                .city(CITY)
                .street(STREET)
                .number(ADDRESS_NUMBER)
                .postalCode(POSTAL_CODE)
                .build();
    }
}
