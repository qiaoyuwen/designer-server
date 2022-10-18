package designer.server.dto.request;

import javax.validation.constraints.NotEmpty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddUserDTO {
  @Schema(description = "用户名")
  @NotEmpty
  private String username;
}
