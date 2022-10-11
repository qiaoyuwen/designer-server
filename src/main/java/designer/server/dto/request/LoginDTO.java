package designer.server.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class LoginDTO {
  @NotEmpty
  @Schema(description = "用户名")
  private String username;

  @NotEmpty
  @Schema(description = "密码")
  private String password;
}
