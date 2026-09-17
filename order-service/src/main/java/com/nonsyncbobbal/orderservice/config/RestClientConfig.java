package com.nonsyncbobbal.orderservice.config;

import com.nonsyncbobbal.orderservice.client.InventoryClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.http.client.ClientHttpRequestFactoryBuilder;
import org.springframework.boot.http.client.HttpClientSettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.time.Duration;

@Configuration
public class RestClientConfig {

    @Value("${inventory.service.url}")
    private String inventoryUrl;

    @Bean
    public InventoryClient inventoryClient() {
        RestClient restClient = RestClient.builder()
                                            .baseUrl(inventoryUrl)
                                            .requestFactory(getClientRequestFactory())
                                            .build();
        var restClientAdapter = RestClientAdapter.create(restClient);
        var httpServiceProxyFactory = HttpServiceProxyFactory.builderFor(restClientAdapter).build();
        return httpServiceProxyFactory.createClient(InventoryClient.class);
    }

    private ClientHttpRequestFactory getClientRequestFactory() {
        HttpClientSettings settings = HttpClientSettings.defaults()
                .withConnectTimeout(Duration.ofSeconds(5))
                .withReadTimeout(Duration.ofSeconds(5));
        return ClientHttpRequestFactoryBuilder.detect().build(settings);
    }
}
