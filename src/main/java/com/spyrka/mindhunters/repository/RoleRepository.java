package com.spyrka.mindhunters.repository;

import com.spyrka.mindhunters.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotNull;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findRoleByName(@NotNull String name);
}
