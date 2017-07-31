package noteapp.nilabh.com.noteapp.dbUtil;

/**
 * Created by nilabh on 11-07-2017.
 */
public interface NoteSchemaInterface {

    /**
     * The constant NOTES_TABLE.
     */
    String NOTES_TABLE = "notes";
    /**
     * The constant COLUMN_ID.
     */
    String COLUMN_ID = "_id";
    /**
     * The constant COLUMN_TITLE.
     */
    String COLUMN_TITLE = "title";
    /**
     * The constant COLUMN_DESC.
     */
    String COLUMN_DESC = "description";
    /**
     * The constant COLUMN_IMAGE_FILE_NAME.
     */
    String COLUMN_IMAGE_FILE_NAME = "image_uri";
    /**
     * The constant COLUMN_DATE.
     */
    String COLUMN_DATE = "created_date";
    /**
     * The constant NOTE_TABLE_CREATE.
     */
    String NOTE_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS "
            + NOTES_TABLE
            + " ("
            + COLUMN_ID
            + " INTEGER PRIMARY KEY, "
            + COLUMN_TITLE
            + " TEXT NOT NULL, "
            + COLUMN_DESC
            + " TEXT NOT NULL, "
            + COLUMN_IMAGE_FILE_NAME
            + " TEXT, "
            + COLUMN_DATE
            + " BIGINT "
            + ")";

    /**
     * The constant NOTES_COLUMNS.
     */
    String[]NOTES_COLUMNS = new String[]{COLUMN_ID,
            COLUMN_TITLE, COLUMN_DESC, COLUMN_IMAGE_FILE_NAME,COLUMN_DATE};
}
