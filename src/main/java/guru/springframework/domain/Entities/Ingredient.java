package guru.springframework.domain.Entities;

import lombok.*;
import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@ToString(exclude = {"uom", "recipe"})
@EqualsAndHashCode(exclude = {"uom", "recipe"})

@Entity
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String description;

    private BigDecimal amount;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "uom_id")
    private UnitOfMeasure uom;

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @Builder
    public Ingredient(String description, BigDecimal amount, UnitOfMeasure uom, Recipe recipe) {
        this.description = description;
        this.amount = amount;
        this.uom = uom;
        this.recipe = recipe;
    }

}
