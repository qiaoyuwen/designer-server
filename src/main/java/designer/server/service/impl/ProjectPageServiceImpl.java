package designer.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import designer.server.mapper.ProjectPageMapper;
import designer.server.pojo.ProjectPage;
import designer.server.service.ProjectPageService;

import org.springframework.stereotype.Service;

@Service
public class ProjectPageServiceImpl extends ServiceImpl<ProjectPageMapper, ProjectPage> implements ProjectPageService {
}
