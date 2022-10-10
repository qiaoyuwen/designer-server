package designer.server.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import designer.server.dto.base.PaginationData;
import designer.server.dto.base.ResponseDTO;
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

  @Operation(summary = "获取项目分页列表", security = { @SecurityRequirement(name = "Authorization") })
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
    queryWrapper.last(sql);
    List<Project> userList = projectService.list(queryWrapper);

    PaginationData<Project> paginationData = new PaginationData<>();
    paginationData.setCurrent(current);
    paginationData.setPageSize(pageSize);
    paginationData.setTotal(total);
    paginationData.setList(userList);

    return ResponseDTO.pagination(paginationData);
  }
}
