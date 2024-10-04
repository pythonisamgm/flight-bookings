package com.flightbookings.flight_bookings.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuration class for setting up Swagger and OpenAPI documentation for the Flight Bookings API.
 */
@Configuration
public class SwaggerConfig {

    /**
     * Creates a custom OpenAPI configuration.
     *
     * @return a configured OpenAPI instance.
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Flight Bookings API")
                        .description("API documentation for the Flight Bookings application")
                        .version("1.0")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .servers(List.of(new Server().url("http://localhost:8080")))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
    }

    /**
     * Configures API documentation for public endpoints.
     *
     * @return a GroupedOpenApi instance for public endpoints.
     */
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public")
                .pathsToMatch(
                        "/api/v1/auth/**",
                        "/api/v1/auth/register",
                        "/api/v1/auth/login",

                        "/api/v1/bookings/create2",

                        "/api/v1/flight/{id}",
                        "/api/v1/flight/"
                )
                .pathsToExclude(
                        "/api/v1/flight/create",
                        "/api/v1/flight/byAirplaneType",
                        "/api/v1/flight/updateAvailability"
                        )
                .build();
    }

    /**
     * Configures API documentation for admin endpoints.
     *
     * @return a GroupedOpenApi instance for admin endpoints.
     */
    @Bean
    public GroupedOpenApi adminApi() {
        return GroupedOpenApi.builder()
                .group("admin")
                .pathsToMatch(
                        "/api/v1/bookings/{id}",
                        "/api/v1/bookings/",
                        "/api/v1/bookings/update/{id}",
                        "/api/v1/bookings/delete/{id}",
                        "/api/v1/bookings/all",

                        "/api/v1/flight/create",
                        "/api/v1/flight/update/{id}",
                        "/api/v1/flight/delete/{id}",
                        "/api/v1/flight/{id}/cancel",
                        "/api/v1/flight/{id}/delay",
                        "/api/v1/flight/updateAvailability",
                        "/api/v1/flight/byAirplaneType",

                        "/api/v1/passengers/create",
                        "/api/v1/passengers/{id}",
                        "/api/v1/passengers",
                        "/api/v1/passengers/update/{id}",
                        "/api/v1/passengers/delete/{id}",

                        "/api/v1/user/create",
                        "/api/v1/user/{id}",
                        "/api/v1/user/",
                        "/api/v1/user/update/{id}",
                        "/api/v1/user/delete/{id}",

                        "/api/airports"
                )
                .pathsToExclude("/api/v1/bookings/create2")
                .build();
    }

    /**
     * Configures API documentation for user endpoints.
     *
     * @return a GroupedOpenApi instance for user endpoints.
     */
    @Bean
    public GroupedOpenApi userApi() {
        return GroupedOpenApi.builder()
                .group("user")
                .pathsToMatch(
                        "/api/v1/bookings/create/{flightId}/{passengerId}/{seatName}/{userId}",
                        "/api/v1/bookings/{id}",
                        "/api/v1/bookings/",

                        "/api/v1/passengers/create",
                        "/api/v1/passengers/{id}",
                        "/api/v1/passengers",
                        "/api/v1/passengers/update/{id}",
                        "/api/v1/passengers/delete/{id}"
                )
                .pathsToExclude("/api/v1/bookings/create2", "/api/v1/bookings/all")
                .build();
    }
}
