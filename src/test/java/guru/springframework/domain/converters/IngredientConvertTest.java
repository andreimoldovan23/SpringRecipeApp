package guru.springframework.domain.converters;

import guru.springframework.domain.Converters.IngredientDTOtoEntity;
import guru.springframework.domain.Converters.IngredientToDTO;
import guru.springframework.domain.Converters.UOMDTOtoEntity;
import guru.springframework.domain.Converters.UnitOfMeasureToDTO;
import guru.springframework.domain.DTOs.IngredientDTO;
import guru.springframework.domain.DTOs.UnitOfMeasureDTO;
import guru.springframework.domain.Entities.Ingredient;
import guru.springframework.domain.Entities.UnitOfMeasure;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class IngredientConvertTest {

    private IngredientToDTO ingredientToDTO;
    private IngredientDTOtoEntity ingredientDTOtoEntity;
    private IngredientDTO ingredientDTO;
    private Ingredient ingredient;

    @BeforeEach
    public void setUp() {
        ingredientDTOtoEntity = new IngredientDTOtoEntity(new UOMDTOtoEntity());
        ingredientToDTO = new IngredientToDTO(new UnitOfMeasureToDTO());

        UnitOfMeasure unitOfMeasure = UnitOfMeasure.builder().description("spoon").build();
        unitOfMeasure.setId(1L);
        ingredient = Ingredient.builder()
                .description("something")
                .amount(new BigDecimal(".5"))
                .uom(unitOfMeasure)
                .build();
        ingredient.setId(1L);

        UnitOfMeasureDTO unitOfMeasureDTO = new UnitOfMeasureDTO();
        unitOfMeasureDTO.setId(1L);
        unitOfMeasureDTO.setDescription("spoon");
        ingredientDTO = new IngredientDTO();
        ingredientDTO.setId(1L);
        ingredientDTO.setAmount(new BigDecimal(".5"));
        ingredientDTO.setDescription("something");
        ingredientDTO.setUom(unitOfMeasureDTO);
    }

    @AfterEach
    public void tearDown() {
        ingredientDTO = null;
        ingredientToDTO = null;
        ingredientDTOtoEntity = null;
        ingredient = null;
    }

    @Test
    public void toDTO() {
        IngredientDTO dto = ingredientToDTO.convert(ingredient);
        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("something", dto.getDescription());
        assertEquals(new BigDecimal(".5"), dto.getAmount());
        assertEquals("spoon", dto.getUom().getDescription());
        assertEquals(1L, dto.getUom().getId());
        assertNull(ingredientToDTO.convert(null));
    }

    @Test
    public void toEntity() {
        Ingredient entity = ingredientDTOtoEntity.convert(ingredientDTO);
        assertNotNull(entity);
        assertEquals(1L, entity.getId());
        assertEquals("something", entity.getDescription());
        assertEquals(new BigDecimal(".5"), entity.getAmount());
        assertEquals("spoon", entity.getUom().getDescription());
        assertEquals(1L, entity.getUom().getId());
        assertNull(ingredientDTOtoEntity.convert(null));
    }

}
