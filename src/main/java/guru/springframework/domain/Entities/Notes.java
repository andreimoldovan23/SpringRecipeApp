package guru.springframework.domain.Entities;

import lombok.*;
import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@ToString(exclude = {"recipe"})
@EqualsAndHashCode(exclude = {"recipe"})

@Entity
public class Notes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Recipe recipe;

    @Lob
    private String recipeNotes;

    @Builder
    public Notes(Recipe recipe, String recipeNotes) {
        this.recipe = recipe;
        this.recipeNotes = recipeNotes;
    }

}
