package designer.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import designer.server.mapper.RoleMapper;
import designer.server.pojo.Role;
import designer.server.service.RoleService;

import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
}
