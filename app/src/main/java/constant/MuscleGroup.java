package constant;

import java.util.ArrayList;

/**
 * Created by jeremy on 8/9/16.
 */
public enum MuscleGroup {
    ABS, ABDUCTORS, ADDUCTORS, BICEPS, CHEST, FOREARMS, CALVES, GLUTES, HAMSTRINGS, LATS,
    LOWER_BACK, MIDDLE_BACK, SHOULDERS, TRAPS, TRICEPS, NECK, QUADS;

    public static ArrayList<String> getGroupsAsStrings() {
        ArrayList<String> names = new ArrayList<>();
        for (MuscleGroup value : values()) {
            names.add(value.toString());
        }

        return names;
    }

    @Override
    public String toString() {
        switch (this) {
            case ABS: return "Abdominals";
            case ABDUCTORS: return "Abductors";
            case ADDUCTORS: return "Adductors";
            case BICEPS: return "Biceps";
            case CHEST: return "Chest";
            case FOREARMS: return "Forearms";
            case CALVES: return "Calves";
            case GLUTES: return "Glutes";
            case HAMSTRINGS: return "Hamstrings";
            case LATS: return "Lats";
            case LOWER_BACK: return "Lower Back";
            case MIDDLE_BACK: return "Middle Back";
            case SHOULDERS: return "Shoulders";
            case TRAPS: return "Traps";
            case TRICEPS: return "Triceps";
            case NECK: return "Neck";
            case QUADS: return "Quadriceps";
            default: throw new IllegalArgumentException();
        }
    }
}
