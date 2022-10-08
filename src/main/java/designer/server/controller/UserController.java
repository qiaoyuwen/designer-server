package designer.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import designer.server.dto.base.ResponseDTO;
import designer.server.pojo.User;
import designer.server.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "用户管理接口")
@RestController
@RequestMapping("/user")
public class UserController {
  @Autowired
  private UserService userService;

  @Operation(summary = "获取用户信息")
  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public ResponseDTO<User> getUser(@PathVariable(name = "id") @Parameter(description = "用户ID") String id) {
    User user = userService.getById(id);

    return new ResponseDTO<>(user);
  }
}
