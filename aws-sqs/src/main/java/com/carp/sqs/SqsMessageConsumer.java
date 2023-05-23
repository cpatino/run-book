package com.carp.sqs;

import io.awspring.cloud.sqs.annotation.SqsListener;
import org.slf4j.Logger;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

@Component
class SqsMessageConsumer {

    private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(SqsMessageConsumer.class);

    @SqsListener("http://localhost:4566/000000000000/customer")
    public void receive(String message, MessageHeaders headers) {
        LOG.info("Received message: {}", message);
    }
}
