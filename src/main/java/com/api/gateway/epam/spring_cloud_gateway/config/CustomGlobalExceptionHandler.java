package com.api.gateway.epam.spring_cloud_gateway.config;

import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Component
public class CustomGlobalExceptionHandler implements ErrorWebExceptionHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String message = "An unexpected error occurred";

        if (ex instanceof ResponseStatusException e) {
            status = HttpStatus.valueOf(e.getStatusCode().value());
            message = e.getReason() != null ? e.getReason() : e.getMessage();
        } else if (ex.getMessage().contains("No instances available")) {
            status = HttpStatus.SERVICE_UNAVAILABLE;
            message = "Service is unavailable. Please try again later.";
        } else if (ex.getMessage().contains("404")) {
            status = HttpStatus.NOT_FOUND;
            message = "Route not found. Please check your request.";
        }

        exchange.getResponse().setStatusCode(status);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

        String errorResponse = String.format("{\"status\": %d, \"message\": \"%s\"}", status.value(), message);
        DataBuffer buffer = exchange.getResponse()
                .bufferFactory()
                .wrap(errorResponse.getBytes(StandardCharsets.UTF_8));

        return exchange.getResponse().writeWith(Mono.just(buffer));
    }
}
