package noteapp.nilabh.com.noteapp.dbUtil;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;

import noteapp.nilabh.com.noteapp.model.NotesModel;

/**
 * Created by nilabh on 11-07-2017.
 */
public class NotesDao extends DbContentProvider
        implements NoteSchemaInterface, NotesDaoInterface {

    private Cursor cursor;
    private ContentValues initialValues;

    /**
     * Instantiates a new Notes dao.
     *
     * @param db the db
     */
    public NotesDao(SQLiteDatabase db) {
        super(db);
    }

    protected NotesModel cursorToEntity(Cursor cursor) {

        NotesModel note = new NotesModel();

        int idIndex;
        int titleIndex;
        int descIndex;
        int imageUriIndex;
        int createdDateIndex;

        if (cursor != null) {
            if (cursor.getColumnIndex(COLUMN_ID) != -1) {
                idIndex = cursor.getColumnIndexOrThrow(COLUMN_ID);
                note.setNoteId(cursor.getInt(idIndex));
            }
            if (cursor.getColumnIndex(COLUMN_TITLE) != -1) {
                titleIndex = cursor.getColumnIndexOrThrow(
                        COLUMN_TITLE);
                note.setTitle(cursor.getString(titleIndex));
            }
            if (cursor.getColumnIndex(COLUMN_DESC) != -1) {
                descIndex = cursor.getColumnIndexOrThrow(
                        COLUMN_DESC);
                note.setDescription(cursor.getString(descIndex));
            }
            if (cursor.getColumnIndex(COLUMN_IMAGE_FILE_NAME) != -1) {
                imageUriIndex = cursor.getColumnIndexOrThrow(
                        COLUMN_IMAGE_FILE_NAME);
                note.setImgFileName(cursor.getString(imageUriIndex));
            }
            if (cursor.getColumnIndex(COLUMN_DATE) != -1) {
                createdDateIndex = cursor.getColumnIndexOrThrow(
                        COLUMN_DATE);
                note.setCreatedTime(new Date(cursor.getLong(createdDateIndex)));
            }

        }
        return note;
    }

    private void setContentValue(NotesModel note) {
        initialValues = new ContentValues();
        initialValues.put(COLUMN_TITLE, note.getTitle());
        initialValues.put(COLUMN_DESC, note.getDescription());
        initialValues.put(COLUMN_IMAGE_FILE_NAME, note.getImgFileName());
        initialValues.put(COLUMN_DATE, note.getCreatedTime().getTime());
    }

    private ContentValues getContentValue() {
        return initialValues;
    }

    @Override
    public NotesModel fetchNotesById(int noteId) {
        final String selectionArgs[] = { Integer.toString(noteId) };

        final String selection = COLUMN_ID + " = ?";
        NotesModel note = new NotesModel();
        cursor = super.query(NOTES_TABLE, NOTES_COLUMNS, selection,
                selectionArgs, COLUMN_ID);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                note = cursorToEntity(cursor);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return note;
    }

    @Override
    public ArrayList<NotesModel> fetchAllNotes(String orderBy) {
        ArrayList<NotesModel> notesList = new ArrayList<>();
        cursor = super.query(NOTES_TABLE, NOTES_COLUMNS, null,
                null, orderBy);

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                NotesModel note = cursorToEntity(cursor);
                notesList.add(note);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return notesList;
    }

    @Override
    public boolean addNote(NotesModel note) {
        setContentValue(note);
        try {
            return super.insert(NOTES_TABLE, getContentValue()) > 0;
        } catch (SQLiteConstraintException ex){
            Log.w("Database", ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteNote(int noteId) {
        try {
            final String selectionArgs[] = { Integer.toString(noteId) };

            final String selection = COLUMN_ID + " = ?";
            return super.delete(NOTES_TABLE, selection, selectionArgs) > 0;
        } catch (SQLiteConstraintException ex){
            Log.w("Database", ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateNote(int noteId) {
        return false;
    }
}
