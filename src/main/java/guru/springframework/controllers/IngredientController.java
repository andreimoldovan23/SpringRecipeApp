package guru.springframework.controllers;

import guru.springframework.domain.DTOs.IngredientDTO;
import guru.springframework.domain.DTOs.RecipeDTO;
import guru.springframework.domain.DTOs.UnitOfMeasureDTO;
import guru.springframework.services.IngredientService;
import guru.springframework.services.RecipeService;
import guru.springframework.services.UnitOfMeasureService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService,
                                UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping("/recipe/{id}/ingredients")
    public String getIngredients(@PathVariable String id, Model model) {
        model.addAttribute("recipe", recipeService.findDTObyId(Long.valueOf(id)));
        return "recipe/ingredients/list";
    }

    @GetMapping("/recipe/{recId}/ingredient/{ingId}/show")
    public String getIngredientOfRecipe(@PathVariable String recId, @PathVariable String ingId, Model model) {
        model.addAttribute("ingredient", ingredientService.findByIdOfRecipe(Long.valueOf(recId), Long.valueOf(ingId)));
        return "recipe/ingredients/show";
    }

    @GetMapping("/recipe/{recId}/ingredient/{ingId}/update")
    public String updateRecipeIngredient(@PathVariable String recId, @PathVariable String ingId, Model model){
        model.addAttribute("ingredient", ingredientService.findByIdOfRecipe(Long.valueOf(recId), Long.valueOf(ingId)));
        model.addAttribute("uomList", unitOfMeasureService.getAll());
        return "recipe/ingredients/ingredientform";
    }

    @GetMapping("/recipe/{recId}/ingredient/new")
    public String createIngredient(@PathVariable String recId, Model model) {
        RecipeDTO recipeDTO = recipeService.findDTObyId(Long.valueOf(recId));
        IngredientDTO ingredientDTO = new IngredientDTO();
        ingredientDTO.setRecipeId(recipeDTO.getId());
        model.addAttribute("ingredient", ingredientDTO);

        ingredientDTO.setUom(new UnitOfMeasureDTO());
        model.addAttribute("uomList", unitOfMeasureService.getAll());

        return "recipe/ingredients/ingredientform";
    }

    @PostMapping("/recipe/{recipeId}/ingredient")
    public String saveOrUpdate(@ModelAttribute IngredientDTO dto){
        IngredientDTO savedDTO = ingredientService.saveIngredientDTO(dto);
        return "redirect:/recipe/update/" + savedDTO.getRecipeId();
    }

    @GetMapping("/recipe/{recId}/ingredient/{id}/delete")
    public String deleteIngredient(@PathVariable String recId, @PathVariable String id) {
        ingredientService.deleteById(Long.valueOf(recId), Long.valueOf(id));
        return "redirect:/recipe/" + recId + "/ingredients";
    }

}
