package com.spyrka.mindhunters.repository;


import com.spyrka.mindhunters.model.Drink;
import com.spyrka.mindhunters.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(@NotNull @Email String email);

    List<Drink> findFavouritesList(String email, Pageable pageable);

    int countPagesFindFavouritesList(String email);

}
