package guru.springframework.domain;

import lombok.*;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@ToString(exclude = {"ingredients", "image", "notes", "categories"})
@EqualsAndHashCode(exclude = {"ingredients", "image", "notes", "categories"})

@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String description;

    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;

    @Lob
    private String directions;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    private Set<Ingredient> ingredients = new HashSet<>();

    @Lob
    private Byte[] image;

    @Enumerated(value = EnumType.STRING)
    private Difficulty difficulty;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "notes_id")
    private Notes notes;

    @ManyToMany
    @JoinTable(name = "recipe_category",
        joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>();

    @Builder
    public Recipe(String description, Integer prepTime, Integer cookTime, Integer servings, String source,
                  String url, String directions, Byte[] image,
                  Difficulty difficulty, Notes notes) {
        this.description = description;
        this.prepTime = prepTime;
        this.cookTime = cookTime;
        this.servings = servings;
        this.source = source;
        this.url = url;
        this.directions = directions;
        this.image = image;
        this.difficulty = difficulty;
        this.notes = notes;
    }

    public void setNotes(Notes notes) {
        this.notes = notes;
        notes.setRecipe(this);
    }

    public void addIngredient(Ingredient ingredient){
        ingredient.setRecipe(this);
        this.ingredients.add(ingredient);
    }

}
