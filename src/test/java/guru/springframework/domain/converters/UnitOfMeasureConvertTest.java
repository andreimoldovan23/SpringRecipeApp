package guru.springframework.domain.converters;


import guru.springframework.domain.Converters.UOMDTOtoEntity;
import guru.springframework.domain.Converters.UnitOfMeasureToDTO;
import guru.springframework.domain.DTOs.UnitOfMeasureDTO;
import guru.springframework.domain.Entities.UnitOfMeasure;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UnitOfMeasureConvertTest {

    private UnitOfMeasureToDTO unitOfMeasureToDTO;
    private UOMDTOtoEntity uomdtOtoEntity;
    private UnitOfMeasure uom;
    private UnitOfMeasureDTO uomDTO;

    @BeforeEach
    public void setUp() {
        unitOfMeasureToDTO = new UnitOfMeasureToDTO();
        uomdtOtoEntity = new UOMDTOtoEntity();

        uom = UnitOfMeasure.builder()
                .description("something")
                .build();
        uom.setId(1L);

        uomDTO = new UnitOfMeasureDTO();
        uomDTO.setDescription("something");
        uomDTO.setId(1L);
    }

    @AfterEach
    public void tearDown() {
        unitOfMeasureToDTO = null;
        uomdtOtoEntity = null;
        uom = null;
        uomDTO = null;
    }

    @Test
    public void toDTO() {
        UnitOfMeasureDTO dto = unitOfMeasureToDTO.convert(uom);
        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("something", dto.getDescription());
        assertNull(unitOfMeasureToDTO.convert(null));
    }

    @Test
    public void toEntity() {
        UnitOfMeasure unit = uomdtOtoEntity.convert(uomDTO);
        assertNotNull(unit);
        assertEquals(1L, unit.getId());
        assertEquals("something", unit.getDescription());
        assertNull(uomdtOtoEntity.convert(null));
    }

}
