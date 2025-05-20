package com.example.order.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "Order API",
                contact = @Contact(
                        name = "Sam",
                        url = "https://github.com/Samlogy"
                ),
                version = "1"
        ),
        servers = {
                @Server(
                        url = "http://localhost:4003/api/orders",
                        description = "API Product Env => Dev"
                ),
                @Server(
                        url = "http://localhost:3001",
                        description = "UI Env => Dev"
                )
        }
)
public class SwaggerConfig {
}