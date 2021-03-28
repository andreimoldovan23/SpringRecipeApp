package guru.springframework.services;

import guru.springframework.domain.DTOs.UnitOfMeasureDTO;

import java.util.Set;

public interface UnitOfMeasureService {
    Set<UnitOfMeasureDTO> getAll();
}
