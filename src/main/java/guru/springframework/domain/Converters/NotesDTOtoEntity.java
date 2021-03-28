package guru.springframework.domain.Converters;

import guru.springframework.domain.DTOs.NotesDTO;
import guru.springframework.domain.Entities.Notes;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class NotesDTOtoEntity implements Converter<NotesDTO, Notes> {

    @Synchronized
    @Nullable
    @Override
    public Notes convert(@Nullable NotesDTO notesToDTO) {
        return Optional.ofNullable(notesToDTO)
                .map(ndto -> {
                    final Notes notes = Notes.builder()
                            .recipeNotes(ndto.getRecipeNotes())
                            .build();
                    notes.setId(ndto.getId());
                    return notes;
                })
                .orElse(null);
    }

}
