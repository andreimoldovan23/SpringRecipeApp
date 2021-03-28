package guru.springframework.domain.Converters;

import guru.springframework.domain.DTOs.UnitOfMeasureDTO;
import guru.springframework.domain.Entities.UnitOfMeasure;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UOMDTOtoEntity implements Converter<UnitOfMeasureDTO, UnitOfMeasure> {

    @Synchronized
    @Nullable
    @Override
    public UnitOfMeasure convert(@Nullable UnitOfMeasureDTO unitOfMeasureDTO) {
        return Optional.ofNullable(unitOfMeasureDTO)
                .map(uomDTO -> {
                    final UnitOfMeasure unitOfMeasure = UnitOfMeasure.builder()
                            .description(uomDTO.getDescription())
                            .build();
                    unitOfMeasure.setId(uomDTO.getId());
                    return unitOfMeasure;
                })
                .orElse(null);
    }

}
