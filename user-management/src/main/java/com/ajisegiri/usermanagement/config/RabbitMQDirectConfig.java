package com.ajisegiri.usermanagement.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMQDirectConfig {
    public static final String WALLET_QUEUE="wallet.events";

	@Bean
	Queue walletQueue() { return new Queue("walletQueue", false); }

	@Bean
	DirectExchange exchange() {
		return new DirectExchange("direct-exchange");
	}

	@Bean
	Binding notificationQueueBinding(Queue notificationQueue, DirectExchange exchange) {
		return BindingBuilder.bind(notificationQueue).to(exchange).with(WALLET_QUEUE);
	}


	@Bean
	public MessageConverter jsonMessageConverter(ObjectMapper objectMapper) {
		return new Jackson2JsonMessageConverter(objectMapper);
	}

	@Bean
	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter jsonMessageConverter) {
		RabbitTemplate template = new RabbitTemplate(connectionFactory);
		template.setMessageConverter(jsonMessageConverter);
		return template;
	}

}
