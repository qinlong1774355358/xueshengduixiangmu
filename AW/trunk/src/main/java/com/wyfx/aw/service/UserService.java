package com.wyfx.aw.service;

import com.wyfx.aw.entity.User;

public interface UserService {

    User findByNameAndPwd(String uName, String pwd);
}
