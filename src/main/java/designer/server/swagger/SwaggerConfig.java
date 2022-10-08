package designer.server.swagger;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Designer", version = "1.0", description = "Designer Api 文档"), externalDocs = @ExternalDocumentation(description = "springdoc文档地址", url = "https://springdoc.org/#getting-started"))
@SecurityScheme(type = SecuritySchemeType.HTTP, name = "JWT", scheme = "bearer", in = SecuritySchemeIn.HEADER)
public class SwaggerConfig {
  @Bean
  public GroupedOpenApi backendApi() {
    return GroupedOpenApi.builder()
        .group("后台接口")
        .pathsToMatch("/**")
        .build();
  }
}
