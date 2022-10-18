package designer.server.controller;

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
import designer.server.dto.request.AddOrUpdateRoleDTO;
import designer.server.mapper.RoleMapper;
import designer.server.pojo.Role;
import designer.server.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@Tag(name = "角色管理接口")
@RestController
@RequestMapping("/role")
public class RoleController {
  @Autowired
  private RoleService roleService;

  @Autowired
  private RoleMapper roleMapper;

  @Operation(summary = "查询角色分页列表", security = { @SecurityRequirement(name = "Authorization") })
  @RequestMapping(value = "/pagination", method = RequestMethod.GET)
  public ResponseDTO<PaginationData<Role>> pagination(
      @RequestParam(name = "current") @Parameter(description = "当前页数") Integer current,
      @RequestParam(name = "pageSize") @Parameter(description = "每页条数") Integer pageSize,
      @Nullable @RequestParam(name = "name") @Parameter(description = "角色名") String name) {
    QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
    if (name != null) {
      queryWrapper.like("name", name);
    }
    queryWrapper.orderByDesc("ctime");
    Page<Role> result = roleMapper.selectPage(new Page<>(current, pageSize), queryWrapper);

    return ResponseDTO.pagination(PaginationData.createData(result));
  }

  @Operation(summary = "查询角色信息", security = { @SecurityRequirement(name = "Authorization") })
  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public ResponseDTO<Role> getUser(@PathVariable("id") String id) {
    Role role = roleService.getById(id);
    return ResponseDTO.success(role);
  }

  @Operation(summary = "新增角色", security = { @SecurityRequirement(name = "Authorization") })
  @RequestMapping(value = "", method = RequestMethod.POST)
  public ResponseDTO<Void> add(@Valid @RequestBody AddOrUpdateRoleDTO params) throws Throwable {
    return saveOrUpdate(null, params);
  }

  @Operation(summary = "更新角色", security = { @SecurityRequirement(name = "Authorization") })
  @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
  public ResponseDTO<Void> update(@PathVariable("id") String id, @Valid @RequestBody AddOrUpdateRoleDTO params)
      throws Throwable {
    return saveOrUpdate(id, params);
  }

  @Operation(summary = "逻辑删除角色", security = { @SecurityRequirement(name = "Authorization") })
  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  public ResponseDTO<Void> delete(@PathVariable("id") String id) throws Throwable {
    roleService.removeById(id);
    return ResponseDTO.success();
  }

  private ResponseDTO<Void> saveOrUpdate(String id, AddOrUpdateRoleDTO params) {
    Role role = new Role();
    if (id != null) {
      role = roleService.getById(id);
      if (role == null) {
        return ResponseDTO.failed("非法的角色id");
      }
    }

    if (params.getName() != null) {
      role.setName(params.getName());
    }

    if (params.getDescription() != null) {
      role.setDescription(params.getDescription());
    }

    roleService.saveOrUpdate(role);
    return ResponseDTO.success();
  }
}
