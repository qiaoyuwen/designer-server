package designer.server.mapper;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.toolkit.Constants;

import designer.server.pojo.ProjectPage;

public interface ProjectPageMapper extends BaseMapper<ProjectPage> {
  IPage<ProjectPage> getPagination(@Param("page") Page<ProjectPage> page,
      @Param(Constants.WRAPPER) Wrapper<ProjectPage> wrapper);
}
