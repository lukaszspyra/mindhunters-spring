package com.spyrka.mindhunters.service;


import com.spyrka.mindhunters.model.User;
import com.spyrka.mindhunters.model.dto.UserView;
import com.spyrka.mindhunters.repository.RoleRepository;
import com.spyrka.mindhunters.repository.UserRepository;
import com.spyrka.mindhunters.service.mapper.UserMapper;
import com.spyrka.mindhunters.service.validator.UserInputValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
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


    public void setRole(String userId, String role) {
        final Long longId = userInputValidator.stringToLongConverter(userId);

        if (longId < 0) {
            return;
        }

        Optional<User> optionalUserById = userRepository.findById(longId);

        if (optionalUserById.isEmpty() || "SUPER_ADMIN".equalsIgnoreCase(optionalUserById.get().getRole().getName())) {
            return;
        }

        User userById = optionalUserById.get();
        userById.setRole(roleRepository.findRoleByName(role).get());
        userRepository.save(userById);
    }

    public List<UserView> showUsers() {

        return userRepository.findAll()
                .stream()
                .map(user -> userMapper.toView(user))
                .collect(Collectors.toList());
    }

}
