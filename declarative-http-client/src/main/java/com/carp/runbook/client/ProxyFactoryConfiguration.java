package com.carp.runbook.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
class ProxyFactoryConfiguration {

    @Bean
    HttpServiceProxyFactory httpServiceProxyFactory() {
        WebClient webClient = WebClient.builder()
                .baseUrl("http://localhost:8080")
                .build();
        return HttpServiceProxyFactory.builder(WebClientAdapter.forClient(webClient))
                .build();
    }
}
