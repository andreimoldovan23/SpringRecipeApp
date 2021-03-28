package guru.springframework.domain.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class IngredientDTO {
    private Long id;
    private String description;
    private BigDecimal amount;
    private UnitOfMeasureDTO unitOfMeasureDTO;
}
