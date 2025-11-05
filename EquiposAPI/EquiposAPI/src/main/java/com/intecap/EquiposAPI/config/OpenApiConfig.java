package com.intecap.EquiposAPI.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
  @Bean
  public OpenAPI api() {
    return new OpenAPI().info(new Info()
      .title("Resultados de Fútbol – API simple")
      .version("1.0.0")
      .description("API mínima con Basic Auth. Si configuras FOOTBALL_API_KEY consulta API-FOOTBALL; si no, responde mock."));
  }
}
