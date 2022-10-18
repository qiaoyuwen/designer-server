package designer.server.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddOrUpdateRoleDTO {
  @Schema(description = "角色名")
  private String name;

  @Schema(description = "角色描述")
  private String description;
}
