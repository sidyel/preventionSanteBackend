package com.sante.reventionplatform.conf;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Plateforme Santé Prévention")
                        .version("1.0.0")
                        .description("API REST pour la plateforme de sensibilisation et prévention en santé")
                        .contact(new Contact()
                                .name("Équipe Développement")
                                .email("dev@sante-prevention.com")));
    }
}