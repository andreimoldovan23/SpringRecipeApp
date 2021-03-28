package guru.springframework.domain.converters;

import guru.springframework.domain.Converters.RecipeDTOtoEntity;
import guru.springframework.domain.Converters.CategoryToDTO;
import guru.springframework.domain.Converters.IngredientToDTO;
import guru.springframework.domain.Converters.CategoryDTOtoEntity;
import guru.springframework.domain.Converters.IngredientDTOtoEntity;
import guru.springframework.domain.Converters.RecipeToDTO;
import guru.springframework.domain.Converters.NotesToDTO;
import guru.springframework.domain.Converters.NotesDTOtoEntity;
import guru.springframework.domain.Converters.UnitOfMeasureToDTO;
import guru.springframework.domain.Converters.UOMDTOtoEntity;
import guru.springframework.domain.DTOs.*;
import guru.springframework.domain.Entities.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class RecipeConvertTest {

    private CategoryToDTO categoryToDTO;
    private CategoryDTOtoEntity categoryDTOtoEntity;
    private IngredientToDTO ingredientToDTO;
    private IngredientDTOtoEntity ingredientDTOtoEntity;
    private RecipeToDTO recipeToDTO;
    private RecipeDTOtoEntity recipeDTOtoEntity;
    private NotesToDTO notesToDTO;
    private NotesDTOtoEntity notesDTOtoEntity;

    private Recipe recipe;
    private RecipeDTO recipeDTO;
    private Category category;
    private CategoryDTO categoryDTO;
    private Ingredient ingredient;
    private IngredientDTO ingredientDTO;

    @BeforeEach
    public void setUp() {
        categoryToDTO = new CategoryToDTO();
        categoryDTOtoEntity = new CategoryDTOtoEntity();
        ingredientToDTO = new IngredientToDTO(new UnitOfMeasureToDTO());
        ingredientDTOtoEntity = new IngredientDTOtoEntity(new UOMDTOtoEntity());
        notesToDTO = new NotesToDTO();
        notesDTOtoEntity = new NotesDTOtoEntity();
        recipeToDTO = new RecipeToDTO(categoryToDTO, ingredientToDTO, notesToDTO);
        recipeDTOtoEntity = new RecipeDTOtoEntity(categoryDTOtoEntity, ingredientDTOtoEntity, notesDTOtoEntity);

        Notes notes = Notes.builder()
                .recipeNotes("noted")
                .build();
        notes.setId(1L);

        NotesDTO notesDTO = new NotesDTO();
        notesDTO.setId(1L);
        notesDTO.setRecipeNotes("noted");

        category = Category.builder()
                .description("something")
                .build();
        category.setId(1L);

        categoryDTO = new CategoryDTO();
        categoryDTO.setId(1L);
        categoryDTO.setDescription("something");

        UnitOfMeasure unitOfMeasure = UnitOfMeasure.builder().description("spoon").build();
        unitOfMeasure.setId(1L);
        ingredient = Ingredient.builder()
                .description("something")
                .amount(new BigDecimal(".5"))
                .uom(unitOfMeasure)
                .build();
        ingredient.setId(1L);

        UnitOfMeasureDTO unitOfMeasureDTO = new UnitOfMeasureDTO();
        unitOfMeasureDTO.setId(1L);
        unitOfMeasureDTO.setDescription("spoon");
        ingredientDTO = new IngredientDTO();
        ingredientDTO.setId(1L);
        ingredientDTO.setAmount(new BigDecimal(".5"));
        ingredientDTO.setDescription("something");
        ingredientDTO.setUom(unitOfMeasureDTO);

        recipe = Recipe.builder()
                .servings(5)
                .cookTime(5)
                .prepTime(5)
                .description("something")
                .directions("something")
                .url("http")
                .source("http")
                .difficulty(Difficulty.EASY)
                .notes(notes)
                .build();
        recipe.setId(1L);
        recipe.getCategories().add(category);
        recipe.getIngredients().add(ingredient);

        recipeDTO = new RecipeDTO();
        recipeDTO.setId(1L);
        recipeDTO.setServings(5);
        recipeDTO.setCookTime(5);
        recipeDTO.setPrepTime(5);
        recipeDTO.setDescription("something");
        recipeDTO.setDirections("something");
        recipeDTO.setUrl("http");
        recipeDTO.setSource("http");
        recipeDTO.setDifficulty(Difficulty.EASY);
        recipeDTO.setNotes(notesDTO);
        recipeDTO.getCategories().add(categoryDTO);
        recipeDTO.getIngredients().add(ingredientDTO);
    }

    @AfterEach
    public void tearDown() {
        categoryToDTO = null;
        categoryDTOtoEntity = null;
        ingredientToDTO = null;
        ingredientDTOtoEntity = null;
        recipeToDTO = null;
        recipeDTOtoEntity = null;
        recipe = null;
        recipeDTO = null;
        category = null;
        categoryDTO = null;
        ingredient = null;
        ingredientDTO = null;
        notesToDTO = null;
        notesDTOtoEntity = null;
    }

    @Test
    public void toDTO() {
        RecipeDTO dto = recipeToDTO.convert(recipe);
        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals(5, dto.getServings());
        assertEquals(5, dto.getCookTime());
        assertEquals(5, dto.getPrepTime());
        assertEquals("something", dto.getDescription());
        assertEquals("something", dto.getDirections());
        assertEquals("http", dto.getUrl());
        assertEquals("http", dto.getSource());
        assertEquals(Difficulty.EASY, dto.getDifficulty());
        assertEquals(1, dto.getCategories().size());
        assertEquals(1, dto.getIngredients().size());

        CategoryDTO catDTO = new ArrayList<>(dto.getCategories()).get(0);
        assertEquals(1L, catDTO.getId());
        assertEquals("something", catDTO.getDescription());

        IngredientDTO ingDTO = new ArrayList<>(dto.getIngredients()).get(0);
        assertEquals(1L, ingDTO.getId());
        assertEquals(new BigDecimal(".5"), ingDTO.getAmount());
        assertEquals("something", ingDTO.getDescription());
        assertEquals(1L, ingDTO.getUom().getId());
        assertEquals("spoon", ingDTO.getUom().getDescription());

        NotesDTO nDTO = dto.getNotes();
        assertEquals(1L, nDTO.getId());
        assertEquals("noted", nDTO.getRecipeNotes());

        assertNull(recipeToDTO.convert(null));
    }

    @Test
    public void toEntity() {
        Recipe entity = recipeDTOtoEntity.convert(recipeDTO);
        assertNotNull(entity);
        assertEquals(1L, entity.getId());
        assertEquals(5, entity.getServings());
        assertEquals(5, entity.getCookTime());
        assertEquals(5, entity.getPrepTime());
        assertEquals("something", entity.getDescription());
        assertEquals("something", entity.getDirections());
        assertEquals("http", entity.getUrl());
        assertEquals("http", entity.getSource());
        assertEquals(Difficulty.EASY, entity.getDifficulty());
        assertEquals(1, entity.getCategories().size());
        assertEquals(1, entity.getIngredients().size());

        Category cat = new ArrayList<>(entity.getCategories()).get(0);
        assertEquals(1L, cat.getId());
        assertEquals("something", cat.getDescription());

        Ingredient ing = new ArrayList<>(entity.getIngredients()).get(0);
        assertEquals(1L, ing.getId());
        assertEquals(new BigDecimal(".5"), ing.getAmount());
        assertEquals("something", ing.getDescription());
        assertEquals(1L, ing.getUom().getId());
        assertEquals("spoon", ing.getUom().getDescription());

        Notes n = entity.getNotes();
        assertEquals(1L, n.getId());
        assertEquals("noted", n.getRecipeNotes());

        assertNull(recipeDTOtoEntity.convert(null));
    }

}
