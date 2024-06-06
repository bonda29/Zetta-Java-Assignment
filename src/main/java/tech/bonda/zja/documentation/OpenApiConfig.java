package tech.bonda.zja.documentation;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Nikolay Boychev",
                        email = "bondableglobe10@gmail.com",
                        url = "https://bonda.tech/"
                ),
                description = "OpenApi documentation for Java Assigment",
                title = "Zetta Java Assignment API",
                version = "1.0"
        ),
        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:8080"
                ),
        }
)
public class OpenApiConfig {
}