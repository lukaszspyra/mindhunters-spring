package com.spyrka.mindhunters.repository;

import com.spyrka.mindhunters.model.Measure;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotNull;

public interface MeasureRepository extends JpaRepository<Measure, Long>{

    Measure findByQuantity(@NotNull String quantity);
}
