package com.hrl.blog.Service;

import com.hrl.blog.entities.User;

public interface UserService {
    User checkUser(String username,String password);
}
