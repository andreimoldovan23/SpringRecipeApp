package guru.springframework.domain;

import lombok.*;
import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@ToString
@EqualsAndHashCode

@Entity
public class UnitOfMeasure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String description;

    @Builder
    public UnitOfMeasure(String description) {
        this.description = description;
    }

}
