package com.carp.runbook.circuit;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/activity")
public class ActivityController {

    private static final Logger LOGGER = org.apache.logging.log4j.LogManager.getLogger(ActivityController.class);

    private final BoredApiClient boredApiClient;

    ActivityController(BoredApiClient boredApiClient) {
        this.boredApiClient = boredApiClient;
    }

    @GetMapping
    @CircuitBreaker(name = "getActivity", fallbackMethod = "getFallbackActivity")
    public Mono<ResponseEntity<ActivityDto>> getActivityResponseEntity() {
        return boredApiClient.getActivity();
    }

    public Mono<ResponseEntity<ActivityDto>> getFallbackActivity(Throwable throwable) {
        LOGGER.info(throwable.getMessage());
        return Mono.just(ResponseEntity.ok(new ActivityDto("Circuit breaker is in action", "Fallback", 0)));
    }
}
