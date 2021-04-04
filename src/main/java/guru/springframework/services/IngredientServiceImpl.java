package guru.springframework.services;

import guru.springframework.domain.Converters.IngredientDTOtoEntity;
import guru.springframework.domain.Converters.IngredientToDTO;
import guru.springframework.domain.DTOs.IngredientDTO;
import guru.springframework.domain.Entities.Ingredient;
import guru.springframework.domain.Entities.Recipe;
import guru.springframework.exceptions.BadRequestException;
import guru.springframework.exceptions.NotFoundException;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final RecipeRepository recipeRepository;
    private final IngredientToDTO ingredientToDTO;
    private final IngredientDTOtoEntity ingredientDTOtoEntity;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public IngredientServiceImpl(RecipeRepository recipeRepository, IngredientToDTO ingredientToDTO,
                                 IngredientDTOtoEntity ingredientDTOtoEntity,
                                 UnitOfMeasureRepository unitOfMeasureRepository) {
        this.recipeRepository = recipeRepository;
        this.ingredientToDTO = ingredientToDTO;
        this.ingredientDTOtoEntity = ingredientDTOtoEntity;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public IngredientDTO findByIdOfRecipe(Long recpId, Long ingId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recpId);
        return recipeOptional.flatMap(recp ->
                recp.getIngredients().stream()
                        .filter(ing -> ing.getId().equals(ingId))
                        .map(ingredientToDTO::convert)
                        .findFirst())
                .orElseThrow(() -> new NotFoundException("Recipe or ingredient do not exist"));
    }

    @Transactional
    @Override
    public IngredientDTO saveIngredientDTO(IngredientDTO dto) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(dto.getRecipeId());

        if (recipeOptional.isEmpty()) {
            throw new NotFoundException("Recipe does not exist");
        } else {
            Recipe recipe = recipeOptional.get();

            Optional<Ingredient> ingredientOptional = recipe
                    .getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(dto.getId()))
                    .findFirst();

            Ingredient ingredient;
            if (ingredientOptional.isPresent()) {
                ingredient = ingredientOptional.get();
                ingredient.setDescription(dto.getDescription());
                ingredient.setAmount(dto.getAmount());
                ingredient.setUom(unitOfMeasureRepository.findById(dto.getUom().getId())
                        .orElseThrow(() -> new NotFoundException("UOM NOT FOUND")));
            } else {
                ingredient = ingredientDTOtoEntity.convert(dto);
                if (ingredient != null) recipe.addIngredient(ingredient);
                else throw new BadRequestException("Invalid ingredient");
            }

            Recipe savedRecipe = recipeRepository.save(recipe);

            return ingredientToDTO.convert(savedRecipe.getIngredients().stream()
                    .filter(recipeIngredients -> recipeIngredients.getId().equals(dto.getId()))
                    .findFirst()
                    .orElse(ingredient));
        }

    }

    @Override
    public void deleteById(Long recId, Long id) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recId);
        if(recipeOptional.isEmpty()) throw new NotFoundException("Recipe does not exist");
        Recipe recipe = recipeOptional.get();

        Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
                .filter(ing -> ing.getId().equals(id))
                .findFirst();
        if(ingredientOptional.isEmpty()) throw new NotFoundException("Ingredient does not exist");
        Ingredient ingredient = ingredientOptional.get();

        ingredient.setRecipe(null);
        recipe.getIngredients().remove(ingredient);
        recipeRepository.save(recipe);
    }

}
