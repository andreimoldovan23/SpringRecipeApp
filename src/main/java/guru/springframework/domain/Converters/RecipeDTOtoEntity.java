package guru.springframework.domain.Converters;

import guru.springframework.domain.DTOs.RecipeDTO;
import guru.springframework.domain.Entities.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class RecipeDTOtoEntity implements Converter<RecipeDTO, Recipe> {

    private final IngredientDTOtoEntity ingredientDTOtoEntity;
    private final CategoryDTOtoEntity categoryDTOtoEntity;

    public RecipeDTOtoEntity(CategoryDTOtoEntity categoryDTOtoEntity, IngredientDTOtoEntity ingredientDTOtoEntity) {
        this.ingredientDTOtoEntity = ingredientDTOtoEntity;
        this.categoryDTOtoEntity = categoryDTOtoEntity;
    }

    @Synchronized
    @Nullable
    @Override
    public Recipe convert(@Nullable RecipeDTO recipeDTO) {
        return Optional.ofNullable(recipeDTO)
                .map(recpDTO -> {
                    final Recipe recipe = Recipe.builder()
                            .cookTime(recpDTO.getCookTime())
                            .prepTime(recpDTO.getPrepTime())
                            .description(recpDTO.getDescription())
                            .directions(recpDTO.getDirections())
                            .difficulty(recpDTO.getDifficulty())
                            .servings(recpDTO.getServings())
                            .source(recpDTO.getSource())
                            .url(recpDTO.getUrl())
                            .build();
                    recipe.setId(recpDTO.getId());
                    recipe.getIngredients().addAll(
                            recpDTO.getIngredientDTOS().stream()
                                .map(ingredientDTOtoEntity::convert)
                                .collect(Collectors.toSet())
                    );
                    recipe.getCategories().addAll(
                            recpDTO.getCategoryDTOS().stream()
                                .map(categoryDTOtoEntity::convert)
                                .collect(Collectors.toSet())
                    );
                    return recipe;
                })
                .orElse(null);
    }

}
