package guru.springframework.services;

import guru.springframework.domain.DTOs.RecipeDTO;
import guru.springframework.domain.Entities.Recipe;

import java.util.Set;

public interface RecipeService {
    Set<Recipe> getRecipes();
    Recipe findById(Long id);
    RecipeDTO saveRecipeDTO(RecipeDTO recipeDTO);
    RecipeDTO findDTObyId(Long id);
    void deleteById(Long id);
}
