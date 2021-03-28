package guru.springframework.services;

import guru.springframework.domain.Converters.UnitOfMeasureToDTO;
import guru.springframework.domain.DTOs.UnitOfMeasureDTO;
import guru.springframework.domain.Entities.UnitOfMeasure;
import guru.springframework.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UnitOfMeasureServiceImplTest {

    UnitOfMeasureToDTO unitOfMeasureToDTO = new UnitOfMeasureToDTO();
    UnitOfMeasureService service;

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    @BeforeEach
    public void setUp() {
        service = new UnitOfMeasureServiceImpl(unitOfMeasureRepository, unitOfMeasureToDTO);
    }

    @Test
    public void getAll() {
        Set<UnitOfMeasure> unitOfMeasures = new HashSet<>();
        UnitOfMeasure uom1 = new UnitOfMeasure();
        uom1.setId(1L);
        unitOfMeasures.add(uom1);

        UnitOfMeasure uom2 = new UnitOfMeasure();
        uom2.setId(2L);
        unitOfMeasures.add(uom2);

        when(unitOfMeasureRepository.findAll()).thenReturn(unitOfMeasures);

        Set<UnitOfMeasureDTO> dtos = service.getAll();

        assertEquals(2, dtos.size());
        verify(unitOfMeasureRepository).findAll();
    }
}
