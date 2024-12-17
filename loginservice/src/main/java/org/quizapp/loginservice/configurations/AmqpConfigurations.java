/*https://github.com/bogdanmarculescu/microservices2024/blob/main/ongoing/src/main/java/org/cards/ongoinground/configurations/AmqpConfiguration.java*/
package org.quizapp.loginservice.configurations;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;

@Configuration
public class AmqpConfigurations {

    @Bean
    public TopicExchange learningAppExchange(
            @Value("${amqp.exchange.name}") final String exchangeName) {
        return ExchangeBuilder.topicExchange(exchangeName).durable(true).build();
    }

    @Bean
    public Queue userQueue(@Value("${amqp.queue.user.name}") final String queueName) {
        return QueueBuilder.durable(queueName).build();
    }


    @Bean
    public Binding userCreatedBinding(Queue userQueue, TopicExchange learningAppExchange) {
        return BindingBuilder.bind(userQueue).to(learningAppExchange).with("user.created");
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public MessageHandlerMethodFactory messageHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
        MappingJackson2MessageConverter jsonConverter = new MappingJackson2MessageConverter();
        jsonConverter.getObjectMapper().registerModule(new ParameterNamesModule(JsonCreator.Mode.PROPERTIES));
        factory.setMessageConverter(jsonConverter);
        return factory;
    }

    @Bean
    public RabbitListenerConfigurer rabbitListenerConfigurer(MessageHandlerMethodFactory messageHandlerMethodFactory) {
        return (c) -> c.setMessageHandlerMethodFactory(messageHandlerMethodFactory);
    }

}
