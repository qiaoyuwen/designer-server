package designer.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import designer.server.mapper.UserMapper;
import designer.server.pojo.User;
import designer.server.service.UserService;

import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
