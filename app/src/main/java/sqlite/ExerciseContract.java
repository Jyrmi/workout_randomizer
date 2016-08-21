package sqlite;

import android.provider.BaseColumns;

/**
 * Created by jeremy on 8/18/16.
 */
public class ExerciseContract {
    public ExerciseContract() {}

    public static abstract class ExerciseEntry implements BaseColumns {
        public static final String TABLE_NAME = "exercises";
        public static final String COLUMN_EXERCISE = "exercise";
        public static final String COLUMN_GROUP = "musclegroup";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_STATUS = "status";
    }
}
