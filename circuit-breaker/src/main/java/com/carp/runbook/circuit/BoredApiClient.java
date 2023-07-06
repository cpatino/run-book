package com.carp.runbook.circuit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import reactor.core.publisher.Mono;

public interface BoredApiClient {

    @GetExchange("/api/activity")
    Mono<ResponseEntity<ActivityDto>> getActivity();
}

@Configuration
class ClientServiceConfiguration {

    @Bean
    BoredApiClient boredApiClient(HttpServiceProxyFactory httpServiceProxyFactory) {
        return httpServiceProxyFactory.createClient(BoredApiClient.class);
    }

    @Bean
    HttpServiceProxyFactory httpServiceProxyFactory() {
        WebClient webClient = WebClient.builder()
                .baseUrl("http://www.boredapi.com")
                .build();
        return HttpServiceProxyFactory.builder(WebClientAdapter.forClient(webClient))
                .build();
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
record ActivityDto(String activity, String type, Integer participants) {

    public ActivityDto() {
        this(null, null, null);
    }
}
