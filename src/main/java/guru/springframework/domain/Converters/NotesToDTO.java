package guru.springframework.domain.Converters;

import guru.springframework.domain.DTOs.NotesDTO;
import guru.springframework.domain.Entities.Notes;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class NotesToDTO implements Converter<Notes, NotesDTO> {

    @Synchronized
    @Nullable
    @Override
    public NotesDTO convert(@Nullable Notes notes) {
        return Optional.ofNullable(notes)
                .map(nt -> {
                    final NotesDTO notesDTO = new NotesDTO();
                    notesDTO.setId(nt.getId());
                    notesDTO.setRecipeNotes(nt.getRecipeNotes());
                    return notesDTO;
                })
                .orElse(null);
    }

}
