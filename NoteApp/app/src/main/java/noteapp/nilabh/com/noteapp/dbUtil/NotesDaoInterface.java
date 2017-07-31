package noteapp.nilabh.com.noteapp.dbUtil;

import java.util.List;

import noteapp.nilabh.com.noteapp.model.NotesModel;

/**
 * Created by nilabh on 11-07-2017.
 */
public interface NotesDaoInterface {
    /**
     * Fetch notes by id notes model.
     *
     * @param noteId the note id
     * @return the notes model
     */
    public NotesModel fetchNotesById(int noteId);

    /**
     * Fetch all notes list.
     *
     * @param orderBy the order by
     * @return the list
     */
    public List<NotesModel> fetchAllNotes(String orderBy);

    /**
     * Add note boolean.
     *
     * @param note the note
     * @return the boolean
     */
    public boolean addNote(NotesModel note);

    /**
     * Delete note boolean.
     *
     * @param noteId the note id
     * @return the boolean
     */
    public boolean deleteNote(int noteId);

    /**
     * Update note boolean.
     *
     * @param noteId the note id
     * @return the boolean
     */
    public boolean updateNote(int noteId);
}
