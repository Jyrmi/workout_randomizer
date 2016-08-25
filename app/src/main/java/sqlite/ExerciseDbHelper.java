package sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jeremy on 8/18/16.
 */
public class ExerciseDbHelper extends SQLiteOpenHelper{
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FeedReader.db";

    private static final String ID_TYPE = "INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL";
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ExerciseContract.ExerciseEntry.TABLE_NAME + " (" +
                    ExerciseContract.ExerciseEntry._ID + " " + ID_TYPE + COMMA_SEP +
                    ExerciseContract.ExerciseEntry.COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
                    ExerciseContract.ExerciseEntry.COLUMN_GROUP + TEXT_TYPE + COMMA_SEP +
                    ExerciseContract.ExerciseEntry.COLUMN_OTHER_GROUPS + TEXT_TYPE + COMMA_SEP +
                    ExerciseContract.ExerciseEntry.COLUMN_DIFFICULTY + TEXT_TYPE + COMMA_SEP +
                    ExerciseContract.ExerciseEntry.COLUMN_TYPE + TEXT_TYPE + COMMA_SEP +
                    ExerciseContract.ExerciseEntry.COLUMN_MECHANICS + TEXT_TYPE + COMMA_SEP +
                    ExerciseContract.ExerciseEntry.COLUMN_EQUIPMENT + TEXT_TYPE + COMMA_SEP +
                    ExerciseContract.ExerciseEntry.COLUMN_FORCE + TEXT_TYPE + COMMA_SEP +
                    ExerciseContract.ExerciseEntry.COLUMN_SPORT + TEXT_TYPE + COMMA_SEP +
                    ExerciseContract.ExerciseEntry.COLUMN_RATING + TEXT_TYPE + COMMA_SEP +
                    ExerciseContract.ExerciseEntry.COLUMN_MALE_VIDEO + TEXT_TYPE + COMMA_SEP +
                    ExerciseContract.ExerciseEntry.COLUMN_MALE_IMAGES + TEXT_TYPE + COMMA_SEP +
                    ExerciseContract.ExerciseEntry.COLUMN_FEMALE_VIDEO + TEXT_TYPE + COMMA_SEP +
                    ExerciseContract.ExerciseEntry.COLUMN_FEMALE_IMAGES + TEXT_TYPE + " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ExerciseContract.ExerciseEntry.TABLE_NAME;

    public ExerciseDbHelper(Context context) {
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
}
