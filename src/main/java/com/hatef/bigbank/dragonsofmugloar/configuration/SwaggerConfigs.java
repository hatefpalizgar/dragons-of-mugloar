package com.hatef.bigbank.dragonsofmugloar.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;


@Configuration
@EnableSwagger2
public class SwaggerConfigs {
    
    @Bean
    public Docket swaggerConfiguration() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.any())
                .apis(
                        RequestHandlerSelectors.any()
                )
                .build()
                .apiInfo(apiDetails());
    }
    
    private ApiInfo apiDetails() {
        return new ApiInfo(
                "Dragons of Mugloar API",
                "The API Provided by " + "Bigbank",
                "1.0.0",
                "Free to use",
                new springfox.documentation.service.Contact(
                        "Hatef Palizgar",
                        "https://www.github.com/hatefpalizgar",
                        "hatef.palizgar@gmail.com"
                ),
                "API License",
                "www.bigbank.ee",
                Collections.emptyList()
        );
    }
}
