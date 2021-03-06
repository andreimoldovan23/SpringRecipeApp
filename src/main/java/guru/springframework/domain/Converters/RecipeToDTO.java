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
public class RecipeToDTO implements Converter<Recipe, RecipeDTO> {

    private final CategoryToDTO categoryToDTO;
    private final IngredientToDTO ingredientToDTO;
    private final NotesToDTO notesToDTO;

    public RecipeToDTO(CategoryToDTO categoryToDTO, IngredientToDTO ingredientToDTO, NotesToDTO notesToDTO) {
        this.categoryToDTO = categoryToDTO;
        this.ingredientToDTO = ingredientToDTO;
        this.notesToDTO = notesToDTO;
    }

    @Synchronized
    @Nullable
    @Override
    public RecipeDTO convert(@Nullable Recipe recipe) {
        return Optional.ofNullable(recipe)
                .map(recp -> {
                    final RecipeDTO recipeDTO = new RecipeDTO();
                    recipeDTO.setId(recp.getId());
                    recipeDTO.setCookTime(recp.getCookTime());
                    recipeDTO.setDescription(recp.getDescription());
                    recipeDTO.setDifficulty(recp.getDifficulty());
                    recipeDTO.setDirections(recp.getDirections());
                    recipeDTO.setServings(recp.getServings());
                    recipeDTO.setSource(recp.getSource());
                    recipeDTO.setPrepTime(recp.getPrepTime());
                    recipeDTO.setUrl(recp.getUrl());
                    recipeDTO.setNotes(notesToDTO.convert(recp.getNotes()));
                    recipeDTO.setImage(recp.getImage());
                    recipeDTO.getCategories().addAll(
                            recp.getCategories().stream()
                                .map(categoryToDTO::convert)
                                .collect(Collectors.toSet())
                    );
                    recipeDTO.getIngredients().addAll(
                            recp.getIngredients().stream()
                                .map(ingredientToDTO::convert)
                                .collect(Collectors.toSet())
                    );
                    return recipeDTO;
                })
                .orElse(null);
    }

}
