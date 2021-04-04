package guru.springframework.services;

import guru.springframework.domain.Entities.Recipe;
import guru.springframework.exceptions.BadRequestException;
import guru.springframework.exceptions.NotFoundException;
import guru.springframework.repositories.RecipeRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {

    private final RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void saveImage(Long id, MultipartFile file) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(id);

        if(recipeOptional.isEmpty()) throw new NotFoundException("Recipe does not exist");

        try {
            Recipe recipe = recipeOptional.get();
            Byte[] bytes = new Byte[file.getBytes().length];
            int i = 0;
            for(byte b: file.getBytes())
                bytes[i++] = b;
            recipe.setImage(bytes);
            recipeRepository.save(recipe);
        } catch (IOException ioe) {
            throw new BadRequestException("Error saving image");
        }
    }
}
