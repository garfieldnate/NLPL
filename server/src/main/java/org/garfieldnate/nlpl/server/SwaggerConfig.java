package org.garfieldnate.nlpl.server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Arrays;
import java.util.Collections;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                                                      .apis(RequestHandlerSelectors.basePackage(
                                                          "org.garfieldnate.nlpl.server"))
                                                      .paths(PathSelectors.ant("/api/v1/*"))
                                                      .apis(RequestHandlerSelectors.any())
                                                      .build()
                                                      .apiInfo(apiInfo())
                                                      .useDefaultResponseMessages(false)
                                                      .globalResponseMessage(RequestMethod.POST, Arrays.asList(
                                                          new ResponseMessageBuilder().code(500)
                                                                                      .message("Server error")
                                                                                      //TODO: what is this model?
                                                                                      .responseModel(new ModelRef(
                                                                                          "Error"))
                                                                                      .build(),
                                                          new ResponseMessageBuilder().code(403)
                                                                                      .message("Forbidden")
                                                                                      .build()
                                                      ));
    }


    private ApiInfo apiInfo() {
        return new ApiInfo(
            "NLPL API",
            "API for NLP processing of texts for foreign language learning",
            "0.0.1",
            "TODO.net/tos.html",
            new Contact("Nathan Glenn", "www.TODO.net", "garfieldnate@gmail.com"),
            "Apache 2.0",
            "http://www.apache.org/licenses/LICENSE-2.0.html",
            Collections.emptyList()
        );
    }
}
