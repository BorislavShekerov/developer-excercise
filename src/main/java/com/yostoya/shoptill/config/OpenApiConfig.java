package com.yostoya.shoptill.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "owner",
                        email = "yordan.y.stoyanov@gmail.com",
                        url = "https://yo-stoya.com"
                ),
                description = "REST Api Documentation",
                title = "OpenApi Specification - ShopTill",
                version = "1.0"
        ),
        servers = {
                @Server(
                        description = "Local",
                        url = "http://localhost:8080"
                ),
                @Server(
                        description = "Prod",
                        url = "https://yo-stota.com"
                )
        },
        security = {
                @SecurityRequirement(
                        name = "JWT Auth"
                )
        }
)
@SecurityScheme(
        name = "JWT Auth",
        description = "Register and login required",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
