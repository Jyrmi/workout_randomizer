package constant;

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
                absExercises.add("BOTTOMS UP");
                absExercises.add("PLANK");
                absExercises.add("SPELL CASTER");
                absExercises.add("SUSPENDED FALLOUT");
                absExercises.add("WEIGHTED SUITCASE CRUNCH");
                absExercises.add("DECLINE REVERSE CRUNCH");
                absExercises.add("SPIDER CRAWL");
                absExercises.add("STANDING CABLE LIFT");
                absExercises.add("BARBELL AB ROLLOUT");
                absExercises.add("COCOONS");
                absExercises.add("CROSS-BODY CRUNCH");
                absExercises.add("ELBOW TO KNEE");
                absExercises.add("ONE-ARM HIGH-PULLEY CABLE SIDE BENDS");
                return absExercises;
            case BICEPS:
                ArrayList<String> bicepsExercises = new ArrayList<>();
                bicepsExercises.add("BICEPS CURL TO SHOULDER PRESS");
                bicepsExercises.add("INCLINE HAMMER CURLS");
                bicepsExercises.add("WIDE-GRIP STANDING BARBELL CURL");
                bicepsExercises.add("SPIDER CURL");
                bicepsExercises.add("EZ-BAR CURL");
                bicepsExercises.add("MACHINE BICEP CURL");
                bicepsExercises.add("ZOTTMAN CURL");
                bicepsExercises.add("BARBELL CURL");
                bicepsExercises.add("CONCENTRATION CURLS");
                bicepsExercises.add("DUMBBELL BICEP CURL");
                bicepsExercises.add("FLEXOR INCLINE DUMBBELL CURLS");
                bicepsExercises.add("HAMMER CURLS");
                bicepsExercises.add("OVERHEAD CABLE CURL");
                bicepsExercises.add("CLOSE-GRIP EZ BAR CURL");
                bicepsExercises.add("CROSS BODY HAMMER CURL");
                return bicepsExercises;
            case CHEST:
                ArrayList<String> chestExercises = new ArrayList<>();
                chestExercises.add("PUSHUPS");
                chestExercises.add("BARBELL BENCH PRESS - MEDIUM GRIP");
                chestExercises.add("DUMBBELL BENCH PRESS");
                chestExercises.add("DUMBBELL FLYES");
                chestExercises.add("INCLINE DUMBBELL PRESS");
                chestExercises.add("LOW CABLE CROSSOVER");
                chestExercises.add("BODYWEIGHT FLYES");
                chestExercises.add("DECLINE DUMBBELL FLYES");
                chestExercises.add("DIPS - CHEST VERSION");
                chestExercises.add("INCLINE CABLE FLYE");
                chestExercises.add("INCLINE PUSH-UP WIDE");
                chestExercises.add("DECLINE BARBELL BENCH PRESS");
                chestExercises.add("INCLINE DUMBBELL PRESS REVERSE-GRIP");
                chestExercises.add("WIDE-GRIP BARBELL BENCH PRESS");
                chestExercises.add("WIDE-GRIP DECLINE BARBELL BENCH PRESS");
                return chestExercises;
            case FOREARMS:
                ArrayList<String> forearmsExercises = new ArrayList<>();
                forearmsExercises.add("RICKSHAW CARRY");
                forearmsExercises.add("FARMER\'S WALK");
                forearmsExercises.add("PALMS-DOWN WRIST CURL OVER A BENCH");
                forearmsExercises.add("PALMS-UP BARBELL WRIST CURL OVER A BENCH");
                forearmsExercises.add("WRIST ROTATIONS WITH STRAIGHT BAR");
                forearmsExercises.add("STANDING PALMS-UP BARBELL BEHIND THE BACK WRIST CURL");
                forearmsExercises.add("FINGER CURLS");
                forearmsExercises.add("SEATED TWO-ARM PALMS-UP LOW-PULLEY WRIST CURL");
                forearmsExercises.add("WRIST ROLLER");
                forearmsExercises.add("SEATED PALMS-DOWN BARBELL WRIST CURL");
                forearmsExercises.add("DUMBBELL LYING SUPINATION");
                forearmsExercises.add("SEATED ONE-ARM DUMBBELL PALMS-UP WRIST CURL");
                forearmsExercises.add("DUMBBELL LYING PRONATION");
                forearmsExercises.add("SEATED PALM-UP BARBELL WRIST CURL");
                forearmsExercises.add("PALMS-UP DUMBBELL WRIST CURL OVER A BENCH");
                return forearmsExercises;
            case CALVES:
                ArrayList<String> calvesExercises = new ArrayList<>();
                calvesExercises.add("SMITH MACHINE CALF RAISE");
                calvesExercises.add("STANDING CALF RAISES");
                calvesExercises.add("STANDING DUMBBELL CALF RAISE");
                calvesExercises.add("ROCKING STANDING CALF RAISE");
                calvesExercises.add("CALF PRESS ON THE LEG PRESS MACHINE");
                calvesExercises.add("SEATED CALF RAISE");
                calvesExercises.add("CALF PRESS");
                calvesExercises.add("STANDING BARBELL CALF RAISE");
                calvesExercises.add("BARBELL SEATED CALF RAISE");
                calvesExercises.add("BALANCE BOARD");
                calvesExercises.add("ANKLE CIRCLES");
                calvesExercises.add("DONKEY CALF RAISES");
                calvesExercises.add("SMITH MACHINE REVERSE CALF RAISES");
                calvesExercises.add("DUMBBELL SEATED ONE-LEG CALF RAISE");
                calvesExercises.add("KNEE CIRCLES");
                return calvesExercises;
            case GLUTES:
                ArrayList<String> glutesExercises = new ArrayList<>();
                glutesExercises.add("BARBELL GLUTE BRIDGE");
                glutesExercises.add("BARBELL HIP THRUST");
                glutesExercises.add("ONE-LEGGED CABLE KICKBACK");
                glutesExercises.add("BUTT LIFT (BRIDGE)");
                glutesExercises.add("SINGLE LEG GLUTE BRIDGE");
                glutesExercises.add("STEP-UP WITH KNEE RAISE");
                glutesExercises.add("KNEELING SQUAT");
                glutesExercises.add("FLUTTER KICKS");
                glutesExercises.add("GLUTE KICKBACK");
                glutesExercises.add("PHYSIOBALL HIP BRIDGE");
                glutesExercises.add("KNEELING JUMP SQUAT");
                glutesExercises.add("PULL THROUGH");
                glutesExercises.add("HIP EXTENSION WITH BANDS");
                glutesExercises.add("LEG LIFT");
                glutesExercises.add("PIRIFORMIS-SMR");
                return glutesExercises;
            case HAMSTRINGS:
                ArrayList<String> hamstringsExercises = new ArrayList<>();
                hamstringsExercises.add("CLEAN DEADLIFT");
                hamstringsExercises.add("HANG SNATCH");
                hamstringsExercises.add("ROMANIAN DEADLIFT FROM DEFICIT");
                hamstringsExercises.add("LUNGE PASS THROUGH");
                hamstringsExercises.add("ROMANIAN DEADLIFT WITH DUMBBELLS");
                hamstringsExercises.add("SNATCH DEADLIFT");
                hamstringsExercises.add("BARBELL DEADLIFT");
                hamstringsExercises.add("POWER CLEAN FROM BLOCKS");
                hamstringsExercises.add("POWER SNATCH");
                hamstringsExercises.add("SUMO DEADLIFT");
                hamstringsExercises.add("FLOOR GLUTE-HAM RAISE");
                hamstringsExercises.add("NATURAL GLUTE HAM RAISE");
                hamstringsExercises.add("FRONT LEG RAISES");
                hamstringsExercises.add("LYING LEG CURLS");
                hamstringsExercises.add("SINGLE LEG DEADLIFT");
                return hamstringsExercises;
            case LATS:
                ArrayList<String> latsExercises = new ArrayList<>();
                latsExercises.add("WIDE-GRIP PULL-UP");
                latsExercises.add("WEIGHTED PULL UPS");
                latsExercises.add("MACHINE-ASSISTED PULL-UP");
                latsExercises.add("PULLUPS");
                latsExercises.add("CHIN-UP");
                latsExercises.add("ROCKY PULL-UPS/PULLDOWNS");
                latsExercises.add("MUSCLE UP");
                latsExercises.add("SHOTGUN ROW");
                latsExercises.add("V-BAR PULLDOWN");
                latsExercises.add("CLOSE-GRIP FRONT LAT PULLDOWN");
                latsExercises.add("V-BAR PULLUP");
                latsExercises.add("ROPE CLIMB");
                latsExercises.add("ROPE STRAIGHT-ARM PULLDOWN");
                latsExercises.add("WIDE-GRIP REAR PULL-UP");
                latsExercises.add("WIDE-GRIP LAT PULLDOWN");
                return latsExercises;
            case LOWER_BACK:
                ArrayList<String> lowerbackExercises = new ArrayList<>();
                lowerbackExercises.add("ATLAS STONES");
                lowerbackExercises.add("DEFICIT DEADLIFT");
                lowerbackExercises.add("AXLE DEADLIFT");
                lowerbackExercises.add("HYPEREXTENSIONS (BACK EXTENSIONS)");
                lowerbackExercises.add("HYPEREXTENSIONS WITH NO HYPEREXTENSION BENCH");
                lowerbackExercises.add("DEADLIFT WITH BANDS");
                lowerbackExercises.add("DEADLIFT WITH CHAINS");
                lowerbackExercises.add("RACK PULL WITH BANDS");
                lowerbackExercises.add("RACK PULLS");
                lowerbackExercises.add("SUPERMAN");
                lowerbackExercises.add("WEIGHTED BALL HYPEREXTENSION");
                lowerbackExercises.add("SEATED GOOD MORNINGS");
                lowerbackExercises.add("STIFF LEG BARBELL GOOD MORNING");
                lowerbackExercises.add("SEATED BACK EXTENSION");
                lowerbackExercises.add("ATLAS STONE TRAINER");
                return lowerbackExercises;
            case MIDDLE_BACK:
                ArrayList<String> middlebackExercises = new ArrayList<>();
                middlebackExercises.add("T-BAR ROW WITH HANDLE");
                middlebackExercises.add("PENDLAY ROWS");
                middlebackExercises.add("REVERSE GRIP BENT-OVER ROWS");
                middlebackExercises.add("ONE-ARM DUMBBELL ROW");
                middlebackExercises.add("ONE-ARM LONG BAR ROW");
                middlebackExercises.add("BENT OVER ONE-ARM LONG BAR ROW");
                middlebackExercises.add("BENT OVER TWO-ARM LONG BAR ROW");
                middlebackExercises.add("BENT OVER BARBELL ROW");
                middlebackExercises.add("BENT OVER TWO-DUMBBELL ROW WITH PALMS IN");
                middlebackExercises.add("BODYWEIGHT MID ROW");
                middlebackExercises.add("DUMBBELL INCLINE ROW");
                middlebackExercises.add("SEATED CABLE ROWS");
                middlebackExercises.add("SEATED ONE-ARM CABLE PULLEY ROWS");
                middlebackExercises.add("ALTERNATING RENEGADE ROW");
                middlebackExercises.add("MAN MAKER");
                return middlebackExercises;
            case SHOULDERS:
                ArrayList<String> shouldersExercises = new ArrayList<>();
                shouldersExercises.add("DUMBBELL REAR DELT ROW");
                shouldersExercises.add("CLEAN AND PRESS");
                shouldersExercises.add("SIDE LATERALS TO FRONT RAISE");
                shouldersExercises.add("SINGLE-ARM LINEAR JAMMER");
                shouldersExercises.add("STRAIGHT RAISES ON INCLINE BENCH");
                shouldersExercises.add("STANDING PALM-IN ONE-ARM DUMBBELL PRESS");
                shouldersExercises.add("CLEAN AND JERK");
                shouldersExercises.add("DUMBBELL LYING ONE-ARM REAR LATERAL RAISE");
                shouldersExercises.add("ONE-ARM KETTLEBELL PUSH PRESS");
                shouldersExercises.add("PUSH PRESS");
                shouldersExercises.add("STANDING MILITARY PRESS");
                shouldersExercises.add("KETTLEBELL PIRATE SHIPS");
                shouldersExercises.add("ONE-ARM SIDE LATERALS");
                shouldersExercises.add("POWER PARTIALS");
                shouldersExercises.add("REVERSE FLYES");
                return shouldersExercises;
            case TRAPS:
                ArrayList<String> trapsExercises = new ArrayList<>();
                trapsExercises.add("SMITH MACHINE SHRUG");
                trapsExercises.add("LEVERAGE SHRUG");
                trapsExercises.add("CALF-MACHINE SHOULDER SHRUG");
                trapsExercises.add("DUMBBELL SHRUG");
                trapsExercises.add("KETTLEBELL SUMO HIGH PULL");
                trapsExercises.add("STANDING DUMBBELL UPRIGHT ROW");
                trapsExercises.add("BARBELL SHRUG");
                trapsExercises.add("BARBELL SHRUG BEHIND THE BACK");
                trapsExercises.add("UPRIGHT CABLE ROW");
                trapsExercises.add("CABLE SHRUGS");
                trapsExercises.add("SMITH MACHINE BEHIND THE BACK SHRUG");
                trapsExercises.add("UPRIGHT ROW - WITH BANDS");
                trapsExercises.add("SMITH MACHINE UPRIGHT ROW");
                trapsExercises.add("CLEAN SHRUG");
                trapsExercises.add("SCAPULAR PULL-UP");
                return trapsExercises;
            case TRICEPS:
                ArrayList<String> tricepsExercises = new ArrayList<>();
                tricepsExercises.add("DECLINE EZ BAR TRICEPS EXTENSION");
                tricepsExercises.add("PARALLEL BAR DIP");
                tricepsExercises.add("DIPS - TRICEPS VERSION");
                tricepsExercises.add("DUMBBELL FLOOR PRESS");
                tricepsExercises.add("BODY-UP");
                tricepsExercises.add("CLOSE-GRIP BARBELL BENCH PRESS");
                tricepsExercises.add("CLOSE-GRIP EZ-BAR PRESS");
                tricepsExercises.add("TRICEPS PUSHDOWN - V-BAR ATTACHMENT");
                tricepsExercises.add("WEIGHTED BENCH DIP");
                tricepsExercises.add("EZ-BAR SKULLCRUSHER");
                tricepsExercises.add("INCLINE PUSH-UP CLOSE-GRIP");
                tricepsExercises.add("KNEELING CABLE TRICEPS EXTENSION");
                tricepsExercises.add("PUSH-UPS - CLOSE TRICEPS POSITION");
                tricepsExercises.add("REVERSE GRIP TRICEPS PUSHDOWN");
                tricepsExercises.add("SEATED TRICEPS PRESS");
                return tricepsExercises;
            case NECK:
                ArrayList<String> neckExercises = new ArrayList<>();
                neckExercises.add("LYING FACE DOWN PLATE NECK RESISTANCE");
                neckExercises.add("LYING FACE UP PLATE NECK RESISTANCE");
                neckExercises.add("ISOMETRIC NECK EXERCISE - SIDES");
                neckExercises.add("SEATED HEAD HARNESS NECK RESISTANCE");
                neckExercises.add("ISOMETRIC NECK EXERCISE - FRONT AND BACK");
                neckExercises.add("NECK BRIDGE PRONE");
                neckExercises.add("SIDE NECK STRETCH");
                neckExercises.add("CHIN TO CHEST STRETCH");
                neckExercises.add("NECK-SMR");
                return neckExercises;
            case QUADS:
                ArrayList<String> quadsExercises = new ArrayList<>();
                quadsExercises.add("SINGLE-LEG PRESS");
                quadsExercises.add("CLEAN FROM BLOCKS");
                quadsExercises.add("BARBELL FULL SQUAT");
                quadsExercises.add("TIRE FLIP");
                quadsExercises.add("BARBELL WALKING LUNGE");
                quadsExercises.add("BOX SQUAT");
                quadsExercises.add("HANG CLEAN");
                quadsExercises.add("REVERSE BAND BOX SQUAT");
                quadsExercises.add("SINGLE LEG PUSH-OFF");
                quadsExercises.add("SNATCH");
                quadsExercises.add("FRONT SQUATS WITH TWO KETTLEBELLS");
                quadsExercises.add("ROPE JUMPING");
                quadsExercises.add("KETTLEBELL PISTOL SQUAT");
                quadsExercises.add("NARROW STANCE LEG PRESS");
                quadsExercises.add("OLYMPIC SQUAT");
                return quadsExercises;
            default: throw new IllegalArgumentException();
        }
    }
}
