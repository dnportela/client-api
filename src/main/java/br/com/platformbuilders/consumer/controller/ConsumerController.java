package br.com.platformbuilders.consumer.controller;

import br.com.platformbuilders.consumer.dto.ConsumerDto;
import br.com.platformbuilders.consumer.dto.ContactDto;
import br.com.platformbuilders.consumer.dto.request.ConsumerRequest;
import br.com.platformbuilders.consumer.dto.response.ConsumerResponse;
import br.com.platformbuilders.consumer.dto.response.ConsumersResponse;
import br.com.platformbuilders.consumer.entity.Consumer;
import br.com.platformbuilders.consumer.entity.Contact;
import br.com.platformbuilders.consumer.mapper.ConsumerMapper;
import br.com.platformbuilders.consumer.mapper.ContactMapper;
import br.com.platformbuilders.consumer.service.ConsumerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@RequestMapping("/consumers")
@RequiredArgsConstructor
@Api(value = "/consumers", description = "Operations about consumers")
public class ConsumerController {

    public static final String NEW_CONSUMER_CREATED_WITH_SUCCESS = "New consumer created with success.";
    public static final String CONSUMER_UPDATED_WITH_SUCCESS = "Consumer updated with success.";
    public static final String CONSUMER_CONTACT_UPDATED_WITH_SUCCESS = "Consumer contact updated with success.";

    private final ConsumerService consumerService;
    private final ConsumerMapper consumerMapper;
    private final ContactMapper contactMapper;


    @ResponseBody
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "List all consumers", response = ConsumersResponse.class)
    public ConsumersResponse listAllConsumers(@RequestParam("pageNumber") Integer pageNumber,
                                              @RequestParam("pageSize") Integer pageSize) {

        Pageable pagination = PageRequest.of(pageNumber, pageSize);
        List<Consumer> allConsumers = consumerService.findAll(pagination);
        return consumerMapper.listToResponse(allConsumers);
    }


    @ResponseBody
    @GetMapping(value = "/{consumerId}", produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Search a consumer by Id", response = ConsumerDto.class)
    public ConsumerDto findConsumerById(@PathVariable Integer consumerId) {
        Consumer consumer = consumerService.findById(consumerId);
        return consumerMapper.toDto(consumer);
    }


    @ResponseBody
    @GetMapping(value = "/by-params", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Search a consumer by params", response = ConsumerDto.class)
    public ConsumerDto findConsumerByParams(@RequestBody ConsumerRequest request) {
        Consumer consumer = consumerService.findByParams(request.getDocumentNumber(),
                request.getMobilePhoneNumber(),
                request.getEmail());

        return consumerMapper.toDto(consumer);

    }


    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Creates a new Consumer", response = ConsumerResponse.class)
    public ConsumerResponse createConsumer(@RequestBody @Valid ConsumerDto consumerDto) {
        Consumer updatedConsumer = consumerService.save(consumerMapper.toEntity(consumerDto));
        return new ConsumerResponse(updatedConsumer.getId(), CREATED, NEW_CONSUMER_CREATED_WITH_SUCCESS);
    }


    @PutMapping(value = "/{consumerId}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update consumer data", response = ConsumerResponse.class)
    public ConsumerResponse updateConsumer(@PathVariable Integer consumerId, @RequestBody @Valid ConsumerDto consumerDto) {
        Consumer consumer = consumerMapper.toEntity(consumerDto);
        consumer = consumerService.update(consumerId, consumer);
        return new ConsumerResponse(consumer.getId(), OK, CONSUMER_UPDATED_WITH_SUCCESS);
    }


    @PatchMapping(value = "/{consumerId}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update consumer contact data", response = ConsumerResponse.class)
    public ConsumerResponse updateContactData(@PathVariable Integer consumerId, @RequestBody ContactDto contactDto) {
        Contact contact = contactMapper.toEntity(contactDto);
        Consumer consumer = consumerService.updateContact(consumerId, contact);
        return new ConsumerResponse(consumer.getId(), OK, CONSUMER_CONTACT_UPDATED_WITH_SUCCESS);
    }

}
