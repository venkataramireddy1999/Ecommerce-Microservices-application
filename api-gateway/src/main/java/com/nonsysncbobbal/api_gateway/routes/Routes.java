package com.nonsysncbobbal.api_gateway.routes;

import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.uri;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;


@Configuration
public class Routes {
    
    @Bean
    public RouterFunction<ServerResponse> productServiceRoute() {
        return GatewayRouterFunctions.route("product-service")
                                    .route(RequestPredicates.path("/api/product"), http())
                                    .before(uri("http://localhost:8081"))
                                    .build(); // Implement the route for product service
    }
    @Bean
    public RouterFunction<ServerResponse> orderServiceRoute() {
        return GatewayRouterFunctions.route("order-service")
                                    .route(RequestPredicates.path("/api/order"), http())
                                    .before(uri("http://localhost:8087"))
                                    .build(); // Implement the route for order service
    }

    @Bean
    public RouterFunction<ServerResponse> inventoryServiceRoute() {
        return GatewayRouterFunctions.route("inventory-service")
                                    .route(RequestPredicates.path("/api/inventory"), http())
                                    .before(uri("http://localhost:8088"))
                                    .build(); // Implement the route for inventory service
    }
}
