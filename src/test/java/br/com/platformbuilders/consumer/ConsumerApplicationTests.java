package br.com.platformbuilders.consumer;

import br.com.platformbuilders.consumer.controller.ConsumerController;
import br.com.platformbuilders.consumer.mapper.ConsumerMapper;
import br.com.platformbuilders.consumer.repository.ConsumerRepository;
import br.com.platformbuilders.consumer.service.ConsumerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ConsumerApplicationTests {

	@Autowired
	private ConsumerController consumerController;

	@Autowired
	private ConsumerService consumerService;

	@Autowired
	private ConsumerRepository consumerRepository;

	@Autowired
	private ConsumerMapper consumerMapper;

	@Test
	void contextLoads() {
		assertThat(consumerController).isNotNull();
		assertThat(consumerService).isNotNull();
		assertThat(consumerRepository).isNotNull();
		assertThat(consumerMapper).isNotNull();
	}

}
