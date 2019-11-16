package com.tangwh.service;

import com.tangwh.mapper.UserMapper;
import com.tangwh.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;
    @Override
    public User queryByname(String name) {
        return userMapper.queryByname(name);
    }
}
