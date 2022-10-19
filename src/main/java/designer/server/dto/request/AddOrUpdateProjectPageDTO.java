package designer.server.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddOrUpdateProjectPageDTO {
  @Schema(description = "页面名称")
  private String name;

  @Schema(description = "页面描述")
  private String description;

  @Schema(description = "页面schema json")
  private String schemaJson;

  @Schema(description = "项目id")
  private String projectId;

  @Schema(description = "上线状态, 0: 未上线, 1: 已上线")
  private Integer status;
}
