package com.egbas.HNG_stage_two.config;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@OpenAPIDefinition(
        info = @Info(
                title = "MonieFlex App",
                description = "APIs for MonieFlex Fintech App",
                version = "1.0",
                contact = @Contact(
                        name = "SQ020",
                        email = "monieflexapp@gmail.com",
                        url = "https://github.com/decadev-sq020"
                ),
                license = @License(
                        name = "MonieFlex Application",
                        url = "https://github.com/decadev-sq020"
                )
        ),
        externalDocs = @ExternalDocumentation(
                description = "MonieFlex RESTful API Documentation",
                url = "https://github.com/egbas"
        )
)
public class Swagger {

}
