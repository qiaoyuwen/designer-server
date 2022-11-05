package designer.server.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
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
import designer.server.dto.request.AddOrUpdateProjectDTO;
import designer.server.mapper.ProjectMapper;
import designer.server.pojo.Project;
import designer.server.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@Tag(name = "项目管理接口")
@RestController
@RequestMapping("/project")
public class ProjectController {
  @Autowired
  private ProjectService projectService;

  @Autowired
  private ProjectMapper projectMapper;

  @Operation(summary = "查询项目分页列表", security = { @SecurityRequirement(name = "Authorization") })
  @RequestMapping(value = "/pagination", method = RequestMethod.GET)
  public ResponseDTO<PaginationData<Project>> pagination(
      @RequestParam(name = "current") @Parameter(description = "当前页数") Integer current,
      @RequestParam(name = "pageSize") @Parameter(description = "每页条数") Integer pageSize,
      @Nullable @RequestParam(name = "name") @Parameter(description = "项目名") String name,
      @Nullable @RequestParam(name = "ctime") @Parameter(description = "创建时间") String[] ctimes) {
    QueryWrapper<Project> queryWrapper = new QueryWrapper<>();
    if (name != null) {
      queryWrapper.like("name", name);
    }
    if (ctimes != null && ctimes.length == 2) {
      queryWrapper.between("ctime", ctimes[0] + " 00:00:00", ctimes[1] + " 23:59:59");
    }
    queryWrapper.orderByDesc("ctime");
    Page<Project> result = projectMapper.selectPage(new Page<>(current, pageSize), queryWrapper);

    return ResponseDTO.pagination(PaginationData.createData(result));
  }

  @Operation(summary = "查询项目列表", security = { @SecurityRequirement(name = "Authorization") })
  @RequestMapping(value = "", method = RequestMethod.GET)
  public ResponseDTO<List<Project>> getList(
      @Nullable @RequestParam(name = "name") @Parameter(description = "项目名") String name) {
    QueryWrapper<Project> queryWrapper = new QueryWrapper<>();
    if (name != null) {
      queryWrapper.like("name", name);
    }

    List<Project> projects = projectService.list(queryWrapper);

    return ResponseDTO.success(projects);
  }

  @Operation(summary = "查询项目信息", security = { @SecurityRequirement(name = "Authorization") })
  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public ResponseDTO<Project> getProject(@PathVariable("id") String id) {
    Project project = projectService.getById(id);
    return ResponseDTO.success(project);
  }

  @Operation(summary = "新增项目", security = { @SecurityRequirement(name = "Authorization") })
  @RequestMapping(value = "", method = RequestMethod.POST)
  public ResponseDTO<Project> add(@Valid @RequestBody AddOrUpdateProjectDTO params) throws Throwable {
    return saveOrUpdate(null, params);
  }

  @Operation(summary = "更新项目", security = { @SecurityRequirement(name = "Authorization") })
  @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
  public ResponseDTO<Project> update(@PathVariable("id") String id, @Valid @RequestBody AddOrUpdateProjectDTO params)
      throws Throwable {
    return saveOrUpdate(id, params);
  }

  @Operation(summary = "逻辑删除项目", security = { @SecurityRequirement(name = "Authorization") })
  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  public ResponseDTO<Void> delete(@PathVariable("id") String id) throws Throwable {
    projectService.removeById(id);
    return ResponseDTO.success();
  }

  private ResponseDTO<Project> saveOrUpdate(String id, AddOrUpdateProjectDTO params) {
    Project project = new Project();
    if (id != null) {
      project = projectService.getById(id);
      if (project == null) {
        return ResponseDTO.failed(project, "非法的项目id");
      }
    }

    if (params.getName() != null) {
      project.setName(params.getName());
    }

    if (params.getDescription() != null) {
      project.setDescription(params.getDescription());
    }

    if (params.getMenuConfig() != null) {
      project.setMenuConfig(params.getMenuConfig());
    }

    projectService.saveOrUpdate(project);
    return ResponseDTO.success(project);
  }
}
