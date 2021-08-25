package com.spyrka.mindhunters.services.mappers;


import com.spyrka.mindhunters.models.User;
import com.spyrka.mindhunters.models.dto.UserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserMapper {

@Autowired
private RoleMapper roleMapper;

    public UserView toView(User user) {
        UserView userView = new UserView();
        userView.setId(user.getId());
        userView.setName(user.getName());
        userView.setEmail(user.getEmail());
        userView.setRole(roleMapper.toView(user.getRole()));
        return userView;
    }

    public User toEntity(UserView userview) {
        User user = new User();
        user.setId(userview.getId());
        user.setName(userview.getName());
        user.setEmail(userview.getEmail());
        user.setRole(roleMapper.toEntity(userview.getRole()));
        return user;
    }

    public List<User> toEntity(List<UserView> userViews) {
        List<User> users = new ArrayList<>();
        for (UserView view : userViews){
            users.add(toEntity(view));
        }
        return users;
    }

}
