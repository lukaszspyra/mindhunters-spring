package com.spyrka.mindhunters.repositories;

import com.spyrka.mindhunters.models.Measure;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotNull;

public interface MeasureRepository extends JpaRepository<Measure, Long>{

    Measure findByQuantity(@NotNull String quantity);
}
