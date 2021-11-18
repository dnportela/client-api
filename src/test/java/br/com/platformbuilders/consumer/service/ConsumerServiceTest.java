package br.com.platformbuilders.consumer.service;

import br.com.platformbuilders.consumer.entity.Consumer;
import br.com.platformbuilders.consumer.entity.Contact;
import br.com.platformbuilders.consumer.exception.EntityNotFoundException;
import br.com.platformbuilders.consumer.exception.ValidationException;
import br.com.platformbuilders.consumer.mapper.AddressMapper;
import br.com.platformbuilders.consumer.mapper.ConsumerMapper;
import br.com.platformbuilders.consumer.mapper.ContactMapper;
import br.com.platformbuilders.consumer.repository.ConsumerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static br.com.platformbuilders.consumer.mock.ConsumerMock.*;
import static br.com.platformbuilders.consumer.service.ConsumerService.CONSUMER_NOT_FOUND_FOR_PARAMS;
import static java.time.LocalDate.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ConsumerServiceTest {

    @Spy
    private ConsumerMapper consumerMapper = new ConsumerMapper(new AddressMapper(), new ContactMapper());

    @Mock
    private ConsumerRepository consumerRepository;

    @InjectMocks
    private ConsumerService consumerService;

    @Test
    public void givenHasConsumersInDatabaseFindAll_ThenReturnListOfConsumers() {
        Pageable pagination = PageRequest.of(0, 10);
        List<Consumer> expectedList = mockConsumerList();

        when(consumerRepository.getAllConsumersList(eq(pagination)))
                .thenReturn(expectedList);

        List<Consumer> consumerList = consumerService.findAll(pagination);

        assertNotNull(consumerList);
        assertEquals(expectedList, consumerList);
    }

    @Test(expected = ValidationException.class)
    public void givenConsumerHasNoNameWhenSaves_ThenThrowValidationException() {
        Consumer wrongConsumer = mockConsumer(null, null, "123456789", of(1990, 6, 5));

        consumerService.save(wrongConsumer);
    }

    @Test(expected = ValidationException.class)
    public void givenConsumerHasNoDocumentNumberWhenSaves_ThenThrowValidationException() {
        Consumer wrongConsumer = mockConsumer(null, "Consumer Test", null, of(1990, 6, 5));

        consumerService.save(wrongConsumer);
    }

    @Test(expected = ValidationException.class)
    public void givenConsumerWithDocumentNumberExistsWhenSaves_ThenThrowValidationException() {
        String documentNumber = "123456789";
        Consumer newConsumer = mockConsumer(null, "Consumer Test", documentNumber, of(1990, 6, 5));
        Consumer existentConsumer = mockConsumer(1, "Consumer Saved", documentNumber, of(1980, 2, 1));

        when(consumerRepository.findByDocumentNumber(eq(documentNumber)))
                .thenReturn(Optional.of(existentConsumer));

        consumerService.save(newConsumer);
    }

    @Test
    public void givenConsumerDataOkWhenSaves_ThenSavesConsumer() {
        Consumer newConsumer = mockConsumer(1, "Consumer Test", "123456789", of(1990, 6, 5));

        when(consumerRepository.save(newConsumer)).thenReturn(newConsumer);

        Consumer savedConsumer = consumerService.save(newConsumer);

        assertNotNull(savedConsumer);
        assertNotNull(savedConsumer.getId());
        assertEquals(newConsumer, savedConsumer);
    }

    @Test(expected = ValidationException.class)
    public void givenConsumerIdIsNullWhenFind_ThenThrowValidateException() {
        consumerService.findById(null);
    }

    @Test
    public void givenConsumerIdExistsWhenFind_ThenReturnsConsumer() {
        Integer consumerId = 1;
        Consumer savedConsumer = mockConsumer(consumerId, "Consumer Test", "123456789", of(1990, 6, 5));

        when(consumerRepository.findById(consumerId))
                .thenReturn(Optional.of(savedConsumer));

        Consumer returnedConsumer = consumerService.findById(consumerId);

        assertNotNull(returnedConsumer);
        assertNotNull(returnedConsumer.getId());
        assertEquals(savedConsumer, returnedConsumer);
    }

    @Test(expected = ValidationException.class)
    public void givenNullParamsWhenFindByParams_ThenThrowValidationException() {
        consumerService.findByParams(null, null, null);
    }

    @Test(expected = ValidationException.class)
    public void givenEmptyParamsWhenFindByParams_ThenThrowValidationException() {
        consumerService.findByParams("", "", "");
    }

    @Test(expected = EntityNotFoundException.class)
    public void givenConsumerNotFoundWhenFindByParams_ThenThrowEntityNotFoundException() {
       when(consumerRepository.findByDocumentNumberOrMobilePhoneNumberOrEmail(CONSUMER1_DOCUMENT_NUMBER, null, null))
                .thenThrow(new EntityNotFoundException(String.format(CONSUMER_NOT_FOUND_FOR_PARAMS, CONSUMER1_DOCUMENT_NUMBER, null, null)));

        consumerService.findByParams(CONSUMER1_DOCUMENT_NUMBER, null, null);
    }

    @Test
    public void givenDocumentNumberExistsWhenFindByParamsThenReturnsConsumer() {
        Consumer savedConsumer = mockConsumer(CONSUMER1_ID, CONSUMER1_NAME, CONSUMER1_DOCUMENT_NUMBER, CONSUMER1_BIRTHDATE);

        when(consumerRepository.findByDocumentNumberOrMobilePhoneNumberOrEmail(CONSUMER1_DOCUMENT_NUMBER, null, null))
                .thenReturn(Optional.of(savedConsumer));

        Consumer returnedConsumer = consumerService.findByParams(CONSUMER1_DOCUMENT_NUMBER, null, null);

        assertNotNull(returnedConsumer);
        assertNotNull(returnedConsumer.getId());
        assertEquals(savedConsumer, returnedConsumer);
}

    @Test
    public void givenDataOkWhenUpdateContact_ThenReturnsConsumer() {
        Contact newContact = mockContact("teste@email.com", "11 36259-8734", "11 987349-2734");
        Consumer savedConsumer = mockConsumer(CONSUMER1_ID, CONSUMER1_NAME, CONSUMER1_DOCUMENT_NUMBER, CONSUMER1_BIRTHDATE);

        Consumer updatedConsumer = mockConsumer(CONSUMER1_ID, CONSUMER1_NAME, CONSUMER1_DOCUMENT_NUMBER, CONSUMER1_BIRTHDATE);
        updatedConsumer.setContact(newContact);

        when(consumerRepository.findById(CONSUMER1_ID)).thenReturn(Optional.of(savedConsumer));
        when(consumerRepository.save(savedConsumer)).thenReturn(updatedConsumer);

        Consumer returnedConsumer = consumerService.updateContact(CONSUMER1_ID, newContact);

        assertNotNull(returnedConsumer);
        assertNotNull(returnedConsumer.getId());
        assertEquals(savedConsumer, returnedConsumer);
    }

    @Test
    public void givenDataOkWhenUpdateConsumer_ThenReturnsConsumer() {
        Consumer savedConsumer = mockConsumer(CONSUMER1_ID, CONSUMER1_NAME, CONSUMER1_DOCUMENT_NUMBER, CONSUMER1_BIRTHDATE);
        Consumer updatedConsumer = mockConsumer(CONSUMER1_ID, CONSUMER2_NAME, CONSUMER2_DOCUMENT_NUMBER, CONSUMER2_BIRTHDATE);

        when(consumerRepository.findById(CONSUMER1_ID)).thenReturn(Optional.of(savedConsumer));
        when(consumerRepository.save(updatedConsumer)).thenReturn(updatedConsumer);

        Consumer returnedConsumer = consumerService.update(CONSUMER1_ID, updatedConsumer);

        assertNotNull(returnedConsumer);
        assertNotNull(returnedConsumer.getId());
        assertEquals(updatedConsumer, returnedConsumer);
    }
}
