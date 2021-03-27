package guru.springframework.services;


import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    @Test
    public void findById() {
        Recipe recipe = new Recipe();

        when(recipeRepository.findById(anyLong()))
                .thenAnswer(invocationOnMock -> {
                    Long argument = invocationOnMock.getArgument(0);
                    return argument <= 5L ? Optional.of(recipe) : Optional.empty();
                });

        assertEquals(recipe, recipeService.findById(3L));
        assertThrows(RuntimeException.class, () -> recipeService.findById(10L));
        verify(recipeRepository, times(2)).findById(anyLong());
        verify(recipeRepository, never()).findAll();
    }

}