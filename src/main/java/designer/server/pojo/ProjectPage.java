package designer.server.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName(value = "project_page")
public class ProjectPage {
  @Schema(description = "页面ID")
  @TableId(type = IdType.ASSIGN_UUID)
  private String id;

  @Schema(description = "页面名称")
  private String name;

  @Schema(description = "页面描述")
  private String description;

  @Schema(description = "创建时间")
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime ctime;

  @Schema(description = "页面 schema json")
  private String schemaJson;

  @Schema(description = "上线状态, 0: 未上线, 1: 已上线")
  private Integer status;

  @Schema(description = "是否已删除  0: 未删除, 1: 已删除")
  @TableLogic
  private Integer deleted;

  @TableField(exist = false)
  private Project project;
}
