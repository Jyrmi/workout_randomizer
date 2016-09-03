package sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by jeremy on 8/18/16.
 */
public class LogsDbHelper extends SQLiteOpenHelper{
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FeedReader.db";

    private static final String ID_TYPE = "INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL";
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + LogsContract.LogEntry.TABLE_NAME + " (" +
                    LogsContract.LogEntry._ID + " " + ID_TYPE + COMMA_SEP +
                    LogsContract.LogEntry.COLUMN_EXERCISE + TEXT_TYPE + COMMA_SEP +
                    LogsContract.LogEntry.COLUMN_GROUP + TEXT_TYPE + COMMA_SEP +
                    LogsContract.LogEntry.COLUMN_DATE + TEXT_TYPE + COMMA_SEP +
                    LogsContract.LogEntry.COLUMN_STATUS + TEXT_TYPE + " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + LogsContract.LogEntry.TABLE_NAME;

    public LogsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public static void writeLogEntry(Context context, HashMap<String, String> exercise,
                                      String status) {
        // Gets the data repository in write mode
        LogsDbHelper mDbHelper = new LogsDbHelper(context);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        String dateAndTime = Calendar.getInstance().getTime().toString();
        ContentValues values = new ContentValues();
        values.put(LogsContract.LogEntry.COLUMN_EXERCISE,
                exercise.get(ExerciseContract.ExerciseEntry.COLUMN_NAME));
        values.put(LogsContract.LogEntry.COLUMN_GROUP,
                exercise.get(ExerciseContract.ExerciseEntry.COLUMN_GROUP));
        values.put(LogsContract.LogEntry.COLUMN_DATE, dateAndTime);
        values.put(LogsContract.LogEntry.COLUMN_STATUS, status);

        // Insert the new row, returning the primary key value of the new row
        db.insert(LogsContract.LogEntry.TABLE_NAME, null, values);

        db.close();
    }

    public static ArrayList<HashMap<String, String>> readLogs(Context context) {
        ArrayList<HashMap<String, String>> exercises = new ArrayList<>();
        HashMap<String, String> exerciseDetails;

        LogsDbHelper mDbHelper = new LogsDbHelper(context);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                LogsContract.LogEntry._ID,
                LogsContract.LogEntry.COLUMN_EXERCISE,
                LogsContract.LogEntry.COLUMN_GROUP,
                LogsContract.LogEntry.COLUMN_DATE,
                LogsContract.LogEntry.COLUMN_STATUS
        };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                LogsContract.LogEntry.COLUMN_DATE + " DESC";

        Cursor c = db.query(
                LogsContract.LogEntry.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                null,                                     // The columns for the WHERE clause
                null,                                     // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                String exercise = c.getString(c.getColumnIndex(LogsContract.LogEntry.COLUMN_EXERCISE));
                String group = c.getString(c.getColumnIndex(LogsContract.LogEntry.COLUMN_GROUP));
                String date = c.getString(c.getColumnIndex(LogsContract.LogEntry.COLUMN_DATE));
                String status = c.getString(c.getColumnIndex(LogsContract.LogEntry.COLUMN_STATUS));

                exerciseDetails = new HashMap<>();
                exerciseDetails.put(LogsContract.LogEntry.COLUMN_EXERCISE, exercise);
                exerciseDetails.put(LogsContract.LogEntry.COLUMN_GROUP, group);
                exerciseDetails.put(LogsContract.LogEntry.COLUMN_DATE, date);
                exerciseDetails.put(LogsContract.LogEntry.COLUMN_STATUS, status);

                exercises.add(exerciseDetails);

                c.moveToNext();
            }
        }

        c.close();
        db.close();

        return exercises;
    }
}
