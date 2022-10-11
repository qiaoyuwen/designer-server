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
import designer.server.dto.request.AddOrUpdateProjectPageDTO;
import designer.server.pojo.ProjectPage;
import designer.server.service.ProjectPageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@Tag(name = "项目页面管理接口")
@RestController
@RequestMapping("/project_page")
public class ProjectPageController {
  @Autowired
  private ProjectPageService projectPageService;

  @Operation(summary = "查询项目页面分页列表", security = { @SecurityRequirement(name = "Authorization") })
  @RequestMapping(value = "/pagination", method = RequestMethod.GET)
  public ResponseDTO<PaginationData<ProjectPage>> pagination(
      @RequestParam(name = "current") @Parameter(description = "当前页数") Integer current,
      @RequestParam(name = "pageSize") @Parameter(description = "每页条数") Integer pageSize,
      @Nullable @RequestParam(name = "name") @Parameter(description = "页面名称") String name) {
    String sql = "limit " + (current - 1) * pageSize + "," + pageSize;
    QueryWrapper<ProjectPage> queryWrapper = new QueryWrapper<>();
    if (name != null) {
      queryWrapper.like("name", name);
    }
    queryWrapper.eq("deleted", 0);
    int total = projectPageService.count(queryWrapper);
    queryWrapper.last(sql);
    List<ProjectPage> projectPages = projectPageService.list(queryWrapper);

    PaginationData<ProjectPage> paginationData = new PaginationData<>();
    paginationData.setCurrent(current);
    paginationData.setPageSize(pageSize);
    paginationData.setTotal(total);
    paginationData.setList(projectPages);

    return ResponseDTO.pagination(paginationData);
  }

  @Operation(summary = "查询项目页面信息", security = { @SecurityRequirement(name = "Authorization") })
  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public ResponseDTO<ProjectPage> getUser(@PathVariable("id") @Parameter(description = "页面ID") String id) {
    ProjectPage projectpPage = projectPageService.getById(id);
    return ResponseDTO.success(projectpPage);
  }

  @Operation(summary = "新增项目页面", security = { @SecurityRequirement(name = "Authorization") })
  @RequestMapping(value = "", method = RequestMethod.POST)
  public ResponseDTO<Void> add(@Valid @RequestBody AddOrUpdateProjectPageDTO params) throws Throwable {
    return saveOrUpdate(null, params);
  }

  @Operation(summary = "更新项目页面", security = { @SecurityRequirement(name = "Authorization") })
  @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
  public ResponseDTO<Void> update(@PathVariable("id") @Parameter(description = "页面ID") String id,
      @Valid @RequestBody AddOrUpdateProjectPageDTO params)
      throws Throwable {
    return saveOrUpdate(id, params);
  }

  @Operation(summary = "删除项目页面", security = { @SecurityRequirement(name = "Authorization") })
  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  public ResponseDTO<Void> delete(@PathVariable("id") @Parameter(description = "页面ID") String id) throws Throwable {
    ProjectPage projectPage = projectPageService.getById(id);
    if (projectPage != null) {
      projectPage.setDeleted(1);
      projectPageService.updateById(projectPage);
    }
    return ResponseDTO.success();
  }

  private ResponseDTO<Void> saveOrUpdate(String id, AddOrUpdateProjectPageDTO params) {
    ProjectPage projectPage = new ProjectPage();
    if (id != null) {
      projectPage = projectPageService.getById(id);
      if (projectPage == null) {
        return ResponseDTO.failed("非法的项目页面id");
      }
    }

    if (params.getName() != null) {
      projectPage.setName(params.getName());
    }

    if (params.getDescription() != null) {
      projectPage.setDescription(params.getDescription());
    }

    if (params.getSchemaJson() != null) {
      projectPage.setSchemaJson(params.getSchemaJson());
    }

    if (params.getStatus() != null) {
      projectPage.setStatus(params.getStatus());
    }

    projectPageService.saveOrUpdate(projectPage);
    return ResponseDTO.success();
  }
}