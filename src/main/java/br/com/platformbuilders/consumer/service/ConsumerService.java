package br.com.platformbuilders.consumer.service;

import br.com.platformbuilders.consumer.entity.Consumer;
import br.com.platformbuilders.consumer.entity.Contact;
import br.com.platformbuilders.consumer.exception.EntityNotFoundException;
import br.com.platformbuilders.consumer.exception.ValidationException;
import br.com.platformbuilders.consumer.mapper.ConsumerMapper;
import br.com.platformbuilders.consumer.repository.ConsumerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
@RequiredArgsConstructor
public class ConsumerService {

    public static final String CONSUMER_NOT_FOUND_FOR_PARAMS = "Consumer not found for params: documentNumber '%s', mobilePhoneNumber '%s', email '%s'";
    public static final String CONSUMER_NOT_FOUND_FOR_ID = "Consumer not found for id: %s";
    public static final String CONSUMER_ALREADY_EXISTS_FOR_DOCUMENT_NUMBER = "A consumer already exists for document number %s.";
    public static final String FIELDS_NAME_AND_DOCUMENT_NUMBER_ARE_REQUIRED = "Fields name and document number are required.";
    public static final String AT_LEAST_ONE_PARAM_HAVE_TO_BE_INFORMED_TO_SEARCH = "At least one param have to be informed to search a Consumer.";
    public static final String ID_HAVE_TO_BE_INFORMED_FOR_SEARCH = "Id have to be informed for search.";

    private final ConsumerRepository consumerRepository;
    private final ConsumerMapper consumerMapper;

    public List<Consumer> findAll(Pageable pagination) {
        return consumerRepository.getAllConsumersList(pagination);
    }

    public Consumer save(Consumer consumer) {
        try {
            if (isNull(consumer) || isBlank(consumer.getName()) || isBlank(consumer.getDocumentNumber())) {
                throw new ValidationException(FIELDS_NAME_AND_DOCUMENT_NUMBER_ARE_REQUIRED);
            }

            boolean isDocumentNumberExists = consumerRepository.findByDocumentNumber(consumer.getDocumentNumber()).isPresent();

            if (isDocumentNumberExists) {
                throw new ValidationException(String.format(CONSUMER_ALREADY_EXISTS_FOR_DOCUMENT_NUMBER,
                        consumer.getDocumentNumber()));
            }

            consumerRepository.save(consumer);

        } catch (ValidationException e) {
            e.printStackTrace();
            throw e;
        }
        return consumer;
    }

    public Consumer findById(Integer id) {
        try {
            if(isNull(id)){
                throw new ValidationException(ID_HAVE_TO_BE_INFORMED_FOR_SEARCH);
            }

            return consumerRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException(String.format(CONSUMER_NOT_FOUND_FOR_ID, id)));
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public Consumer findByParams(String documentNumber, String mobilePhoneNumber, String email) {
        try {
            if (isBlank(documentNumber) && isBlank(mobilePhoneNumber) && isBlank(email)) {
                throw new ValidationException(AT_LEAST_ONE_PARAM_HAVE_TO_BE_INFORMED_TO_SEARCH);
            }

            return consumerRepository.findByDocumentNumberOrMobilePhoneNumberOrEmail(documentNumber, mobilePhoneNumber, email)
                    .orElseThrow(() -> new EntityNotFoundException(String.format(CONSUMER_NOT_FOUND_FOR_PARAMS,
                            documentNumber, mobilePhoneNumber, email)));

        } catch (EntityNotFoundException | ValidationException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public Consumer updateContact(Integer consumerId, Contact contact) {
        try {
            Consumer consumer = findById(consumerId);
            consumer.setContact(contact);

            return consumerRepository.save(consumer);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public Consumer update(Integer consumerId, Consumer updatedConsumer) {
        Consumer consumerDb = findById(consumerId);

        if (!updatedConsumer.equals(consumerDb)) {
            consumerDb = consumerMapper.merge(consumerDb, updatedConsumer);
            return consumerRepository.save(consumerDb);
        }

        return null;
    }
}
