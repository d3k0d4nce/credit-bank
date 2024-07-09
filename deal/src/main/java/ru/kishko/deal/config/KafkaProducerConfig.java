package ru.kishko.deal.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import ru.kishko.openapi.model.EmailMessage;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Value("${kafka.topic.finishRegistration}")
    private String finishRegistration;

    @Value("${kafka.topic.createDocuments}")
    private String createDocuments;

    @Value("${kafka.topic.sendDocuments}")
    private String sendDocuments;

    @Value("${kafka.topic.sendSes}")
    private String sendSes;

    @Value("${kafka.topic.creditIssued}")
    private String creditIssued;

    @Value("${kafka.topic.statementDenied}")
    private String statementDenied;

    private Map<String, Object> commonProducerConfig() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        configProps.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        return configProps;
    }

    @Bean
    public ProducerFactory<Integer, EmailMessage> emailProducerFactory() {
        return new DefaultKafkaProducerFactory<>(commonProducerConfig());
    }

    @Bean
    public KafkaTemplate<Integer, EmailMessage> emailKafkaTemplate() {
        return new KafkaTemplate<>(emailProducerFactory());
    }

    @Bean
    public NewTopic finishRegistration() {
        return TopicBuilder.name(finishRegistration)
                .partitions(1)
                .build();
    }

    @Bean
    public NewTopic createDocuments() {
        return TopicBuilder.name(createDocuments)
                .partitions(1)
                .build();
    }

    @Bean
    public NewTopic sendDocuments() {
        return TopicBuilder.name(sendDocuments)
                .partitions(1)
                .build();
    }

    @Bean
    public NewTopic sendSes() {
        return TopicBuilder.name(sendSes)
                .partitions(1)
                .build();
    }

    @Bean
    public NewTopic creditIssued() {
        return TopicBuilder.name(creditIssued)
                .partitions(1)
                .build();
    }

    @Bean
    public NewTopic statementDenied() {
        return TopicBuilder.name(statementDenied)
                .partitions(1)
                .build();
    }
}