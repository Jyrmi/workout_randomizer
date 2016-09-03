package preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import constant.MuscleGroup;
import constant.PreferenceTags;
import constant.TwoTuple;

/**
 * Created by jeremy on 8/27/16.
 */
public class MySharedPrefs {
    private static void clear(SharedPreferences sp) {
        sp.edit().clear().apply();
    }

    public static void clearDefault(Context context) {
        clear(PreferenceManager.getDefaultSharedPreferences(context));
    }

    /**
     * Clears a shared preference file of the specified tag.
     * @param sharedPreferencesTag the string representing the preferences file.
     */
    public static void clearByTag(Context context, String sharedPreferencesTag) {
        clear(context.getSharedPreferences(sharedPreferencesTag, Context.MODE_PRIVATE));
    }

    public static void clearAll(Context context) {
        clearDefault(context);
        clearByTag(context, PreferenceTags.PREFERENCES_SELECTED_MUSCLE_GROUPS);
    }

    public static void putString(Context context, String tag, String key, String value) {
        context.getSharedPreferences(tag, Context.MODE_PRIVATE)
                .edit().putString(key, value).apply();
    }

    public static boolean getDefaultBool(Context context, String key) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(key, false);
    }

    public static void putDefaultBool(Context context, String key, Boolean value) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit().putBoolean(key, value).apply();
    }

    public static void finishFirstRun(Context context) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean("firstrun", false)
                .apply();
    }

    public static ArrayList<String> getFavoriteGroups(Context context) {
        ArrayList<String> mostSelected = new ArrayList<>();
        int maxPicked = 0;

        SharedPreferences sharedPreferences = context.getSharedPreferences(
                PreferenceTags.PREFERENCES_SELECTED_MUSCLE_GROUPS, Context.MODE_PRIVATE);

        int count;
        for (String muscleGroup : MuscleGroup.getGroupsAsStrings()) {
            count = sharedPreferences.getInt(muscleGroup, 0);
            if (count > 0) {
                if (count > maxPicked) {
                    maxPicked = count;
                    mostSelected.clear();
                    mostSelected.add(muscleGroup);
                } else if (count == maxPicked) {
                    mostSelected.add(muscleGroup);
                }
            }
        }

        return mostSelected;
    }
}
