package com.spyrka.mindhunters.services;


import com.spyrka.mindhunters.models.User;
import com.spyrka.mindhunters.models.dto.UserView;
import com.spyrka.mindhunters.repositories.RoleRepository;
import com.spyrka.mindhunters.repositories.UserRepository;
import com.spyrka.mindhunters.services.mappers.UserMapper;
import com.spyrka.mindhunters.services.validator.UserInputValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AdminUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserInputValidator userInputValidator;


    public boolean setAdminRole(String userId) {

        final Long longId = userInputValidator.stringToLongConverter(userId);

        if (longId < 0) {
            return false;
        }

        User userById = userRepository.findUserById(longId).get();

        if ("SUPER_ADMIN".equalsIgnoreCase(userById.getRole().getName())) {
            return false;
        }
        userById.setRole(roleRepository.findRoleByName("ADMIN").get());
        User adminUser = userRepository.update(userById);

        return adminUser.getRole().getName().equalsIgnoreCase("ADMIN");
    }

    public boolean removeAdminRole(String adminId) {
        final Long longId = userInputValidator.stringToLongConverter(adminId);

        if (longId < 0) {
            return false;
        }

        User userById = userRepository.findUserById(longId).orElseThrow(IllegalArgumentException::new);

        if ("SUPER_ADMIN".equalsIgnoreCase(userById.getRole().getName())) {
            return false;
        }
        userById.setRole(roleRepository.findRoleByName("USER").get());
        User ordinaryUser = userRepository.update(userById);

        return ordinaryUser.getRole().getName().equalsIgnoreCase("USER");

    }


    public List<UserView> showUsers() {

        return userRepository.findAll()
                .stream()
                .map(user -> userMapper.toView(user))
                .collect(Collectors.toList());
    }


}
