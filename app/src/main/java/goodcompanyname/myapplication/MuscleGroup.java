package goodcompanyname.myapplication;

import java.util.ArrayList;

/**
 * Created by jeremy on 8/9/16.
 */
public enum MuscleGroup {
    ABS, BICEPS, CHEST, FOREARMS, CALVES, GLUTES, HAMSTRINGS, LATS, LOWER_BACK, MIDDLE_BACK,
    SHOULDERS, TRAPS, TRICEPS, NECK, QUADS;

    @Override
    public String toString() {
        switch (this) {
            case ABS: return "Abs";
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
            case QUADS: return "Quads";
            default: throw new IllegalArgumentException();
        }
    }

    public ArrayList<String> getExercises() {
        switch (this) {
            case ABS:
                ArrayList<String> absExercises = new ArrayList<>();
                absExercises.add("LANDMINE 180\'S");
                absExercises.add("ONE-ARM MEDICINE BALL SLAM");
                return absExercises;
            case BICEPS:
                ArrayList<String> bicepsExercises = new ArrayList<>();
                bicepsExercises.add("BICEPS CURL TO SHOULDER PRESS");
                bicepsExercises.add("INCLINE HAMMER CURLS");
                return bicepsExercises;
            case CHEST:
                ArrayList<String> chestExercises = new ArrayList<>();
                chestExercises.add("PUSHUPS");
                chestExercises.add("BARBELL BENCH PRESS - MEDIUM GRIP");
                return chestExercises;
            case FOREARMS:
                ArrayList<String> forearmsExercises = new ArrayList<>();
                forearmsExercises.add("RICKSHAW CARRY");
                forearmsExercises.add("FARMER\'S WALK");
                return forearmsExercises;
            case CALVES:
                ArrayList<String> calvesExercises = new ArrayList<>();
                calvesExercises.add("SMITH MACHINE CALF RAISE");
                calvesExercises.add("STANDING CALF RAISES");
                return calvesExercises;
            case GLUTES:
                ArrayList<String> glutesExercises = new ArrayList<>();
                glutesExercises.add("BARBELL GLUTE BRIDGE");
                glutesExercises.add("BARBELL HIP THRUST");
                return glutesExercises;
            case HAMSTRINGS:
                ArrayList<String> hamstringsExercises = new ArrayList<>();
                hamstringsExercises.add("CLEAN DEADLIFT");
                hamstringsExercises.add("HANG SNATCH");
                return hamstringsExercises;
            case LATS:
                ArrayList<String> latsExercises = new ArrayList<>();
                latsExercises.add("WIDE-GRIP PULL-UP");
                latsExercises.add("WEIGHTED PULL UPS");
                return latsExercises;
            case LOWER_BACK:
                ArrayList<String> lowerbackExercises = new ArrayList<>();
                lowerbackExercises.add("ATLAS STONES");
                lowerbackExercises.add("DEFICIT DEADLIFT");
                return lowerbackExercises;
            case MIDDLE_BACK:
                ArrayList<String> middlebackExercises = new ArrayList<>();
                middlebackExercises.add("T-BAR ROW WITH HANDLE");
                middlebackExercises.add("PENDLAY ROWN");
                return middlebackExercises;
            case SHOULDERS:
                ArrayList<String> shouldersExercises = new ArrayList<>();
                shouldersExercises.add("CLEAN AND PRESS");
                shouldersExercises.add("DUMBBELL REAR DELT ROW");
                return shouldersExercises;
            case TRAPS:
                ArrayList<String> trapsExercises = new ArrayList<>();
                trapsExercises.add("SMITH MACHINE SHRUG");
                trapsExercises.add("LEVERAGE SHRUG");
                return trapsExercises;
            case TRICEPS:
                ArrayList<String> tricepsExercises = new ArrayList<>();
                tricepsExercises.add("DECLINE EZ BAR TRICEPS EXTENSION");
                tricepsExercises.add("PARALLEL BAR DIP");
                return tricepsExercises;
            case NECK:
                ArrayList<String> neckExercises = new ArrayList<>();
                neckExercises.add("LYING FACE DOWN PLATE NECK RESISTANCE");
                neckExercises.add("LYING FACE UP PLATE NECK RESISTANCE");
                return neckExercises;
            case QUADS:
                ArrayList<String> quadsExercises = new ArrayList<>();
                quadsExercises.add("CLEAN FROM BLOCKS");
                quadsExercises.add("SINGLE-LEG PRESS");
                return quadsExercises;
            default: throw new IllegalArgumentException();
        }
    }
}
