package guru.springframework.domain.Converters;

import guru.springframework.domain.DTOs.CategoryDTO;
import guru.springframework.domain.Entities.Category;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CategoryDTOtoEntity implements Converter<CategoryDTO, Category> {

    @Synchronized
    @Nullable
    @Override
    public Category convert(@Nullable CategoryDTO categoryDTO) {
        return Optional.ofNullable(categoryDTO)
                .map(catDTO -> {
                    final Category category = Category.builder()
                            .description(catDTO.getDescription())
                            .build();
                    category.setId(catDTO.getId());
                    return category;
                })
                .orElse(null);
    }

}
