package com.carp.sns;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.services.sns.SnsClient;

import java.net.URI;

import static software.amazon.awssdk.regions.Region.US_EAST_1;

@Configuration
class SnsClientConfiguration {

    @Bean
    public SnsClient snsClient() {
        return SnsClient.builder()
                .endpointOverride(URI.create("http://localhost:4566"))
                .region(US_EAST_1)
                .credentialsProvider(StaticCredentialsProvider.create(new AwsCredentials() {
                    @Override
                    public String accessKeyId() {
                        return "localstack";
                    }

                    @Override
                    public String secretAccessKey() {
                        return "localstack";
                    }
                }))
                .build();
    }
}
