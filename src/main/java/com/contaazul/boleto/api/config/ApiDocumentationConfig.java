package com.contaazul.boleto.api.config;

import io.swagger.annotations.Contact;
import io.swagger.annotations.ExternalDocs;
import io.swagger.annotations.Info;
import io.swagger.annotations.License;
import io.swagger.annotations.SwaggerDefinition;

@SwaggerDefinition(
        info = @Info(
                description = "Conta Azul Resources",
                version = "V0.0.1",
                title = "Conta Azul Resource API",
                contact = @Contact(
                   name = "Fabio Rener ", 
                   email = "fabiorener@gmail.com", 
                   url = "https://github.com/fabiorener"
                ),
                license = @License(
                   name = "Apache 2.0", 
                   url = "http://www.apache.org/licenses/LICENSE-2.0"
                )
        ),
        consumes = {"application/json", "application/xml"},
        produces = {"application/json", "application/xml"},
        schemes = {SwaggerDefinition.Scheme.HTTP, SwaggerDefinition.Scheme.HTTPS},
        externalDocs = @ExternalDocs(value = "Read This For Sure", url = "https://github.com/fabiorener")
)
public interface ApiDocumentationConfig {

}
