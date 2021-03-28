package guru.springframework.services;

import guru.springframework.domain.Converters.RecipeDTOtoEntity;
import guru.springframework.domain.Converters.RecipeToDTO;
import guru.springframework.domain.DTOs.RecipeDTO;
import guru.springframework.domain.Entities.Recipe;
import guru.springframework.repositories.RecipeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeToDTO recipeToDTO;
    private final RecipeDTOtoEntity recipeDTOtoEntity;

    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeToDTO recipeToDTO, RecipeDTOtoEntity recipeDTOtoEntity) {
        this.recipeRepository = recipeRepository;
        this.recipeToDTO = recipeToDTO;
        this.recipeDTOtoEntity = recipeDTOtoEntity;
    }

    @Override
    public Set<Recipe> getRecipes() {
        Set<Recipe> recipeSet = new HashSet<>();
        recipeRepository.findAll().iterator().forEachRemaining(recipeSet::add);
        return recipeSet;
    }

    @Override
    public Recipe findById(Long id) {
        Optional<Recipe> recipe = recipeRepository.findById(id);
        if(recipe.isEmpty()) throw new RuntimeException("Invalid id");
        return recipe.get();
    }

    @Override
    @Transactional
    public RecipeDTO saveRecipeDTO(RecipeDTO recipeDTO) {
        Recipe detachedRecipe = recipeDTOtoEntity.convert(recipeDTO);
        Recipe savedRecipe = detachedRecipe == null ? null : recipeRepository.save(detachedRecipe);
        return recipeToDTO.convert(savedRecipe);
    }

    @Override
    @Transactional
    public RecipeDTO findDTObyId(Long id) {
        return recipeToDTO.convert(findById(id));
    }

    @Override
    public void deleteById(Long id) {
        recipeRepository.deleteById(id);
    }

}
