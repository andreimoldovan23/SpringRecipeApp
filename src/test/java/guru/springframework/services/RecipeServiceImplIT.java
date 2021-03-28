package guru.springframework.services;

import guru.springframework.domain.Converters.RecipeDTOtoEntity;
import guru.springframework.domain.Converters.RecipeToDTO;
import guru.springframework.domain.DTOs.RecipeDTO;
import guru.springframework.domain.Entities.Recipe;
import guru.springframework.repositories.RecipeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class RecipeServiceImplIT {

    private static final String NEW_DESCRIPTION = "New Description";

    @Autowired
    RecipeService recipeService;

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    RecipeDTOtoEntity recipeDTOtoEntity;

    @Autowired
    RecipeToDTO recipeToDTO;

    @Transactional
    @Test
    public void testSaveOfDescription() throws Exception {
        Iterable<Recipe> recipes = recipeRepository.findAll();
        Recipe testRecipe = recipes.iterator().next();
        RecipeDTO testRecipeDTO = recipeToDTO.convert(testRecipe);

        assertNotNull(testRecipeDTO);
        testRecipeDTO.setDescription(NEW_DESCRIPTION);
        RecipeDTO savedRecipeDTO = recipeService.saveRecipeDTO(testRecipeDTO);

        assertEquals(NEW_DESCRIPTION, savedRecipeDTO.getDescription());
        assertEquals(testRecipe.getId(), savedRecipeDTO.getId());
        assertEquals(testRecipe.getCategories().size(), savedRecipeDTO.getCategoryDTOS().size());
        assertEquals(testRecipe.getIngredients().size(), savedRecipeDTO.getIngredientDTOS().size());
    }

}
