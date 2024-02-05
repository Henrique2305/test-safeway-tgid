package br.com.safeway.safeway.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI openAPIDocumentation() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("SafeWay API")
                                .description("API do sistema SafeWay")
                                .version("v1.0")
                                .contact(new Contact()
                                        .name("Henrique Ferreira")
                                        .email("henriquesilvaferreira2305@gmail.com")
                                )


                );
    }
}
