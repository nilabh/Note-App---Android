package noteapp.nilabh.com.noteapp.dbUtil;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by nilabh on 11-07-2017.
 */
public abstract class DbContentProvider {
    /**
     * The M db.
     */
    public SQLiteDatabase mDb;

    /**
     * Delete int.
     *
     * @param tableName     the table name
     * @param selection     the selection
     * @param selectionArgs the selection args
     * @return the int
     */
    public int delete(String tableName, String selection,
                      String[] selectionArgs) {
        return mDb.delete(tableName, selection, selectionArgs);
    }

    /**
     * Insert long.
     *
     * @param tableName the table name
     * @param values    the values
     * @return the long
     */
    public long insert(String tableName, ContentValues values) {
        return mDb.insert(tableName, null, values);
    }

    /**
     * Cursor to entity t.
     *
     * @param <T>    the type parameter
     * @param cursor the cursor
     * @return the t
     */
    protected abstract <T> T cursorToEntity(Cursor cursor);

    /**
     * Instantiates a new Db content provider.
     *
     * @param db the db
     */
    public DbContentProvider(SQLiteDatabase db) {
        this.mDb = db;
    }

    /**
     * Query cursor.
     *
     * @param tableName     the table name
     * @param columns       the columns
     * @param selection     the selection
     * @param selectionArgs the selection args
     * @param sortOrder     the sort order
     * @return the cursor
     */
    public Cursor query(String tableName, String[] columns,
                        String selection, String[] selectionArgs, String sortOrder) {

        final Cursor cursor = mDb.query(tableName, columns,
                selection, selectionArgs, null, null, sortOrder);

        return cursor;
    }

    /**
     * Query cursor.
     *
     * @param tableName     the table name
     * @param columns       the columns
     * @param selection     the selection
     * @param selectionArgs the selection args
     * @param sortOrder     the sort order
     * @param limit         the limit
     * @return the cursor
     */
    public Cursor query(String tableName, String[] columns,
                        String selection, String[] selectionArgs, String sortOrder,
                        String limit) {

        return mDb.query(tableName, columns, selection,
                selectionArgs, null, null, sortOrder, limit);
    }

    /**
     * Query cursor.
     *
     * @param tableName     the table name
     * @param columns       the columns
     * @param selection     the selection
     * @param selectionArgs the selection args
     * @param groupBy       the group by
     * @param having        the having
     * @param orderBy       the order by
     * @param limit         the limit
     * @return the cursor
     */
    public Cursor query(String tableName, String[] columns,
                        String selection, String[] selectionArgs, String groupBy,
                        String having, String orderBy, String limit) {

        return mDb.query(tableName, columns, selection,
                selectionArgs, groupBy, having, orderBy, limit);
    }

    /**
     * Update int.
     *
     * @param tableName     the table name
     * @param values        the values
     * @param selection     the selection
     * @param selectionArgs the selection args
     * @return the int
     */
    public int update(String tableName, ContentValues values,
                      String selection, String[] selectionArgs) {
        return mDb.update(tableName, values, selection,
                selectionArgs);
    }

    /**
     * Raw query cursor.
     *
     * @param sql           the sql
     * @param selectionArgs the selection args
     * @return the cursor
     */
    public Cursor rawQuery(String sql, String[] selectionArgs) {
        return mDb.rawQuery(sql, selectionArgs);
    }
}
