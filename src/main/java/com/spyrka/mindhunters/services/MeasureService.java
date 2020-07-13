package com.spyrka.mindhunters.services;


import com.spyrka.mindhunters.models.Measure;
import com.spyrka.mindhunters.repositories.MeasureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MeasureService {

    @Autowired
    private MeasureRepository measureRepository;

    public Measure getOrCreate(String quantity) {
        Measure measure = measureRepository.findByQuantity(quantity);
        if (measure == null) {
            measure = new Measure();
            measure.setQuantity(quantity);
            measureRepository.save(measure);
        }
        return measure;
    }
}
