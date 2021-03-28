package guru.springframework.domain.converters;

import guru.springframework.domain.Converters.CategoryDTOtoEntity;
import guru.springframework.domain.Converters.CategoryToDTO;
import guru.springframework.domain.DTOs.CategoryDTO;
import guru.springframework.domain.Entities.Category;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryConvertTest {

    private CategoryDTO categoryDTO;
    private Category category;
    private CategoryToDTO categoryToDTO;
    private CategoryDTOtoEntity categoryDTOtoEntity;

    @BeforeEach
    public void setUp() {
        categoryToDTO = new CategoryToDTO();
        categoryDTOtoEntity = new CategoryDTOtoEntity();

        category = Category.builder()
                .description("something")
                .build();
        category.setId(1L);

        categoryDTO = new CategoryDTO();
        categoryDTO.setId(1L);
        categoryDTO.setDescription("something");
    }

    @AfterEach
    public void tearDown() {
        categoryDTO = null;
        categoryDTOtoEntity = null;
        categoryToDTO = null;
        category = null;
    }

    @Test
    public void toDTO() {
        CategoryDTO dto = categoryToDTO.convert(category);
        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("something", dto.getDescription());
        assertNull(categoryToDTO.convert(null));
    }

    @Test
    public void toEntity() {
        Category entity = categoryDTOtoEntity.convert(categoryDTO);
        assertNotNull(entity);
        assertEquals(1L, entity.getId());
        assertEquals("something", entity.getDescription());
        assertNull(categoryDTOtoEntity.convert(null));
    }
}
