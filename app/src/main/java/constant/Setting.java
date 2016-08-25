package constant;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by jeremy on 8/23/16.
 */
public enum Setting {
    CARDIO, OLYMPIC_WEIGHTLIFTING, PLYOMETRICS, POWERLIFTING, STRENGTH, STRETCHING,
    STRONGMAN,

    BANDS, BARBELL, BODY_ONLY, CABLE, DUMBBELL, EZ_CURL_BAR, EXERCISE_BALL, FOAM_ROLL,
    KETTLEBELLS, MACHINE, MEDICINE_BALL, NONE, OTHER,

    COMPOUND, ISOLATION, NEITHER,

    PUSH, PULL, STATIC, OTHER_FORCE,

    BEGINNER, INTERMEDIATE, EXPERT,

    YES, NO;

    @Override
    public String toString() {
        switch (this) {
            case CARDIO: return "Cardio";
            case OLYMPIC_WEIGHTLIFTING: return "Olympic Weightlifting";
            case PLYOMETRICS: return "Plyometrics";
            case POWERLIFTING: return "Powerlifting";
            case STRENGTH: return "Strength";
            case STRETCHING: return "Stretching";
            case STRONGMAN: return "Strongman";
            case BANDS: return "Bands";
            case BARBELL: return "Barbell";
            case BODY_ONLY: return "Body Only";
            case CABLE: return "Cable";
            case DUMBBELL: return "Dumbbell";
            case EZ_CURL_BAR: return "E-Z Curl Bar";
            case EXERCISE_BALL: return "Exercise Ball";
            case FOAM_ROLL: return "Foam Roll";
            case KETTLEBELLS: return "Kettlebells";
            case MACHINE: return "Machine";
            case MEDICINE_BALL: return "Medicine Ball";
            case NONE: return "None";
            case OTHER: return "Other";
            case COMPOUND: return "Compound";
            case ISOLATION: return "Isolation";
            case NEITHER: return "N/A";
            case PUSH: return "Push";
            case PULL: return "Pull";
            case STATIC: return "Static";
            case OTHER_FORCE: return "N/A Force";
            case BEGINNER: return "Beginner";
            case INTERMEDIATE: return "Intermediate";
            case EXPERT: return "Expert";
            case YES: return "Yes";
            case NO: return "No";
            default: throw new IllegalArgumentException();
        }
    }

    public static Setting toSetting(String s) {
        switch (s) {
            case "Cardio": return CARDIO;
            case "Olympic Weightlifting": return OLYMPIC_WEIGHTLIFTING;
            case "Plyometrics": return PLYOMETRICS;
            case "Powerlifting": return POWERLIFTING;
            case "Strength": return STRENGTH;
            case "Stretching": return STRETCHING;
            case "Strongman": return STRONGMAN;
            case "Bands": return BANDS;
            case "Barbell": return BARBELL;
            case "Body Only": return BODY_ONLY;
            case "Cable": return CABLE;
            case "Dumbbell": return DUMBBELL;
            case "E-Z Curl Bar": return EZ_CURL_BAR;
            case "Exercise Ball": return EXERCISE_BALL;
            case "Foam Roll": return FOAM_ROLL;
            case "Kettlebells": return KETTLEBELLS;
            case "Machine": return MACHINE;
            case "Medicine Ball": return MEDICINE_BALL;
            case "None": return NONE;
            case "Other": return OTHER;
            case "Compound": return COMPOUND;
            case "Isolation": return ISOLATION;
            case "N/A": return NEITHER;
            case "Push": return PUSH;
            case "Pull": return PULL;
            case "Static": return STATIC;
            case  "N/A Force": return OTHER_FORCE;
            case "Beginner": return BEGINNER;
            case "Intermediate": return INTERMEDIATE;
            case "Expert": return EXPERT;
            case "Yes": return YES;
            case "No": return NO;
            default: throw new IllegalArgumentException();
        }
    }

    public SettingsCategory getCategory() {
        switch(this) {
            case CARDIO: return SettingsCategory.TYPE;
            case OLYMPIC_WEIGHTLIFTING: return SettingsCategory.TYPE;
            case PLYOMETRICS: return SettingsCategory.TYPE;
            case POWERLIFTING: return SettingsCategory.TYPE;
            case STRENGTH: return SettingsCategory.TYPE;
            case STRETCHING: return SettingsCategory.TYPE;
            case STRONGMAN: return SettingsCategory.TYPE;
            case BANDS: return SettingsCategory.EQUIPMENT;
            case BARBELL: return SettingsCategory.EQUIPMENT;
            case BODY_ONLY: return SettingsCategory.EQUIPMENT;
            case CABLE: return SettingsCategory.EQUIPMENT;
            case DUMBBELL: return SettingsCategory.EQUIPMENT;
            case EZ_CURL_BAR: return SettingsCategory.EQUIPMENT;
            case EXERCISE_BALL: return SettingsCategory.EQUIPMENT;
            case FOAM_ROLL: return SettingsCategory.EQUIPMENT;
            case KETTLEBELLS: return SettingsCategory.EQUIPMENT;
            case MACHINE: return SettingsCategory.EQUIPMENT;
            case MEDICINE_BALL: return SettingsCategory.EQUIPMENT;
            case NONE: return SettingsCategory.EQUIPMENT;
            case OTHER: return SettingsCategory.EQUIPMENT;
            case COMPOUND: return SettingsCategory.MECHANICS;
            case ISOLATION: return SettingsCategory.MECHANICS;
            case NEITHER: return SettingsCategory.MECHANICS;
            case PUSH: return SettingsCategory.FORCE;
            case PULL: return SettingsCategory.FORCE;
            case STATIC: return SettingsCategory.FORCE;
            case OTHER_FORCE: return SettingsCategory.FORCE;
            case BEGINNER: return SettingsCategory.DIFFICULTY;
            case INTERMEDIATE: return SettingsCategory.DIFFICULTY;
            case EXPERT: return SettingsCategory.DIFFICULTY;
            case YES: return SettingsCategory.SPORTS;
            case NO: return SettingsCategory.SPORTS;
            default: throw new IllegalArgumentException();
        }
    }
}
