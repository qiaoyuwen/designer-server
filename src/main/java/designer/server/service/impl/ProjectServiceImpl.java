package designer.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import designer.server.mapper.ProjectMapper;
import designer.server.pojo.Project;
import designer.server.service.ProjectService;

import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements ProjectService {
}
