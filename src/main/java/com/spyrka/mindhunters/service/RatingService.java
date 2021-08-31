package com.spyrka.mindhunters.service;

import com.spyrka.mindhunters.model.Rating;
import com.spyrka.mindhunters.repository.DrinkRepository;
import com.spyrka.mindhunters.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private DrinkRepository drinkRepository;


    public Double getCalculatedRatingByDrinkId(Long drinkId) {

        final Rating rating = getRatingByDrinkId(drinkId);

        if (rating.getSum() == 0.0) {
            return 0.0;
        }

        return rating.getSum() / rating.getNumberOfRatings();

    }

    public Rating getRatingByDrinkId(Long drinkId) {

        return ratingRepository.findByDrinkId(drinkId).orElseGet(() -> createEmptyRating(drinkId));

    }


    private Rating createEmptyRating(Long drinkId) {

        Rating initialRating = new Rating();
        initialRating.setNumberOfRatings(0L);
        initialRating.setSum(0.0);
        initialRating.setDrink(drinkRepository.findById(drinkId).get());

        ratingRepository.save(initialRating);

        return initialRating;
    }

    @Transactional
    public Rating updateRating(Long drinkId, Double ratingParam) {

        final Rating rating = ratingRepository.findByDrinkId(drinkId).get();

        final Double newSum = rating.getSum() + ratingParam;
        final long newNumberOfRating = rating.getNumberOfRatings() + 1;

        rating.setSum(newSum);
        rating.setNumberOfRatings(newNumberOfRating);

        return ratingRepository.save(rating);

    }

}
