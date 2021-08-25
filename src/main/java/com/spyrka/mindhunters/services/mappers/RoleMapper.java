package com.spyrka.mindhunters.services.mappers;


import com.spyrka.mindhunters.models.Role;
import com.spyrka.mindhunters.models.dto.RoleView;
import org.springframework.stereotype.Service;

@Service
public class RoleMapper {

    public RoleView toView(Role role) {

        RoleView roleView = new RoleView();
        roleView.setId(role.getId());
        roleView.setName(role.getName());
        return roleView;
    }

    public Role toEntity(RoleView roleView) {

        Role role = new Role();
        role.setId(roleView.getId());
        role.setName(roleView.getName());
        return role;
    }
}
