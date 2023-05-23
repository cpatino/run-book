package com.carp.sns;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.sns.core.SnsTemplate;
import org.slf4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sns.SnsClient;

import java.util.Map;
import java.util.Random;

@Component
class SnsMessagePublisher implements CommandLineRunner {

    private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(SnsMessagePublisher.class);
    private static final String TOPIC_NAME = "customer_topic";

    private final SnsTemplate snsTemplate;
    private final ObjectMapper objectMapper;
    private final Random randomService;

    SnsMessagePublisher(SnsClient snsClient, SnsTemplate snsTemplate) {
        snsClient.createTopic(builder -> builder.name(TOPIC_NAME).build());
        snsClient.subscribe(builder -> builder.topicArn("arn:aws:sns:us-east-1:000000000000:" + TOPIC_NAME)
                .protocol("sqs")
                .endpoint("arn:aws:sqs:us-east-1:000000000000:customer")
                .attributes(Map.of("RawMessageDelivery", "true"))
                .build());

        this.snsTemplate = snsTemplate;
        objectMapper = new ObjectMapper();
        randomService = new Random();
    }

    @Override
    public void run(String... args) {
        // traceId and spanId are generated with this length to match the length of the traceId and spanId generated by the micrometer tracing library
        String traceId = buildRandomHex(32);
        String spanId = buildRandomHex(16);

        LOG.info("send: {} {}", traceId, spanId);

        Map<String, Object> headers = Map.of("traceId", traceId, "spanId", spanId);
        String message = buildCustomerMessage(new Customer(4, "Jones"));

        snsTemplate.convertAndSend(TOPIC_NAME, message, headers);
    }

    private String buildCustomerMessage(Customer customer) {
        try {
            return objectMapper.writeValueAsString(customer);
        } catch (JsonProcessingException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    private String buildRandomHex(int randomHexLength) {
        StringBuilder sb = new StringBuilder();
        while (sb.length() < randomHexLength) {
            sb.append(Integer.toHexString(randomService.nextInt()));
        }
        sb.setLength(randomHexLength);
        return sb.toString();
    }
}
