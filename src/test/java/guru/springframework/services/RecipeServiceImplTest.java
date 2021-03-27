package guru.springframework.services;


import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RecipeServiceImplTest {

    @Mock
    RecipeRepository recipeRepository;

    @InjectMocks
    RecipeServiceImpl recipeService;

    @Test
    public void getRecipes() {
        Recipe recipe = new Recipe();

        when(recipeService.getRecipes()).thenReturn(Set.of(recipe));

        Set<Recipe> recipes = recipeService.getRecipes();
        assertEquals(recipes.size(), 1);
        verify(recipeRepository, times(1)).findAll();
    }

}