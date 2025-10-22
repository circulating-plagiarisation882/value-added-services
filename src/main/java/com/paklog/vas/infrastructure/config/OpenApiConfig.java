package com.paklog.vas.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.*;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI vasOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Value-Added Services API")
                .description("VAS operations for Paklog WMS/WES platform")
                .version("1.0.0"));
    }
}
