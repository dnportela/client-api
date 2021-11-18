package br.com.platformbuilders.consumer.repository;

import br.com.platformbuilders.consumer.entity.Consumer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static br.com.platformbuilders.consumer.mock.ConsumerMock.mockConsumer;
import static br.com.platformbuilders.consumer.mock.ConsumerMock.mockConsumerList;
import static java.time.LocalDate.of;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class ConsumerRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ConsumerRepository consumerRepository;


    @Before
    public void setUp() {

    }


    @Test
    public void whenFindConsumerByValidId_thenReturnConsumer() {
        Consumer savedConsumer = mockConsumer(null, "Consumer 1", "123456789",
                of(1990, 6, 5));

        entityManager.persist(savedConsumer);
        entityManager.flush();

        Integer consumerId = savedConsumer.getId();
        Consumer foundConsumer = consumerRepository.findById(consumerId).get();

        assertNotNull(foundConsumer);
        assertEquals(savedConsumer, foundConsumer);
    }

    @Test
    public void whenFindConsumerByNotValidId_thenReturnNull() {
        Consumer savedConsumer = mockConsumer(null, "Consumer 1", "123456789",
                of(1990, 6, 5));

        entityManager.persist(savedConsumer);
        entityManager.flush();

        Integer consumerId = 99999999;
        Optional<Consumer> foundConsumer = consumerRepository.findById(consumerId);

        assertNotNull(foundConsumer);
        assertFalse(foundConsumer.isPresent());
    }

    @Test
    public void whenFindConsumerByValidDocumentNumber_thenReturnConsumer() {
        String documentNumber = "123456789";
        Consumer savedConsumer = mockConsumer(null, "Consumer 1", documentNumber, of(1990, 6, 5));

        entityManager.persist(savedConsumer);
        entityManager.flush();

        Consumer foundConsumer = consumerRepository.findByDocumentNumber(documentNumber).get();

        assertNotNull(foundConsumer);
        assertEquals(savedConsumer, foundConsumer);
    }

    @Test
    public void whenFindConsumerByNotValidDocumentNumber_thenReturnNull() {
        String documentNumber = "123456789";
        Consumer savedConsumer = mockConsumer(null, "Consumer 1", documentNumber, of(1990, 6, 5));

        entityManager.persist(savedConsumer);
        entityManager.flush();

        documentNumber = documentNumber.concat("8526");
        Optional<Consumer> foundConsumer = consumerRepository.findByDocumentNumber(documentNumber);

        assertNotNull(foundConsumer);
        assertFalse(foundConsumer.isPresent());
    }

    @Test
    public void whenListAllConsumersAndExists_thenReturnConsumerList() {
        Pageable pagination = PageRequest.of(0, 10);
        List<Consumer> savedConsumers = mockConsumerList();
        savedConsumers.stream().forEach(c -> c.setId(null));

        savedConsumers.stream().forEach(c -> {
            entityManager.persist(c);
            entityManager.flush();
        });

        List<Consumer> foundConsumers = consumerRepository.getAllConsumersList(pagination);

        assertNotNull(foundConsumers);
        assertEquals(savedConsumers, foundConsumers);
    }

    @Test
    public void whenListAllConsumersAndNotExists_thenReturnNull() {
        Pageable pagination = PageRequest.of(0, 10);

        List<Consumer> foundConsumers = consumerRepository.getAllConsumersList(pagination);

        assertNotNull(foundConsumers);
        assertEquals(0, foundConsumers.size());
    }

    @Test
    public void whenFindConsumerByValidMobilePhone_thenReturnConsumer() {
        String mobilePhone = "11 99999-9999";
        Consumer savedConsumer = mockConsumer(null, "Consumer 1", "123456", of(1990, 6, 5));
        savedConsumer.getContact().setMobilePhoneNumber(mobilePhone);

        entityManager.persist(savedConsumer);
        entityManager.flush();

        Consumer foundConsumer = consumerRepository.findByDocumentNumberOrMobilePhoneNumberOrEmail(null, mobilePhone, null).get();

        assertNotNull(foundConsumer);
        assertEquals(savedConsumer, foundConsumer);
    }

    @Test
    public void whenFindConsumerByValidEmail_thenReturnConsumer() {
        String email = "test@email.com";
        Consumer savedConsumer = mockConsumer(null, "Consumer 1", "123456", of(1990, 6, 5));
        savedConsumer.getContact().setEmail(email);

        entityManager.persist(savedConsumer);
        entityManager.flush();

        Consumer foundConsumer = consumerRepository.findByDocumentNumberOrMobilePhoneNumberOrEmail(null, null, email).get();

        assertNotNull(foundConsumer);
        assertEquals(savedConsumer, foundConsumer);
    }

    @Test
    public void whenFindConsumerByNullParams_thenReturnNull() {
        Consumer savedConsumer = mockConsumer(null, "Consumer 1", "123456", of(1990, 6, 5));

        entityManager.persist(savedConsumer);
        entityManager.flush();

        Optional<Consumer> foundConsumer = consumerRepository.findByDocumentNumberOrMobilePhoneNumberOrEmail(null, null, null);

        assertNotNull(foundConsumer);
        assertFalse(foundConsumer.isPresent());
    }
}

