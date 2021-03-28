package guru.springframework.domain.Converters;

import guru.springframework.domain.DTOs.CategoryDTO;
import guru.springframework.domain.Entities.Category;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CategoryToDTO implements Converter<Category, CategoryDTO> {

    @Synchronized
    @Nullable
    @Override
    public CategoryDTO convert(@Nullable Category category) {
        return Optional.ofNullable(category)
                .map(cat -> {
                    final CategoryDTO categoryDTO = new CategoryDTO();
                    categoryDTO.setId(cat.getId());
                    categoryDTO.setDescription(cat.getDescription());
                    return categoryDTO;
                })
                .orElse(null);
    }
}
