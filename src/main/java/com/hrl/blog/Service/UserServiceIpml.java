package com.hrl.blog.Service;

import com.hrl.blog.Dao.UserRepository;
import com.hrl.blog.Util.MD5Utils;
import com.hrl.blog.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceIpml implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User checkUser(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username, MD5Utils.code(password));
        return user;
    }
}
