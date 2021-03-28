package guru.springframework.domain.converters;

import guru.springframework.domain.Converters.NotesDTOtoEntity;
import guru.springframework.domain.Converters.NotesToDTO;
import guru.springframework.domain.DTOs.NotesDTO;
import guru.springframework.domain.Entities.Notes;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NotesConvertTest {

    private NotesToDTO notesToDTO;
    private NotesDTOtoEntity notesDTOtoEntity;
    private Notes notes;
    private NotesDTO notesDTO;

    @BeforeEach
    public void setUp() {
        notesToDTO = new NotesToDTO();
        notesDTOtoEntity = new NotesDTOtoEntity();

        notes = Notes.builder()
                .recipeNotes("something")
                .build();
        notes.setId(1L);

        notesDTO = new NotesDTO();
        notesDTO.setId(1L);
        notesDTO.setRecipeNotes("something");
    }

    @AfterEach
    public void tearDown() {
        notesDTO = null;
        notes = null;
        notesToDTO = null;
        notesDTOtoEntity = null;
    }

    @Test
    public void toDTO() {
        NotesDTO dto = notesToDTO.convert(notes);
        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("something", dto.getRecipeNotes());
        assertNull(notesToDTO.convert(null));
    }

    @Test
    public void toEntity() {
        Notes entity = notesDTOtoEntity.convert(notesDTO);
        assertNotNull(entity);
        assertEquals(1L, entity.getId());
        assertEquals("something", entity.getRecipeNotes());
        assertNull(notesDTOtoEntity.convert(null));
    }

}
