package guru.springframework.repositories;

import guru.springframework.domain.Entities.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
}
