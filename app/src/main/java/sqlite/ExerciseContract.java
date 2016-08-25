package sqlite;

import android.provider.BaseColumns;

/**
 * Created by jeremy on 8/18/16.
 */
public class ExerciseContract {
    public ExerciseContract() {}

    public static abstract class ExerciseEntry implements BaseColumns {
        public static final String TABLE_NAME = "exercise";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_GROUP = "musclegroup";
        public static final String COLUMN_OTHER_GROUPS = "othergroups";
        public static final String COLUMN_DIFFICULTY = "level";
        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_MECHANICS = "mechanics";
        public static final String COLUMN_EQUIPMENT = "equipment";
        public static final String COLUMN_FORCE = "force";
        public static final String COLUMN_SPORT = "sport";
        public static final String COLUMN_RATING = "rating";
        public static final String COLUMN_MALE_VIDEO = "malevideo";
        public static final String COLUMN_MALE_IMAGES = "maleimages";
        public static final String COLUMN_FEMALE_VIDEO = "femalevideo";
        public static final String COLUMN_FEMALE_IMAGES = "femaleimages";
    }
}
