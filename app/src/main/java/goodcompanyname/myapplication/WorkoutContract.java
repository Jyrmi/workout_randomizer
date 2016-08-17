package goodcompanyname.myapplication;

import android.provider.BaseColumns;

/**
 * Created by jeremy on 8/17/16.
 */
public class WorkoutContract {
    public static final String DB_NAME = "goodcompanyname.workout_randomizer.db";
    public static final int DB_VERSION = 1;

    public class TaskEntry implements BaseColumns {
        public static final String TABLE = "tasks";

        public static final String COL_TIME = "time";
        public static final String COL_EXERCISES = "exercises";
    }
}
