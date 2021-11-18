package br.com.platformbuilders.consumer.controller;

import br.com.platformbuilders.consumer.dto.ConsumerDto;
import br.com.platformbuilders.consumer.dto.ContactDto;
import br.com.platformbuilders.consumer.dto.response.ConsumerResponse;
import br.com.platformbuilders.consumer.dto.response.ConsumersResponse;
import br.com.platformbuilders.consumer.entity.Consumer;
import br.com.platformbuilders.consumer.entity.Contact;
import br.com.platformbuilders.consumer.mapper.ConsumerMapper;
import br.com.platformbuilders.consumer.mapper.ContactMapper;
import br.com.platformbuilders.consumer.service.ConsumerService;
import br.com.platformbuilders.consumer.utils.JsonUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static br.com.platformbuilders.consumer.controller.ConsumerController.*;
import static br.com.platformbuilders.consumer.mock.ConsumerMock.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ConsumerController.class)
public class ConsumerControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ConsumerService consumerService;

    @MockBean
    private ConsumerMapper consumerMapper;

    @MockBean
    private ContactMapper contactMapper;

    @Test
    public void givenConsumerId_whenGetConsumer_thenStatus200() throws Exception {
        Consumer consumer = mockConsumer(CONSUMER1_ID, CONSUMER1_NAME, CONSUMER1_DOCUMENT_NUMBER, CONSUMER1_BIRTHDATE);
        ConsumerDto consumerDto = mockConsumerDto(CONSUMER1_ID, CONSUMER1_NAME, CONSUMER1_DOCUMENT_NUMBER, CONSUMER1_BIRTHDATE);
        String responseJson = JsonUtils.toJson(consumerDto);

        when(consumerService.findById(1)).thenReturn(consumer);
        when(consumerMapper.toDto(consumer)).thenReturn(consumerDto);

        mvc.perform(get("/consumers/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(responseJson));
    }

    @Test
    public void givenConsumers_whenGetAllConsumer_thenStatus200() throws Exception {
        List<Consumer> consumers = mockConsumerList();
        ConsumersResponse consumerResponse = mockConsumersResponse();
        String responseJson = JsonUtils.toJson(consumerResponse);

        Pageable pagination = PageRequest.of(0, 10);

        when(consumerService.findAll(pagination)).thenReturn(consumers);
        when(consumerMapper.listToResponse(consumers)).thenReturn(consumerResponse);

        mvc.perform(get("/consumers?pageNumber=0&pageSize=10"))
                .andExpect(status().isOk())
                .andExpect(content().json(responseJson));
    }

    @Test
    public void givenConsumer_whenGetConsumerByParams_thenStatus200() throws Exception {
        Consumer consumer = mockConsumer(CONSUMER1_ID, CONSUMER1_NAME, CONSUMER1_DOCUMENT_NUMBER, CONSUMER1_BIRTHDATE);
        ConsumerDto consumerDto = mockConsumerDto(CONSUMER1_ID, CONSUMER1_NAME, CONSUMER1_DOCUMENT_NUMBER, CONSUMER1_BIRTHDATE);

        String requestJson = JsonUtils.toJson(mockConsumerRequest(CONSUMER1_DOCUMENT_NUMBER, null, null));
        String responseJson = JsonUtils.toJson(consumerDto);

        when(consumerService.findByParams(CONSUMER1_DOCUMENT_NUMBER, null, null)).thenReturn(consumer);
        when(consumerMapper.toDto(consumer)).thenReturn(consumerDto);

        mvc.perform(get("/consumers/by-params")
                    .contentType(APPLICATION_JSON)
                    .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().json(responseJson));
    }

    @Test
    public void givenConsumer_whenPostConsumer_thenStatus200() throws Exception {
        Consumer consumer = mockConsumer(1, CONSUMER1_NAME, CONSUMER1_DOCUMENT_NUMBER, CONSUMER1_BIRTHDATE);
        ConsumerDto consumerDto = mockConsumerDto(null, CONSUMER1_NAME, CONSUMER1_DOCUMENT_NUMBER, CONSUMER1_BIRTHDATE);
        ConsumerResponse consumerResponse = mockConsumerCreateResponse(CONSUMER1_ID, CREATED, NEW_CONSUMER_CREATED_WITH_SUCCESS);

        String requestJson = JsonUtils.toJson(consumerDto);
        String responseJson = JsonUtils.toJson(consumerResponse);

        when(consumerMapper.toEntity(any(ConsumerDto.class))).thenReturn(consumer);
        when(consumerService.save(any(Consumer.class))).thenReturn(consumer);

        mvc.perform(post("/consumers")
                    .contentType(APPLICATION_JSON)
                    .accept(APPLICATION_JSON)
                    .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().json(responseJson));
    }

    @Test
    public void givenConsumer_whenPutUpdatedConsumer_thenStatus200() throws Exception {
        Consumer consumer = mockConsumer(1, CONSUMER1_NAME, CONSUMER1_DOCUMENT_NUMBER, CONSUMER1_BIRTHDATE);
        ConsumerDto consumerDto = mockConsumerDto(null, CONSUMER1_NAME, CONSUMER1_DOCUMENT_NUMBER, CONSUMER1_BIRTHDATE);
        ConsumerResponse consumerResponse = mockConsumerCreateResponse(CONSUMER1_ID, OK, CONSUMER_UPDATED_WITH_SUCCESS);

        String requestJson = JsonUtils.toJson(consumerDto);
        String responseJson = JsonUtils.toJson(consumerResponse);

        when(consumerMapper.toEntity(any(ConsumerDto.class))).thenReturn(consumer);
        when(consumerService.update(eq(CONSUMER1_ID), any(Consumer.class))).thenReturn(consumer);

        consumerDto.setDocumentNumber(CONSUMER2_DOCUMENT_NUMBER);
        when(consumerMapper.toDto(any(Consumer.class))).thenReturn(consumerDto);

        mvc.perform(put("/consumers/1")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().json(responseJson));
    }

    @Test
    public void givenConsumer_whenPatchUpdatedContact_thenStatus200() throws Exception {
        Consumer consumer = mockConsumer(1, CONSUMER1_NAME, CONSUMER1_DOCUMENT_NUMBER, CONSUMER1_BIRTHDATE);
        Contact contact = Contact.builder().email("newEmail@test.com").build();
        ConsumerResponse consumerResponse = mockConsumerCreateResponse(CONSUMER1_ID, OK, CONSUMER_CONTACT_UPDATED_WITH_SUCCESS);

        String requestJson = JsonUtils.toJson(contact);
        String responseJson = JsonUtils.toJson(consumerResponse);

        when(contactMapper.toEntity(any(ContactDto.class))).thenReturn(contact);
        when(consumerService.updateContact(eq(CONSUMER1_ID), any(Contact.class))).thenReturn(consumer);

        mvc.perform(patch("/consumers/1")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().json(responseJson));
    }
}
