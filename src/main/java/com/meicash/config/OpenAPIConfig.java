package com.meicash.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "Especificação OpenApi - MeiCash API",
                description = "Documentação da API do Meicash Server | Mandacaru.dev - #Time 05 do Módulo Juazeiro",
                version = "1.0"
        ),
        servers = {
                @Server(
                        description = "Ambiente Padrão",
                        url = "/"
                ),
                @Server(
                        description = "Ambiente de Produção",
                        url = "http://20.163.168.29:8080"
                ),
                @Server(
                        description = "Ambiente de Local",
                        url = "http://localhost:8080"
                )
        },
        security = {
                @SecurityRequirement(
                        name = "bearerAuth"
                )
        }
)

@SecurityScheme(
        name = "bearerAuth",
        description = "Token JWT para autenticação",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenAPIConfig {
}
