package guru.springframework.repositories;

import guru.springframework.domain.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class CategoryRepositoryIT {

    @Autowired
    CategoryRepository categoryRepository;

    @Test
    public void findByDescription() {
        Optional<Category> categoryOptional = categoryRepository.findByDescription("American");
        assertEquals("American", categoryOptional.get().getDescription());
    }

    @Test
    public void findByDescriptionMexican() {
        Optional<Category> categoryOptional = categoryRepository.findByDescription("Mexican");
        assertEquals("Mexican", categoryOptional.get().getDescription());
    }

}
