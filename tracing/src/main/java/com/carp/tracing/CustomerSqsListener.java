package com.carp.tracing;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.sqs.annotation.SqsListener;
import io.micrometer.tracing.Span;
import io.micrometer.tracing.TraceContext;
import io.micrometer.tracing.Tracer;
import org.slf4j.Logger;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class CustomerSqsListener {

    private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(CustomerSqsListener.class);

    private final Tracer tracer;
    private final CustomerService customerService;
    private final ObjectMapper objectMapper;

    CustomerSqsListener(Tracer tracer, CustomerService customerService) {
        this.tracer = tracer;
        this.customerService = customerService;
        objectMapper = new ObjectMapper();
    }

    @SqsListener("http://localhost:4566/000000000000/customer")
    public void receive(String customerMessage, MessageHeaders headers) throws JsonProcessingException {
        assignNewTraceContextScope(headers);
        String traceId = Optional.ofNullable(tracer.currentSpan()).map(Span::context).map(TraceContext::traceId).orElse("none");
        LOG.info("Processing: {} {}", customerMessage, traceId);
        customerService.save(objectMapper.readValue(customerMessage, Customer.class));
    }

    private void assignNewTraceContextScope(MessageHeaders headers) {
        String traceId = (String) headers.get("traceId");
        String spanId = (String) headers.get("spanId");

        LOG.info("receive: {}, {}", traceId, spanId);
        TraceContext traceContext = tracer.traceContextBuilder()
                .traceId(traceId)
                .spanId(spanId)
                .sampled(false)
                .build();
        tracer.currentTraceContext().newScope(traceContext);
    }
}
