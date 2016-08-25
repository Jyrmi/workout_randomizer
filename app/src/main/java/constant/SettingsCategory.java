package constant;

import sqlite.ExerciseContract;

/**
 * Created by jeremy on 8/25/16.
 */
public enum SettingsCategory {
    TYPE, EQUIPMENT, MECHANICS, FORCE, DIFFICULTY, SPORTS;

    @Override
    public String toString() {
        switch (this) {
            case TYPE: return ExerciseContract.ExerciseEntry.COLUMN_TYPE;
            case EQUIPMENT: return ExerciseContract.ExerciseEntry.COLUMN_EQUIPMENT;
            case MECHANICS: return ExerciseContract.ExerciseEntry.COLUMN_MECHANICS;
            case FORCE: return ExerciseContract.ExerciseEntry.COLUMN_FORCE;
            case DIFFICULTY: return ExerciseContract.ExerciseEntry.COLUMN_DIFFICULTY;
            case SPORTS: return ExerciseContract.ExerciseEntry.COLUMN_SPORT;
            default: throw new IllegalArgumentException();
        }
    }
}
