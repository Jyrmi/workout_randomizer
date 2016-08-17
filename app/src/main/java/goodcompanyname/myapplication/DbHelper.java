package goodcompanyname.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jeremy on 8/17/16.
 */
public class DbHelper extends SQLiteOpenHelper {

    public DbHelper(Context context) {
        super(context, WorkoutContract.DB_NAME, null, WorkoutContract.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + WorkoutContract.TaskEntry.TABLE + " ( " +
                WorkoutContract.TaskEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WorkoutContract.TaskEntry.COL_TIME + " TEXT NOT NULL, " +
                WorkoutContract.TaskEntry.COL_EXERCISES + " TEXT NOT NULL);";

        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + WorkoutContract.TaskEntry.TABLE);
        onCreate(db);
    }
}