package com.bookstore.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI bookstoreOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Bookstore API")
                        .description("RESTful API for managing books and authors in a bookstore, developed by vaibhav at elevate labs")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Elevate labs")
                                .email("https://github.com/vaibhav-rm/Bookstore-Api")
                                .url("https://github.com/vaibhav-rm/Bookstore-Api"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")));
    }
}
