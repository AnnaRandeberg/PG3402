package org.quizapp.quizservice.configurations;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmqpConfigurations {

    @Bean
    public TopicExchange quizTopicExchange(
            @Value("${amqp.exchange.name}") final String exchangeName
    ) {
        // Opprett en Topic Exchange
        return ExchangeBuilder
                .topicExchange(exchangeName)
                .durable(true)
                .build();
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue quizQueue() {
        return new Queue("quiz.queue", true);
    }

    @Bean
    public Binding quizBinding(Queue quizQueue, TopicExchange quizTopicExchange) {
        return BindingBuilder.bind(quizQueue).to(quizTopicExchange).with("quiz.#");
    }
}
