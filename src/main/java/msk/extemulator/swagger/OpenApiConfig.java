package msk.extemulator.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Эмулятор ответов от внешних систем",
                description = "Rest эмулятор", version = "1.0.0"
        )
)
public class OpenApiConfig {
}
