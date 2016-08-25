package sqlite;

import android.provider.BaseColumns;

/**
 * Created by jeremy on 8/18/16.
 */
public class LogsContract {
    public LogsContract() {}

    public static abstract class LogEntry implements BaseColumns {
        public static final String TABLE_NAME = "logs";
        public static final String COLUMN_EXERCISE = "exercise";
        public static final String COLUMN_GROUP = "musclegroup";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_STATUS = "status";
    }
}
