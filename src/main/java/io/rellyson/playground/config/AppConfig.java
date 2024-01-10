package io.rellyson.playground.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public OpenAPI applicationOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Playground Application")
                        .description("Java Spring Proof of Concept Application.")
                        .license(new License().name("MIT License").url("https://opensource.org/license/mit/"))
                );
    }
}
