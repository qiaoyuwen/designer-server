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

import designer.server.dto.base.PaginationData;
import designer.server.dto.base.ResponseDTO;
import designer.server.dto.request.AddOrUpdateProjectDTO;
import designer.server.pojo.Project;
import designer.server.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@Tag(name = "项目管理接口")
@RestController
@RequestMapping("/project")
public class ProjectController {
  @Autowired
  private ProjectService projectService;

  @Operation(summary = "查询项目分页列表", security = { @SecurityRequirement(name = "Authorization") })
  @RequestMapping(value = "/pagination", method = RequestMethod.GET)
  public ResponseDTO<PaginationData<Project>> pagination(@RequestParam(name = "current") Integer current,
      @RequestParam(name = "pageSize") Integer pageSize,
      @Nullable @RequestParam(name = "name") String name) {
    String sql = "limit " + (current - 1) * pageSize + "," + pageSize;
    QueryWrapper<Project> queryWrapper = new QueryWrapper<>();
    if (name != null) {
      queryWrapper.like("name", name);
    }
    queryWrapper.eq("deleted", 0);
    int total = projectService.count(queryWrapper);

    queryWrapper.orderByDesc("ctime");
    queryWrapper.last(sql);
    List<Project> projects = projectService.list(queryWrapper);

    PaginationData<Project> paginationData = new PaginationData<>();
    paginationData.setCurrent(current);
    paginationData.setPageSize(pageSize);
    paginationData.setTotal(total);
    paginationData.setList(projects);

    return ResponseDTO.pagination(paginationData);
  }

  @Operation(summary = "查询项目信息", security = { @SecurityRequirement(name = "Authorization") })
  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public ResponseDTO<Project> getUser(@PathVariable("id") String id) {
    Project project = projectService.getById(id);
    return ResponseDTO.success(project);
  }

  @Operation(summary = "新增项目", security = { @SecurityRequirement(name = "Authorization") })
  @RequestMapping(value = "", method = RequestMethod.POST)
  public ResponseDTO<Void> add(@Valid @RequestBody AddOrUpdateProjectDTO params) throws Throwable {
    return saveOrUpdate(null, params);
  }

  @Operation(summary = "更新项目", security = { @SecurityRequirement(name = "Authorization") })
  @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
  public ResponseDTO<Void> update(@PathVariable("id") String id, @Valid @RequestBody AddOrUpdateProjectDTO params)
      throws Throwable {
    return saveOrUpdate(id, params);
  }

  @Operation(summary = "删除项目", security = { @SecurityRequirement(name = "Authorization") })
  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  public ResponseDTO<Void> delete(@PathVariable("id") String id) throws Throwable {
    Project project = projectService.getById(id);
    if (project != null) {
      project.setDeleted(1);
      projectService.updateById(project);
    }
    return ResponseDTO.success();
  }

  private ResponseDTO<Void> saveOrUpdate(String id, AddOrUpdateProjectDTO params) {
    Project project = new Project();
    if (id != null) {
      project = projectService.getById(id);
      if (project == null) {
        return ResponseDTO.failed("非法的项目id");
      }
    }

    if (params.getName() != null) {
      project.setName(params.getName());
    }

    if (params.getDescription() != null) {
      project.setDescription(params.getDescription());
    }

    projectService.saveOrUpdate(project);
    return ResponseDTO.success();
  }
}
