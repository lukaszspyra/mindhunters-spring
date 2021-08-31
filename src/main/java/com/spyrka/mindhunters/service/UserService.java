package com.spyrka.mindhunters.service;

import com.spyrka.mindhunters.model.Drink;
import com.spyrka.mindhunters.model.User;
import com.spyrka.mindhunters.model.dto.FullDrinkView;
import com.spyrka.mindhunters.model.dto.UserGoogleView;
import com.spyrka.mindhunters.model.dto.UserView;
import com.spyrka.mindhunters.repository.DrinkRepository;
import com.spyrka.mindhunters.repository.RoleRepository;
import com.spyrka.mindhunters.repository.UserRepository;
import com.spyrka.mindhunters.service.mapper.FullDrinkMapper;
import com.spyrka.mindhunters.service.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class.getName());
    private static final Integer PAGE_SIZE = 20;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserMapper userMapper;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    DrinkRepository drinkRepository;

    @Autowired
    FullDrinkMapper fullDrinkMapper;


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

    public void updateUserFavouriteDrinks(String email, String drinkId) {

        Drink drink = drinkRepository.findById(Long.parseLong(drinkId)).orElseThrow();
        User user = userRepository.findByEmail(email).orElseThrow();

        List<Drink> favouriteDrinks = user.getDrinks();

        //TODO: moze lepiej zamiast obiektu po id - co sie stanie jak edytujemy obiekt? czy bedzie podwojnie w ulubionych?
//        List<Long> favouriteDrinkIds = favouriteDrinks.stream()
//                .map(Drink::getId)
//                .collect(Collectors.toUnmodifiableList());

        if (!favouriteDrinks.contains(drink)) {
            user.getDrinks().add(drink);
            userRepository.save(user);
            LOGGER.info("User email = {} added drink ID = {} to favourites", email, drinkId);

        } else {
            user.getDrinks().remove(drink);
            userRepository.save(user);
            LOGGER.info("User email = {} deleted drink ID = {} from favourites", email, drinkId);

        }

    }

    public List<FullDrinkView> favouritesList(String email) {

        User user = userRepository.findByEmail(email).orElseThrow();

        return fullDrinkMapper.toView(user.getDrinks());

    }

    public List<FullDrinkView> favouritesList(String email, int pageNumber) {
        int startPosition = (pageNumber - 1) * PAGE_SIZE;
        int endPosition = PAGE_SIZE;

        List<Drink> drinks = userRepository.findFavouritesList(email, PageRequest.of(startPosition, endPosition));

        return fullDrinkMapper.toView(drinks);

    }

    public int countPagesFavouritesList(String email) {
        int maxPageNumber = userRepository.countPagesFindFavouritesList(email);

        return maxPageNumber;

    }

    public int getMaxPageNumber(String querySize) {
        return (int) Math.ceil((Double.valueOf(querySize) / PAGE_SIZE));
    }

}
