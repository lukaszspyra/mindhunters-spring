package com.spyrka.mindhunters.repositories;

import com.spyrka.mindhunters.models.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    List<Ingredient> findByNameContaining(@NotNull String name);

    Ingredient findByName(@NotNull String name);

/*    @Override
    public List<Ingredient> liveSearchIngredientsByName(String partialIngredientName) {
        Query query = entityManager.createNamedQuery("Ingredient.findIngredientsByPartialName");
        query.setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false);

        query.setMaxResults(LIVE_SEARCH_LIMIT);
        query.setParameter("partialIngredientName", "%" + partialIngredientName + "%");
        return query.getResultList();
    }*/
}
