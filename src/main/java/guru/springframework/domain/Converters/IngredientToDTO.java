package guru.springframework.domain.Converters;

import guru.springframework.domain.DTOs.IngredientDTO;
import guru.springframework.domain.Entities.Ingredient;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class IngredientToDTO implements Converter<Ingredient, IngredientDTO> {

    private final UnitOfMeasureToDTO unitOfMeasureToDTO;

    public IngredientToDTO(UnitOfMeasureToDTO unitOfMeasureToDTO) {
        this.unitOfMeasureToDTO = unitOfMeasureToDTO;
    }

    @Synchronized
    @Nullable
    @Override
    public IngredientDTO convert(@Nullable Ingredient ingredient) {
        return Optional.ofNullable(ingredient)
                .map(ing -> {
                    final IngredientDTO ingredientDTO = new IngredientDTO();
                    ingredientDTO.setId(ing.getId());
                    ingredientDTO.setAmount(ing.getAmount());
                    ingredientDTO.setDescription(ing.getDescription());
                    ingredientDTO.setUnitOfMeasureDTO(unitOfMeasureToDTO.convert(ing.getUom()));
                    return ingredientDTO;
                })
                .orElse(null);
    }

}
