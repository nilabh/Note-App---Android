package noteapp.nilabh.com.noteapp.dbUtil;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLException;


/**
 * Created by nilabh on 11-07-2017.
 */
public class DataBaseHandler {

    private static final String TAG = "MyDatabase";
    private static final String DATABASE_NAME = "my_database.db";
    private DatabaseHelper mDbHelper;
    private static final int DATABASE_VERSION = 1;
    private final Context mContext;
    /**
     * The constant mNotesDao.
     */
    public static NotesDao mNotesDao;


    /**
     * Open data base handler.
     *
     * @return the data base handler
     * @throws SQLException the sql exception
     */
    public DataBaseHandler open() throws SQLException {
        mDbHelper = new DatabaseHelper(mContext);
        SQLiteDatabase mDb = mDbHelper.getWritableDatabase();
        mNotesDao = new NotesDao(mDb);
        return this;
    }

    /**
     * Close.
     */
    public void close() {
        mDbHelper.close();
    }

    /**
     * Instantiates a new Data base handler.
     *
     * @param context the context
     */
    public DataBaseHandler(Context context) {
        this.mContext = context;
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        /**
         * Instantiates a new Database helper.
         *
         * @param context the context
         */
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(NoteSchemaInterface.NOTE_TABLE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion,
                              int newVersion) {
        }
    }

}
