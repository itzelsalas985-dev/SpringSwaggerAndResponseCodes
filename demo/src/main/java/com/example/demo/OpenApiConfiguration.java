
package com.example.demo;

import java.util.List;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenAPI productApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Spring Many-to-Many")
                        .description("API de productos, categorías, comentarios e imágenes")
                        .version("1.0.0"))
                .servers(List.of(
                        new Server().url("/")
                                .description("Servidor actual")
                ));
    }
}


