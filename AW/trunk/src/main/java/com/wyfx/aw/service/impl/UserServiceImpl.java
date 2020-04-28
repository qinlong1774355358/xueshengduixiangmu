package com.wyfx.aw.service.impl;

import com.wyfx.aw.dao.UserMapper;
import com.wyfx.aw.entity.User;
import com.wyfx.aw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByNameAndPwd(String userName, String pwd) {
        User user= userMapper.selectByNameAndPwd(userName,pwd);
        return user;
    }
}
