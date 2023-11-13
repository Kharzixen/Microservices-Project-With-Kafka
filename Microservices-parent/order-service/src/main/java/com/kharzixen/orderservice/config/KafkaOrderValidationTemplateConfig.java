package com.kharzixen.orderservice.config;

import com.kharzixen.orderservice.event.OrderNotificationEvent;
import com.kharzixen.orderservice.event.OrderValidationEvent;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaOrderValidationTemplateConfig {
    @Value("${spring.kafka.order-validation-template.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.order-validation-template.producer.key-serializer}")
    private String keySerializer;

    @Value("${spring.kafka.order-validation-template.producer.value-serializer}")
    private String valueSerializer;

    @Value("${spring.kafka.order-validation-template.producer.properties.spring.json.type.mapping}")
    private String typeMapping;

    @Bean
    public KafkaTemplate<String, OrderValidationEvent> orderValidationTemplate() {
        Map<String, Object> producerProps = new HashMap<>();
        producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keySerializer);
        producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializer);
        producerProps.put(JsonSerializer.TYPE_MAPPINGS, typeMapping);

        DefaultKafkaProducerFactory<String, OrderValidationEvent> producerFactory =
                new DefaultKafkaProducerFactory<>(producerProps);

        return new KafkaTemplate<>(producerFactory);
    }

}
