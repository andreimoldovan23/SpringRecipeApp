package guru.springframework.controllers;

import guru.springframework.domain.DTOs.RecipeDTO;
import guru.springframework.domain.Entities.Recipe;
import guru.springframework.services.RecipeService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class RecipeControllerTest {

    @Mock
    private Model modelController;

    @Mock
    private RecipeService recipeService;

    @InjectMocks
    private RecipeController recipeController;

    MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
    }

    @Test
    public void testGetRecipePage() throws Exception {
        when(recipeService.findById(1L)).thenReturn(Recipe.builder().description("Hello").build());
        ArgumentCaptor<Recipe> argumentCaptor = ArgumentCaptor.forClass(Recipe.class);

        mockMvc.perform(get("/recipe/show/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/show"))
                .andExpect(model().attributeExists("recipe"));

        recipeController.getRecipePage("1", modelController);
        verify(recipeService, times(2)).findById(anyLong());
        verify(modelController).addAttribute(eq("recipe"), argumentCaptor.capture());

        Recipe recipe = argumentCaptor.getValue();
        assertEquals("Hello", recipe.getDescription());
    }

    @Test
    public void testGetNewRecipeForm() throws Exception {
        mockMvc.perform(get("/recipe/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeform"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    public void testPostNewRecipeForm() throws Exception {
        RecipeDTO dto = new RecipeDTO();
        dto.setId(2L);

        when(recipeService.saveRecipeDTO(any())).thenReturn(dto);
        ArgumentCaptor<RecipeDTO> argumentCaptor = ArgumentCaptor.forClass(RecipeDTO.class);

        mockMvc.perform(post("/recipe")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id", "")
                        .param("description", "some string")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/index"));

        recipeController.saveOrUpdate(dto);
        verify(recipeService, times(2)).saveRecipeDTO(argumentCaptor.capture());
        assertEquals(2L, argumentCaptor.getValue().getId());
    }

    @Test
    public void testUpdateForm() throws Exception {
        RecipeDTO dto = new RecipeDTO();
        dto.setId(2L);
        ArgumentCaptor<RecipeDTO> argumentCaptor = ArgumentCaptor.forClass(RecipeDTO.class);

        when(recipeService.findDTObyId(anyLong())).thenReturn(dto);
        mockMvc.perform(get("/recipe/update/2"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeform"))
                .andExpect(model().attributeExists("recipe"));

        recipeController.updateRecipe("1", modelController);
        verify(recipeService, times(2)).findDTObyId(anyLong());
        verify(modelController).addAttribute(eq("recipe"), argumentCaptor.capture());
        assertEquals(2L, argumentCaptor.getValue().getId());
    }

    @Test
    public void testDeleteRecipe() throws Exception {
        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);

        mockMvc.perform(get("/recipe/delete/2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/index"));

        recipeController.deleteRecipe("2");
        verify(recipeService, times(2)).deleteById(argumentCaptor.capture());
        assertEquals(2L, argumentCaptor.getValue());
    }

}
