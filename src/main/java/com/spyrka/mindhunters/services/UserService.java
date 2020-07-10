package com.spyrka.mindhunters.services;

import com.spyrka.mindhunters.models.Drink;
import com.spyrka.mindhunters.models.User;
import com.spyrka.mindhunters.models.dto.FullDrinkView;
import com.spyrka.mindhunters.models.dto.UserGoogleView;
import com.spyrka.mindhunters.models.dto.UserView;
import com.spyrka.mindhunters.repositories.DrinkRepository;
import com.spyrka.mindhunters.repositories.RoleRepository;
import com.spyrka.mindhunters.repositories.UserRepository;
import com.spyrka.mindhunters.services.mappers.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class.getName());

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserMapper userMapper;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    DrinkRepository drinkRepository;


    @Transactional
    public void save(User user) {
        userRepository.save(user);
    }

    public UserView getUserById(Long userId) {
        User foundUser = userRepository.findById(userId).orElseThrow();
        if (foundUser == null) {
            return null;
        }
        return userMapper.toView(foundUser);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User create(UserGoogleView userGoogleView) {
        User user = new User();
        user.setName(userGoogleView.getName());
        user.setEmail(userGoogleView.getEmail());
        user.setRole(roleRepository.findRoleByName("USER").orElseThrow());
        LOGGER.info("User {} created in role as {}", user.getEmail(), user.getRole());
        save(user);
        return user;
    }

    public UserView login(UserGoogleView userGoogleView) {
        User user = userRepository.findByEmail(userGoogleView.getEmail()).orElseGet(() -> create(userGoogleView));
        LOGGER.info("User {} logged in as {}", user.getEmail(), user.getRole());
        return userMapper.toView(user);
    }

    public void saveOrDeleteFavourite(String email, String drinkId) {

        Drink drink = drinkRepository.findById(Long.parseLong(drinkId)).orElseThrow();
        User user = userRepository.findByEmail(email).orElseThrow();

        List<Drink> favouriteDrinks = user.getDrinks();

        if (!favouriteDrinks.contains(drink)) {
            user.getDrinks().add(drink);
            LOGGER.info("User email = {} added drink ID = {} to favourites", email, drinkId);

        } else {
            user.getDrinks().remove(drink);
            LOGGER.info("User email = {} deleted drink ID = {} from favourites", email, drinkId);

        }

    }

/*    public List<FullDrinkView> favouritesList(String email) {

        User user = userRepository.findByEmail(email).orElseThrow();

        return fullDrinkMapper.toView(user.getDrinks());

    }

    public List<FullDrinkView> favouritesList(String email, int pageNumber) {
        int startPosition = (pageNumber - 1) * PAGE_SIZE;
        int endPosition = PAGE_SIZE;

        List<Drink> drinks = userRepository.findFavouritesList(email, startPosition, endPosition);

        return fullDrinkMapper.toView(drinks);

    }

    public int countPagesFavouritesList(String email) {
        int maxPageNumber = userRepository.countPagesFindFavouritesList(email);

        return maxPageNumber;

    }

    public int getMaxPageNumber(String querySize) {
        return (int) Math.ceil((Double.valueOf(querySize) / PAGE_SIZE));
    }*/

}
