package calendly.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Calendly Availability API")
                        .version("1.0")
                        .description("This API allows users to manage their availability schedules and provides functionality to retrieve overlapping availability between two users." +
                                " It supports both daily and weekly schedule types, making it easy to create and query available times."));
    }
}
