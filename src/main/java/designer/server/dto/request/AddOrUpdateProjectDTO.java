package designer.server.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddOrUpdateProjectDTO {
  @Schema(description = "项目名")
  private String name;

  @Schema(description = "项目描述")
  private String description;

  @Schema(description = "项目菜单配置")
  private String menuConfig;
}
