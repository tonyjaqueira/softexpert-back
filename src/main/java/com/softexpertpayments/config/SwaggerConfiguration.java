package com.softexpertpayments.config;


import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    public static final String URL = "http://www.softexpert.com";

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("SoftExpert")
                        .description("API para teste de seleção da empresa SoftExpert")
                        .version("1.0")
                        .termsOfService("Termo de uso: Open Source")
                        .license(new License()
                                .name("Apache 2.0")
                                .url(URL)
                        )
                ).externalDocs(
                        new ExternalDocumentation()
                                .description("SoftExpert")
                                .url(URL));
    }

}
