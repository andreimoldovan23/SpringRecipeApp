package guru.springframework.controllers;

import guru.springframework.domain.DTOs.RecipeDTO;
import guru.springframework.services.ImageService;
import guru.springframework.services.RecipeService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class ImageController {

    private final ImageService imageService;
    private final RecipeService recipeService;

    public ImageController(ImageService imageService, RecipeService recipeService) {
        this.imageService = imageService;
        this.recipeService = recipeService;
    }

    @GetMapping("/recipe/{id}/image")
    public String getImageForm(@PathVariable String id, Model model) {
        model.addAttribute("recipe", recipeService.findDTObyId(Long.valueOf(id)));
        return "recipe/imageform";
    }

    @PostMapping("/recipe/{id}/image")
    public String uploadImage(@PathVariable String id, @RequestParam("imageFile")MultipartFile file) {
        imageService.saveImage(Long.valueOf(id), file);
        return "redirect:/recipe/show/" + id;
    }

    @GetMapping("recipe/{id}/recipeimage")
    public void loadImage(@PathVariable String id, HttpServletResponse response) throws IOException {
        RecipeDTO recipeDTO = recipeService.findDTObyId(Long.valueOf(id));

        if (recipeDTO.getImage() != null) {
            byte[] byteArray = new byte[recipeDTO.getImage().length];
            int i = 0;

            for (Byte wrappedByte : recipeDTO.getImage()) {
                byteArray[i++] = wrappedByte;
            }

            response.setContentType("image/jpeg");
            InputStream is = new ByteArrayInputStream(byteArray);
            IOUtils.copy(is, response.getOutputStream());
        }
    }

}
