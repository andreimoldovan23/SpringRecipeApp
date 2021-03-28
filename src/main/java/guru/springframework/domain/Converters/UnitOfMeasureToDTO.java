package guru.springframework.domain.Converters;

import guru.springframework.domain.DTOs.UnitOfMeasureDTO;
import guru.springframework.domain.Entities.UnitOfMeasure;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UnitOfMeasureToDTO implements Converter<UnitOfMeasure, UnitOfMeasureDTO> {

    @Synchronized
    @Nullable
    @Override
    public UnitOfMeasureDTO convert(@Nullable UnitOfMeasure unitOfMeasure) {
        return Optional.ofNullable(unitOfMeasure)
                .map(uom -> {
                    final UnitOfMeasureDTO uomDTO = new UnitOfMeasureDTO();
                    uomDTO.setDescription(uom.getDescription());
                    uomDTO.setId(uom.getId());
                    return uomDTO;
                })
                .orElse(null);
    }

}
