package designer.server.controller;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import designer.server.dto.base.PaginationData;
import designer.server.dto.base.ResponseDTO;
import designer.server.dto.request.AddUserDTO;
import designer.server.dto.request.LoginDTO;
import designer.server.mapper.UserMapper;
import designer.server.pojo.User;
import designer.server.security.SecurityConfig;
import designer.server.service.UserService;
import designer.server.utils.SpringContext;
import designer.server.utils.WerkzeugPwdEncoder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

  @Autowired
  private UserMapper userMapper;

  @Operation(summary = "查询当前登录用户信息", security = { @SecurityRequirement(name = "Authorization") })
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

  @Operation(summary = "查询用户分页列表", security = { @SecurityRequirement(name = "Authorization") })
  @RequestMapping(value = "/pagination", method = RequestMethod.GET)
  public ResponseDTO<PaginationData<User>> pagination(
      @RequestParam(name = "current") @Parameter(description = "当前页数") Integer current,
      @RequestParam(name = "pageSize") @Parameter(description = "每页条数") Integer pageSize,
      @Nullable @RequestParam(name = "username") @Parameter(description = "用户名") String username) {
    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
    if (username != null) {
      queryWrapper.like("username", username);
    }
    queryWrapper.orderByDesc("ctime");
    Page<User> result = userMapper.selectPage(new Page<>(current, pageSize), queryWrapper);

    return ResponseDTO.pagination(PaginationData.createData(result));
  }

  @Operation(summary = "新增用户", security = { @SecurityRequirement(name = "Authorization") })
  @RequestMapping(value = "", method = RequestMethod.POST)
  public ResponseDTO<Void> add(UsernamePasswordAuthenticationToken token, @Valid @RequestBody AddUserDTO params)
      throws Throwable {
    User loginedUser = userService.getById(token.getName());
    if (!loginedUser.isRoot()) {
      return ResponseDTO.failed("非超级管理员");
    }

    User user = new User();
    user.setUsername(params.getUsername());
    WerkzeugPwdEncoder passwordEncoder = SpringContext.getBean(WerkzeugPwdEncoder.class);
    user.setPassword(passwordEncoder.encode("123456"));
    userService.save(user);
    return ResponseDTO.success();
  }

  @Operation(summary = "逻辑删除用户", security = { @SecurityRequirement(name = "Authorization") })
  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  public ResponseDTO<Void> delete(UsernamePasswordAuthenticationToken token, @PathVariable("id") String id)
      throws Throwable {
    User loginedUser = userService.getById(token.getName());
    if (!loginedUser.isRoot()) {
      return ResponseDTO.failed("非超级管理员");
    }
    userService.removeById(id);
    return ResponseDTO.success();
  }
}
