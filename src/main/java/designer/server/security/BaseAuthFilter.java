package designer.server.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class BaseAuthFilter extends BasicAuthenticationFilter {

  private final SecretKey secretKey;

  public BaseAuthFilter(AuthenticationManager authenticationManager, SecretKey secretKey) {
    super(authenticationManager);
    this.secretKey = secretKey;
  }

  @SneakyThrows
  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain chain) {
    String token = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (token != null && token.startsWith("Bearer ")) {
      try {
        token = token.substring(7);
        Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
        String userId = claims.getBody().getSubject();
        if (userId != null && !userId.isEmpty()) {
          UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userId, null,
              null);
          SecurityContextHolder.getContext().setAuthentication(authentication);
        }
      } catch (JwtException e) {
        log.info("BaseAuthFilter parse token error: {}", e.getMessage());
      }
    }

    super.doFilterInternal(request, response, chain);
  }
}
