package guru.springframework.services;

import guru.springframework.domain.Converters.UnitOfMeasureToDTO;
import guru.springframework.domain.DTOs.UnitOfMeasureDTO;
import guru.springframework.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final UnitOfMeasureToDTO unitOfMeasureToDTO;

    public UnitOfMeasureServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository, UnitOfMeasureToDTO unitOfMeasureToDTO) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.unitOfMeasureToDTO = unitOfMeasureToDTO;
    }

    @Override
    public Set<UnitOfMeasureDTO> getAll() {
        return StreamSupport.stream(unitOfMeasureRepository.findAll().spliterator(), false)
                .map(unitOfMeasureToDTO::convert)
                .collect(Collectors.toSet());
    }

}
