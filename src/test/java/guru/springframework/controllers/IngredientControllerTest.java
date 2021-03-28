package guru.springframework.controllers;

import guru.springframework.domain.DTOs.IngredientDTO;
import guru.springframework.domain.DTOs.RecipeDTO;
import guru.springframework.services.IngredientService;
import guru.springframework.services.RecipeService;
import guru.springframework.services.UnitOfMeasureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class IngredientControllerTest {

    @Mock
    private Model modelController;

    @Mock
    private RecipeService recipeService;

    @Mock
    private IngredientService ingredientService;

    @Mock
    private UnitOfMeasureService unitOfMeasureService;

    @InjectMocks
    private IngredientController ingredientController;

    MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();
    }

    @Test
    public void testGetIngredients() throws Exception {
        RecipeDTO dto = new RecipeDTO();
        dto.setId(1L);
        when(recipeService.findDTObyId(1L)).thenReturn(dto);
        ArgumentCaptor<RecipeDTO> argumentCaptor = ArgumentCaptor.forClass(RecipeDTO.class);

        mockMvc.perform(get("/recipe/1/ingredients"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredients/list"))
                .andExpect(model().attributeExists("recipe"));

        ingredientController.getIngredients("1", modelController);
        verify(recipeService, times(2)).findDTObyId(anyLong());
        verify(modelController).addAttribute(eq("recipe"), argumentCaptor.capture());
        assertEquals(1L, argumentCaptor.getValue().getId());
    }

    @Test
    public void testGetIngredientOfRecipe() throws Exception {
        IngredientDTO ingredientDTO = new IngredientDTO();
        ingredientDTO.setId(2L);
        when(ingredientService.findByIdOfRecipe(1L, 2L)).thenReturn(ingredientDTO);
        ArgumentCaptor<IngredientDTO> argumentCaptor = ArgumentCaptor.forClass(IngredientDTO.class);

        mockMvc.perform(get("/recipe/1/ingredient/2/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredients/show"))
                .andExpect(model().attributeExists("ingredient"));

        ingredientController.getIngredientOfRecipe("1", "2", modelController);
        verify(ingredientService, times(2)).findByIdOfRecipe(anyLong(), anyLong());
        verify(modelController).addAttribute(eq("ingredient"), argumentCaptor.capture());
        assertEquals(2L, argumentCaptor.getValue().getId());
    }

    @Test
    public void testUpdateIngredientForm() throws Exception {
        IngredientDTO ingredientDTO = new IngredientDTO();

        when(ingredientService.findByIdOfRecipe(anyLong(), anyLong())).thenReturn(ingredientDTO);
        when(unitOfMeasureService.getAll()).thenReturn(new HashSet<>());

        mockMvc.perform(get("/recipe/1/ingredient/2/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredients/ingredientform"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("uomList"));
    }

    @Test
    public void testSaveOrUpdate() throws Exception {
        IngredientDTO dto = new IngredientDTO();
        dto.setId(3L);
        dto.setRecipeId(2L);

        when(ingredientService.saveIngredientDTO(any())).thenReturn(dto);

        mockMvc.perform(post("/recipe/2/ingredient")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("description", "some string")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/update/2"));

    }

    @Test
    public void testNewIngredientForm() throws Exception {
        RecipeDTO recipeDTO = new RecipeDTO();
        recipeDTO.setId(1L);

        when(recipeService.findDTObyId(anyLong())).thenReturn(recipeDTO);
        when(unitOfMeasureService.getAll()).thenReturn(new HashSet<>());

        mockMvc.perform(get("/recipe/1/ingredient/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredients/ingredientform"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("uomList"));

        verify(recipeService).findDTObyId(anyLong());
    }

    @Test
    public void testDeleteIngredient() throws Exception {
        mockMvc.perform(get("/recipe/2/ingredient/3/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/2/ingredients"));
        verify(ingredientService, times(1)).deleteById(anyLong(), anyLong());
    }

}
