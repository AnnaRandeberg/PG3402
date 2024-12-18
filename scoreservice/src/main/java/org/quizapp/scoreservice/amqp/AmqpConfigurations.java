/*source: https://github.com/bogdanmarculescu/microservices2024/blob/main/ongoing/src/main/java/org/cards/ongoinground/configurations/AmqpConfiguration.java*/

package org.quizapp.scoreservice.amqp;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmqpConfigurations {

    @Bean
    public TopicExchange learningAppExchange(
            @Value("${amqp.exchange.name}") final String exchangeName) {
        return ExchangeBuilder.topicExchange(exchangeName).durable(true).build();
    }

    @Bean
    public Queue quizCompleteQueue(@Value("${amqp.queue.complete.name}") final String queueName) {
        return QueueBuilder.durable(queueName).build();
    }

    @Bean
    public Binding quizCompleteBinding(
            final Queue quizCompleteQueue,
            final TopicExchange learningAppExchange) {
        return BindingBuilder.bind(quizCompleteQueue)
                .to(learningAppExchange)
                .with("quiz.complete");
    }

    @Bean
    public Jackson2JsonMessageConverter consumerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}
