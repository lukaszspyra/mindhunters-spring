package com.spyrka.mindhunters.services;

import com.infoshareacademy.domain.Rating;
import com.infoshareacademy.repository.DrinkRepository;
import com.infoshareacademy.repository.RatingRepository;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.transaction.Transactional;

@Stateless
public class RatingService {

    @EJB
    private RatingRepository ratingRepository;

    @EJB
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
        initialRating.setDrink(drinkRepository.findDrinkById(drinkId));

        ratingRepository.saveRating(initialRating);

        return initialRating;
    }

    @Transactional
    public Rating updateRating(Long drinkId, Double ratingParam) {

        return ratingRepository.updateRating(drinkId, ratingParam);

    }

}
