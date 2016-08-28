package sqlite;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import constant.PreferenceTags;
import constant.TwoTuple;

/**
 * Created by jeremy on 8/23/16.
 */
public class ExerciseDb extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "exercise.db";
    private static final int DATABASE_VERSION = 1;

    public ExerciseDb(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static ArrayList<HashMap<String, String>> queryGroup(Context context,
                                                                String muscleGroup) {
        ArrayList<HashMap<String, String>> exercises = new ArrayList<>();
        HashMap<String, String> exerciseDetails;

        ExerciseDb exerciseDb = new ExerciseDb(context);
        SQLiteDatabase db = exerciseDb.getReadableDatabase();


        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                ExerciseContract.ExerciseEntry.COLUMN_NAME,
                ExerciseContract.ExerciseEntry.COLUMN_GROUP,
                ExerciseContract.ExerciseEntry.COLUMN_OTHER_GROUPS,
                ExerciseContract.ExerciseEntry.COLUMN_DIFFICULTY,
                ExerciseContract.ExerciseEntry.COLUMN_TYPE,
                ExerciseContract.ExerciseEntry.COLUMN_MECHANICS,
                ExerciseContract.ExerciseEntry.COLUMN_EQUIPMENT,
                ExerciseContract.ExerciseEntry.COLUMN_FORCE,
                ExerciseContract.ExerciseEntry.COLUMN_SPORT,
                ExerciseContract.ExerciseEntry.COLUMN_RATING,
                ExerciseContract.ExerciseEntry.COLUMN_MALE_VIDEO,
                ExerciseContract.ExerciseEntry.COLUMN_MALE_IMAGES,
                ExerciseContract.ExerciseEntry.COLUMN_FEMALE_VIDEO,
                ExerciseContract.ExerciseEntry.COLUMN_FEMALE_IMAGES
        };

        // Get the settings to create the WHERE clause
        TwoTuple<String, ArrayList<String>> whereClause = generateWhereClause(context);
        String whereSQL = whereClause.a;
        ArrayList<String> whereArgs = whereClause.b;
        whereArgs.add(0, muscleGroup);

        // WHERE clause
        String selection = ExerciseContract.ExerciseEntry.COLUMN_GROUP + "=? AND " + whereSQL;

        // Arguments to the WHERE clause
        String[] selectionArgs = whereArgs.toArray(new String[0]);

        Cursor c = db.query(
                ExerciseContract.ExerciseEntry.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );

        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                exerciseDetails = new HashMap<>();

                String exercise = c.getString(c.getColumnIndex(ExerciseContract.ExerciseEntry.COLUMN_NAME));
                String group = c.getString(c.getColumnIndex(ExerciseContract.ExerciseEntry.COLUMN_GROUP));
                String otherGroups = c.getString(c.getColumnIndex(ExerciseContract.ExerciseEntry.COLUMN_OTHER_GROUPS));
                String level = c.getString(c.getColumnIndex(ExerciseContract.ExerciseEntry.COLUMN_DIFFICULTY));
                String type = c.getString(c.getColumnIndex(ExerciseContract.ExerciseEntry.COLUMN_TYPE));
                String mechanics = c.getString(c.getColumnIndex(ExerciseContract.ExerciseEntry.COLUMN_MECHANICS));
                String equipment = c.getString(c.getColumnIndex(ExerciseContract.ExerciseEntry.COLUMN_EQUIPMENT));
                String force = c.getString(c.getColumnIndex(ExerciseContract.ExerciseEntry.COLUMN_FORCE));
                String sport = c.getString(c.getColumnIndex(ExerciseContract.ExerciseEntry.COLUMN_SPORT));
                String rating = c.getString(c.getColumnIndex(ExerciseContract.ExerciseEntry.COLUMN_RATING));
                String mVideo = c.getString(c.getColumnIndex(ExerciseContract.ExerciseEntry.COLUMN_MALE_VIDEO));
                String mImages = c.getString(c.getColumnIndex(ExerciseContract.ExerciseEntry.COLUMN_MALE_IMAGES));
                String fVideo = c.getString(c.getColumnIndex(ExerciseContract.ExerciseEntry.COLUMN_FEMALE_VIDEO));
                String fImages = c.getString(c.getColumnIndex(ExerciseContract.ExerciseEntry.COLUMN_FEMALE_IMAGES));

                exerciseDetails.put(ExerciseContract.ExerciseEntry.COLUMN_NAME, exercise);
                exerciseDetails.put(ExerciseContract.ExerciseEntry.COLUMN_GROUP, group);
                exerciseDetails.put(ExerciseContract.ExerciseEntry.COLUMN_OTHER_GROUPS, otherGroups);
                exerciseDetails.put(ExerciseContract.ExerciseEntry.COLUMN_DIFFICULTY, level);
                exerciseDetails.put(ExerciseContract.ExerciseEntry.COLUMN_TYPE, type);
                exerciseDetails.put(ExerciseContract.ExerciseEntry.COLUMN_MECHANICS, mechanics);
                exerciseDetails.put(ExerciseContract.ExerciseEntry.COLUMN_EQUIPMENT, equipment);
                exerciseDetails.put(ExerciseContract.ExerciseEntry.COLUMN_FORCE, force);
                exerciseDetails.put(ExerciseContract.ExerciseEntry.COLUMN_SPORT, sport);
                exerciseDetails.put(ExerciseContract.ExerciseEntry.COLUMN_RATING, rating);
                exerciseDetails.put(ExerciseContract.ExerciseEntry.COLUMN_MALE_VIDEO, mVideo);
                exerciseDetails.put(ExerciseContract.ExerciseEntry.COLUMN_MALE_IMAGES, mImages);
                exerciseDetails.put(ExerciseContract.ExerciseEntry.COLUMN_FEMALE_VIDEO, fVideo);
                exerciseDetails.put(ExerciseContract.ExerciseEntry.COLUMN_FEMALE_IMAGES, fImages);

                exercises.add(exerciseDetails);

                c.moveToNext();
            }
        }

        c.close();
        db.close();

        return exercises;
    }

    public static HashMap<String, String> queryExercise(Context context, String exerciseName) {
        HashMap<String, String> exerciseDetails = new HashMap<>();

        ExerciseDb exerciseDb = new ExerciseDb(context);
        SQLiteDatabase db = exerciseDb.getReadableDatabase();


        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                ExerciseContract.ExerciseEntry.COLUMN_NAME,
                ExerciseContract.ExerciseEntry.COLUMN_GROUP,
                ExerciseContract.ExerciseEntry.COLUMN_OTHER_GROUPS,
                ExerciseContract.ExerciseEntry.COLUMN_DIFFICULTY,
                ExerciseContract.ExerciseEntry.COLUMN_TYPE,
                ExerciseContract.ExerciseEntry.COLUMN_MECHANICS,
                ExerciseContract.ExerciseEntry.COLUMN_EQUIPMENT,
                ExerciseContract.ExerciseEntry.COLUMN_FORCE,
                ExerciseContract.ExerciseEntry.COLUMN_SPORT,
                ExerciseContract.ExerciseEntry.COLUMN_RATING,
                ExerciseContract.ExerciseEntry.COLUMN_MALE_VIDEO,
                ExerciseContract.ExerciseEntry.COLUMN_MALE_IMAGES,
                ExerciseContract.ExerciseEntry.COLUMN_FEMALE_VIDEO,
                ExerciseContract.ExerciseEntry.COLUMN_FEMALE_IMAGES
        };

        // WHERE clause
        String selection = ExerciseContract.ExerciseEntry.COLUMN_NAME + "=?";

        // Arguments to the WHERE clause
        String[] selectionArgs = {exerciseName};

        Cursor c = db.query(
                ExerciseContract.ExerciseEntry.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );

        if (c.moveToFirst()) {
            exerciseDetails = new HashMap<>();

            String exercise = c.getString(c.getColumnIndex(ExerciseContract.ExerciseEntry.COLUMN_NAME));
            String group = c.getString(c.getColumnIndex(ExerciseContract.ExerciseEntry.COLUMN_GROUP));
            String otherGroups = c.getString(c.getColumnIndex(ExerciseContract.ExerciseEntry.COLUMN_OTHER_GROUPS));
            String level = c.getString(c.getColumnIndex(ExerciseContract.ExerciseEntry.COLUMN_DIFFICULTY));
            String type = c.getString(c.getColumnIndex(ExerciseContract.ExerciseEntry.COLUMN_TYPE));
            String mechanics = c.getString(c.getColumnIndex(ExerciseContract.ExerciseEntry.COLUMN_MECHANICS));
            String equipment = c.getString(c.getColumnIndex(ExerciseContract.ExerciseEntry.COLUMN_EQUIPMENT));
            String force = c.getString(c.getColumnIndex(ExerciseContract.ExerciseEntry.COLUMN_FORCE));
            String sport = c.getString(c.getColumnIndex(ExerciseContract.ExerciseEntry.COLUMN_SPORT));
            String rating = c.getString(c.getColumnIndex(ExerciseContract.ExerciseEntry.COLUMN_RATING));
            String mVideo = c.getString(c.getColumnIndex(ExerciseContract.ExerciseEntry.COLUMN_MALE_VIDEO));
            String mImages = c.getString(c.getColumnIndex(ExerciseContract.ExerciseEntry.COLUMN_MALE_IMAGES));
            String fVideo = c.getString(c.getColumnIndex(ExerciseContract.ExerciseEntry.COLUMN_FEMALE_VIDEO));
            String fImages = c.getString(c.getColumnIndex(ExerciseContract.ExerciseEntry.COLUMN_FEMALE_IMAGES));

            exerciseDetails.put(ExerciseContract.ExerciseEntry.COLUMN_NAME, exercise);
            exerciseDetails.put(ExerciseContract.ExerciseEntry.COLUMN_GROUP, group);
            exerciseDetails.put(ExerciseContract.ExerciseEntry.COLUMN_OTHER_GROUPS, otherGroups);
            exerciseDetails.put(ExerciseContract.ExerciseEntry.COLUMN_DIFFICULTY, level);
            exerciseDetails.put(ExerciseContract.ExerciseEntry.COLUMN_TYPE, type);
            exerciseDetails.put(ExerciseContract.ExerciseEntry.COLUMN_MECHANICS, mechanics);
            exerciseDetails.put(ExerciseContract.ExerciseEntry.COLUMN_EQUIPMENT, equipment);
            exerciseDetails.put(ExerciseContract.ExerciseEntry.COLUMN_FORCE, force);
            exerciseDetails.put(ExerciseContract.ExerciseEntry.COLUMN_SPORT, sport);
            exerciseDetails.put(ExerciseContract.ExerciseEntry.COLUMN_RATING, rating);
            exerciseDetails.put(ExerciseContract.ExerciseEntry.COLUMN_MALE_VIDEO, mVideo);
            exerciseDetails.put(ExerciseContract.ExerciseEntry.COLUMN_MALE_IMAGES, mImages);
            exerciseDetails.put(ExerciseContract.ExerciseEntry.COLUMN_FEMALE_VIDEO, fVideo);
            exerciseDetails.put(ExerciseContract.ExerciseEntry.COLUMN_FEMALE_IMAGES, fImages);

            c.moveToNext();
        }

        c.close();
        db.close();

        return exerciseDetails;
    }

    public static TwoTuple<String, ArrayList<String>> generateWhereClause(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                PreferenceTags.PREFERENCES_SETTINGS, Context.MODE_PRIVATE);

        ArrayList<String> whereClauseSections = new ArrayList<>();
        ArrayList<String> queryArgs = new ArrayList<>();

        HashMap<String, String> settings = (HashMap) sharedPreferences.getAll();
        HashMap<String, ArrayList<String>> categories = new HashMap<>();

        for (Map.Entry<String, String> entry : settings.entrySet()) {
            String category = entry.getValue();

            if (!category.equals("Gender")) { // Gender is not a column, so it shouldn't be in the where clause.
                if (!categories.containsKey(category)) {
                    categories.put(category, new ArrayList<String>());
                }
                categories.get(category).add(entry.getKey());
            }
        }

        for (Map.Entry<String, ArrayList<String>> entry : categories.entrySet()) {
            ArrayList<String> whereStatements = new ArrayList<>();
            for (String setting : entry.getValue()) {
                whereStatements.add(entry.getKey() + "=?");
                queryArgs.add(setting);
            }
            whereClauseSections.add("(" + TextUtils.join(" OR ", whereStatements) + ")");
        }

        String fullWhereClause = TextUtils.join(" AND ", whereClauseSections);

        return new TwoTuple<>(fullWhereClause, queryArgs);
    }
}
