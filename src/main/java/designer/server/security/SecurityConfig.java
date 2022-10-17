package designer.server.security;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import javax.crypto.SecretKey;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

@Configuration
@ConditionalOnWebApplication
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  @Value("${jwt.secret:c9d0799fa21962386d69fb20f40a8fd5e9bfea4ae07de0aa55e262a0f46f96a0}")
  private String jwtSecret;

  @Bean
  public SecretKey getSecretKey() throws UnsupportedEncodingException {
    return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
  }

  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {
    httpSecurity.cors()
        .and()
        .csrf()
        .disable()
        .authorizeRequests()
        .antMatchers("/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/user/login",
            "/project",
            "/project/**")
        .permitAll()
        .anyRequest().authenticated()
        .and()
        .addFilter(new BaseAuthFilter(authenticationManager(), getSecretKey()))
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    ;
  }
}
