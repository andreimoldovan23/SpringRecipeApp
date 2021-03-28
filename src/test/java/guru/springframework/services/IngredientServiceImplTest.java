package guru.springframework.services;

import guru.springframework.domain.Converters.IngredientDTOtoEntity;
import guru.springframework.domain.Converters.IngredientToDTO;
import guru.springframework.domain.Converters.UOMDTOtoEntity;
import guru.springframework.domain.Converters.UnitOfMeasureToDTO;
import guru.springframework.domain.DTOs.IngredientDTO;
import guru.springframework.domain.Entities.Ingredient;
import guru.springframework.domain.Entities.Recipe;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class IngredientServiceImplTest {

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private UnitOfMeasureRepository unitOfMeasureRepository;

    private IngredientService ingredientService;

    @BeforeEach
    public void setUp() {
        IngredientToDTO ingredientToDTO = new IngredientToDTO(new UnitOfMeasureToDTO());
        IngredientDTOtoEntity ingredientDTOtoEntity = new IngredientDTOtoEntity(new UOMDTOtoEntity());
        ingredientService = new IngredientServiceImpl(recipeRepository, ingredientToDTO, ingredientDTOtoEntity, unitOfMeasureRepository);
    }

    @Test
    public void findByRecipeId() {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(1L);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(2L);

        Recipe recipe = new Recipe();
        recipe.setId(1L);
        recipe.getIngredients().add(ingredient);
        recipe.getIngredients().add(ingredient2);

        when(recipeRepository.findById(anyLong()))
                .thenAnswer(invocationOnMock -> {
                    Long id = invocationOnMock.getArgument(0);
                    return id <= 5 ? Optional.of(recipe) : Optional.empty();
                });

        IngredientDTO dto = ingredientService.findByIdOfRecipe(1L, 2L);
        assertThrows(RuntimeException.class, () -> ingredientService.findByIdOfRecipe(10L, 2L));
        assertThrows(RuntimeException.class, () -> ingredientService.findByIdOfRecipe(1L, 10L));
        assertEquals(2L, dto.getId());
    }

    @Test
    public void saveIngredientDTO() {
        IngredientDTO dto = new IngredientDTO();
        dto.setId(3L);
        dto.setRecipeId(2L);

        Optional<Recipe> recipeOptional = Optional.of(new Recipe());

        Recipe savedRecipe = new Recipe();
        savedRecipe.addIngredient(new Ingredient());
        savedRecipe.getIngredients().iterator().next().setId(3L);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        when(recipeRepository.save(any())).thenReturn(savedRecipe);

        IngredientDTO savedDTO = ingredientService.saveIngredientDTO(dto);

        assertEquals(Long.valueOf(3L), savedDTO.getId());
        verify(recipeRepository).findById(anyLong());
        verify(recipeRepository).save(any(Recipe.class));
    }

    @Test
    public void testDeleteById() {
        Recipe recipe = new Recipe();
        Ingredient ingredient = new Ingredient();
        ingredient.setId(3L);
        recipe.addIngredient(ingredient);
        ingredient.setRecipe(recipe);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        //when
        ingredientService.deleteById(1L, 3L);

        //then
        verify(recipeRepository).findById(anyLong());
        verify(recipeRepository).save(any(Recipe.class));
    }

}
