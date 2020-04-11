package life.qyh.community.service;

import life.qyh.community.mapper.UserMapper;
import life.qyh.community.model.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import java.util.UUID;

@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    public void insertOrUpdateUser (User user) {
        User userTest = userMapper.findById(user.getAccount_id());
        if (userTest == null) {
            userMapper.insertUser(user);
        } else {
            userTest.setAvatar_url(user.getAvatar_url());
            userTest.setToken(user.getToken());
            userTest.setName(user.getName());
            userTest.setGmt_modified(System.currentTimeMillis());
            userMapper.updateUser(userTest);
        }
    }

}

