package io.disc99.protocol.buffers.spring.cloud.stream;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.stream.annotation.StreamMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MessageConverter;

@Configuration
public class ProtocolBuffersSpringCloudStreamAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    @StreamMessageConverter
    public MessageConverter protobufMessageConverter() {
        return new ProtobufMessageConverter();
    }
}
