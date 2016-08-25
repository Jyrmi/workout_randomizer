package constant;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by jeremy on 8/23/16.
 */
public enum Settings {
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
}
