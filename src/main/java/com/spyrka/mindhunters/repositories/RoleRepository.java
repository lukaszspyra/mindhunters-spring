package com.spyrka.mindhunters.repositories;

import com.spyrka.mindhunters.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotNull;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findRoleByName(@NotNull String name);
}
