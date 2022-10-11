package designer.server.dto.request.project;

import javax.validation.constraints.NotEmpty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddOrUpdateProjectDTO {
  @NotEmpty
  @Schema(description = "项目名")
  private String name;

  @Schema(description = "项目描述")
  private String description;
}
