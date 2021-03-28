package guru.springframework.domain.Converters;

import guru.springframework.domain.DTOs.IngredientDTO;
import guru.springframework.domain.Entities.Ingredient;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class IngredientDTOtoEntity implements Converter<IngredientDTO, Ingredient> {

    private final UOMDTOtoEntity uomdtOtoEntity;

    public IngredientDTOtoEntity(UOMDTOtoEntity uomdtOtoEntity) {
        this.uomdtOtoEntity = uomdtOtoEntity;
    }

    @Synchronized
    @Nullable
    @Override
    public Ingredient convert(@Nullable IngredientDTO ingredientDTO) {
        return Optional.ofNullable(ingredientDTO)
                .map(ingDto -> {
                    final Ingredient ingredient = Ingredient.builder()
                            .description(ingDto.getDescription())
                            .amount(ingDto.getAmount())
                            .uom(uomdtOtoEntity.convert(ingDto.getUom()))
                            .build();
                    ingredient.setId(ingDto.getId());
                    return ingredient;
                })
                .orElse(null);
    }

}
