package designer.server.controller;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import designer.server.dto.base.ResponseDTO;
import designer.server.dto.request.LoginDTO;
import designer.server.pojo.User;
import designer.server.security.SecurityConfig;
import designer.server.service.UserService;
import designer.server.utils.SpringContext;
import designer.server.utils.WerkzeugPwdEncoder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Tag(name = "用户管理接口")
@RestController
@RequestMapping("/user")
public class UserController {
  @Autowired
  private UserService userService;

  @Operation(summary = "获取当前登录用户信息", security = { @SecurityRequirement(name = "Authorization") })
  @RequestMapping(value = "", method = RequestMethod.GET)
  public ResponseDTO<User> getUser(UsernamePasswordAuthenticationToken token) {
    User user = userService.getById(token.getName());
    return ResponseDTO.success(user);
  }

  @Operation(summary = "登录")
  @RequestMapping(value = "/login", method = RequestMethod.POST)
  public ResponseDTO<Void> login(@Valid @RequestBody LoginDTO params, HttpServletResponse response) throws IOException {
    log.info("user {} try to login", params.getUsername());
    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("username", params.getUsername());
    User user = userService.getOne(queryWrapper);
    if (user != null) {
      WerkzeugPwdEncoder pwdEncoder = new WerkzeugPwdEncoder();
      if (pwdEncoder.matches(params.getPassword(), user.getPassword())) {
        SecurityConfig securityConfig = SpringContext.getBean(SecurityConfig.class);
        String token = Jwts.builder()
            .signWith(securityConfig.getSecretKey(), SignatureAlgorithm.HS256)
            .setSubject(user.getId())
            .setExpiration(new Date(System.currentTimeMillis() + 86400L * 1000 * 365))
            .compact();
        response.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        log.info("user {} login succeed", params.getUsername());
        return ResponseDTO.success();
      }
    }
    // 认证失败
    log.info("user {} login failed", params.getUsername());
    return ResponseDTO.failed("用户名密码错误");
  }
}
