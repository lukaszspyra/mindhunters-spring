package com.spyrka.mindhunters.service;


import com.spyrka.mindhunters.model.Measure;
import com.spyrka.mindhunters.repository.MeasureRepository;
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
            measure = measureRepository.save(measure);
        }
        return measure;
    }
}
