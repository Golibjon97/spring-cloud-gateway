//package com.api.gateway.epam.spring_cloud_gateway.config;
//
//import org.springframework.cloud.gateway.route.RouteLocator;
//import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class SpringCloudConfig {
//
//    @Bean
//    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
//        return builder.routes()
//                .route(r -> r.path("/api/v1/resources/**")
//                        .uri("lb://RESOURCE-SERVICE"))
//
//                .route(r -> r.path("/api/v1/song/**")
//                        .uri("lb://SONG-SERVICE"))
//                .build();
//    }
//}
