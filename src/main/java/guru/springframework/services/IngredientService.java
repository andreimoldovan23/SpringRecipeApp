package guru.springframework.services;

import guru.springframework.domain.DTOs.IngredientDTO;

public interface IngredientService {
    IngredientDTO findByIdOfRecipe(Long l1, Long l2);
    IngredientDTO saveIngredientDTO(IngredientDTO dto);
    void deleteById(Long recId, Long id);
}
