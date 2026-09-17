package com.nonsysncbobbal.api_gateway.routes;

import org.springframework.cloud.gateway.server.mvc.filter.CircuitBreakerFilterFunctions;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;

import static org.springframework.cloud.gateway.server.mvc.filter.FilterFunctions.setPath;
import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.uri;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;


@Configuration
public class Routes {
    // Implement the route for product service
    @Bean
    public RouterFunction<ServerResponse> productServiceRoute() {
        return GatewayRouterFunctions.route("product-service")
                .route(RequestPredicates.path("/api/product"), http())
                .before(uri("http://localhost:8081"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("product-service",
                        URI.create("forward:/fallbackRoute")))
                .build();
    }
    // Implement the route for order service
    @Bean
    public RouterFunction<ServerResponse> orderServiceRoute() {
        return GatewayRouterFunctions.route("order-service")
                .route(RequestPredicates.path("/api/order"), http())
                .before(uri("http://localhost:8087"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("order-service",
                        URI.create("forward:/fallbackRoute")))
                .build();
    }
    // Implement the route for inventory service
    @Bean
    public RouterFunction<ServerResponse> inventoryServiceRoute() {
        return GatewayRouterFunctions.route("inventory-service")
                .route(RequestPredicates.path("/api/inventory"), http())
                .before(uri("http://localhost:8088"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("inventory-service",
                        URI.create("forward:/fallbackRoute")))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> productServiceSwaggerRoute() {
        // Implement the route for product service Swagger UI and add filter to modify the request path
        return GatewayRouterFunctions.route("product-service-swagger")
                .route(RequestPredicates
                                .path("/aggregate/product-service/v3/api-docs"),
                        http())
                .before(uri("http://localhost:8081"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("product-service-swagger",
                        URI.create("forward:/fallbackRoute")))
                .filter(setPath("/v3/api-docs"))
                .build();
    }

    //Implement the route for order service Swagger UI and add filter to modify the request path
    @Bean
    public RouterFunction<ServerResponse> orderServiceSwaggerRoute() {
        return GatewayRouterFunctions.route("order-service-swagger")
                .route(RequestPredicates
                                .path("/aggregate/order-service/v3/api-docs"),
                        http())
                .before(uri("http://localhost:8087"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("order-service-swagger",
                        URI.create("forward:/fallbackRoute")))
                .filter(setPath("/v3/api-docs"))
                .build();
    }

    //Implement the route for inventory service Swagger UI and add filter to modify the request path
    @Bean
    public RouterFunction<ServerResponse> inventoryServiceSwaggerRoute() {
        return GatewayRouterFunctions.route("inventory-service-swagger")
                .route(RequestPredicates
                                .path("/aggregate/inventory-service/v3/api-docs"),
                        http())
                .before(uri("http://localhost:8088"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("inventory-service-swagger",
                        URI.create("forward:/fallbackRoute")))
                .filter(setPath("/v3/api-docs"))
                .build();
    }
    //Implement the fallback route
    @Bean
    public RouterFunction<ServerResponse> fallbackRoute() {
        return GatewayRouterFunctions.route("fallbackRoute")
                .GET("/fallbackRoute", request ->
                        ServerResponse.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("Service is unavailable. Please try again later."))
                .build();
    }
}
