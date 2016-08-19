package sqlite;

import android.provider.BaseColumns;

/**
 * Created by jeremy on 8/18/16.
 */
public class WorkoutContract {
    public WorkoutContract() {}

    public static abstract class WorkoutEntry implements BaseColumns {
        public static final String TABLE_NAME = "workout";
        public static final String COLUMN_WORKOUT = "exercises";
        public static final String COLUMN_DATE = "date";
    }
}
