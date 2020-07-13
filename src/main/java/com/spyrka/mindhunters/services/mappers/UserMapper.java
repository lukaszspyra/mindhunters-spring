package com.spyrka.mindhunters.services.mappers;


import com.spyrka.mindhunters.models.User;
import com.spyrka.mindhunters.models.dto.UserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

@Autowired
private RoleMapper roleMapper;


    public UserView toView(User user) {
        UserView userView = new UserView();
        userView.setId(user.getId());
        userView.setName(user.getName());
        userView.setEmail(user.getEmail());
        userView.setRole(roleMapper.toView(user.getRole()).getName());
        return userView;
    }
}
