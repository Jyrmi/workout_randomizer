package goodcompanyname.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import constant.PreferenceTags;
import sqlite.ExerciseContract;

/**
 * Created by jeremy on 8/20/16.
 */
public class SettingsFragment extends Fragment {
    // todo: create options for isolation/compound, difficulty, and syncing exercise db via web scrape and "about" information

    private static final String TAG = "SettingsFragment";
    public static final String ARG_PAGE = "ARG_PAGE";

    // Exercise Types section
    CheckBox[] typeCheckBoxes;
    CheckBox checkBoxCardio;
    CheckBox checkBoxOlympic;
    CheckBox checkBoxPlyometrics;
    CheckBox checkBoxPowerlifting;
    CheckBox checkBoxStrength;
    CheckBox checkBoxStretching;
    CheckBox checkBoxStrongman;

    // Equipment section
    CheckBox[] equipmentCheckBoxes;
    CheckBox checkBoxBands;
    CheckBox checkBoxBarbell;
    CheckBox checkBoxBody;
    CheckBox checkBoxCable;
    CheckBox checkBoxDumbbell;
    CheckBox checkBoxEzBar;
    CheckBox checkBoxExerciseBall;
    CheckBox checkBoxFoamRoll;
    CheckBox checkBoxKettlebells;
    CheckBox checkBoxMachine;
    CheckBox checkBoxMedicineBall;
    CheckBox checkBoxNone;
    CheckBox checkBoxOther;

    // Mechanics Section
    CheckBox[] mechanicsCheckBoxes;
    CheckBox checkBoxCompound;
    CheckBox checkBoxIsolation;
    CheckBox checkBoxNeither;

    // Force Section
    CheckBox[] forceCheckBoxes;
    CheckBox checkBoxPush;
    CheckBox checkBoxPull;
    CheckBox checkBoxStatic;
    CheckBox checkBoxOtherForce;

    // Difficulty section
    CheckBox[] difficultyCheckBoxes;
    CheckBox checkBoxEasy;
    CheckBox checkBoxMedium;
    CheckBox checkBoxHard;

    // Sports section
    CheckBox checkBoxYes;
    CheckBox checkBoxNo;

    OnSettingsRequestedListener callback;
    private int mPageNo;

    // Container Activity must implement this interface
    protected interface OnSettingsRequestedListener {
        void onSettingsRequested();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            callback = (OnSettingsRequestedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnMuscleGroupsSelectedListener");
        }
    }

    public static SettingsFragment newInstance(int pageNo) {

        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNo);
        SettingsFragment fragment = new SettingsFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNo = getArguments().getInt(ARG_PAGE);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        // Exercise Types section
        checkBoxCardio = (CheckBox) view.findViewById(R.id.checkbox_cardio);
        checkBoxOlympic = (CheckBox) view.findViewById(R.id.checkbox_olympic);
        checkBoxPlyometrics = (CheckBox) view.findViewById(R.id.checkbox_plyometrics);
        checkBoxPowerlifting = (CheckBox) view.findViewById(R.id.checkbox_powerlifting);
        checkBoxStrength = (CheckBox) view.findViewById(R.id.checkbox_strength);
        checkBoxStretching = (CheckBox) view.findViewById(R.id.checkbox_stretching);
        checkBoxStrongman = (CheckBox) view.findViewById(R.id.checkbox_strongman);

        // Equipment section
        checkBoxBands = (CheckBox) view.findViewById(R.id.checkbox_bands);
        checkBoxBarbell = (CheckBox) view.findViewById(R.id.checkbox_barbell);
        checkBoxBody = (CheckBox) view.findViewById(R.id.checkbox_body);
        checkBoxCable = (CheckBox) view.findViewById(R.id.checkbox_cable);
        checkBoxDumbbell = (CheckBox) view.findViewById(R.id.checkbox_dumbbell);
        checkBoxEzBar = (CheckBox) view.findViewById(R.id.checkbox_ez_bar);
        checkBoxExerciseBall = (CheckBox) view.findViewById(R.id.checkbox_exercise_ball);
        checkBoxFoamRoll = (CheckBox) view.findViewById(R.id.checkbox_foam_roll);
        checkBoxKettlebells = (CheckBox) view.findViewById(R.id.checkbox_kettlebells);
        checkBoxMachine = (CheckBox) view.findViewById(R.id.checkbox_machine);
        checkBoxMedicineBall = (CheckBox) view.findViewById(R.id.checkbox_medicine_ball);
        checkBoxNone = (CheckBox) view.findViewById(R.id.checkbox_none);
        checkBoxOther = (CheckBox) view.findViewById(R.id.checkbox_other);

        // Mechanics Section
        checkBoxCompound = (CheckBox) view.findViewById(R.id.checkbox_compound);
        checkBoxIsolation = (CheckBox) view.findViewById(R.id.checkbox_isolation);
        checkBoxNeither = (CheckBox) view.findViewById(R.id.checkbox_neither);

        // Force Section
        checkBoxPush = (CheckBox) view.findViewById(R.id.checkbox_push);
        checkBoxPull = (CheckBox) view.findViewById(R.id.checkbox_pull);
        checkBoxStatic = (CheckBox) view.findViewById(R.id.checkbox_static);
        checkBoxOtherForce = (CheckBox) view.findViewById(R.id.checkbox_other_force);

        // Difficulty section
        checkBoxEasy = (CheckBox) view.findViewById(R.id.checkbox_easy);
        checkBoxMedium = (CheckBox) view.findViewById(R.id.checkbox_medium);
        checkBoxHard = (CheckBox) view.findViewById(R.id.checkbox_hard);

        // Sports section
        checkBoxYes = (CheckBox) view.findViewById(R.id.checkbox_yes);
        checkBoxNo = (CheckBox) view.findViewById(R.id.checkbox_no);

        recallSettings();

//        // Contain in arrays for convenience
//        typeCheckBoxes = new CheckBox[]{checkBoxCardio, checkBoxOlympic, checkBoxPlyometrics,
//                checkBoxPowerlifting, checkBoxStrength, checkBoxStretching, checkBoxStrongman};
//        equipmentCheckBoxes = new CheckBox[]{checkBoxBands, checkBoxBarbell, checkBoxBody,
//                checkBoxCable, checkBoxDumbbell, checkBoxEzBar, checkBoxExerciseBall,
//                checkBoxFoamRoll, checkBoxKettlebells, checkBoxMachine, checkBoxMedicineBall,
//                checkBoxNone, checkBoxOther};
//        mechanicsCheckBoxes = new CheckBox[]{checkBoxCompound, checkBoxIsolation, checkBoxNeither};
//        difficultyCheckBoxes = new CheckBox[]{checkBoxEasy, checkBoxMedium, checkBoxHard};

        return view;
    }

    public HashMap<String, ArrayList<String>> getSettings() {
        HashMap<String, ArrayList<String>> settings = new HashMap<>();
        ArrayList<String> typeSettings = new ArrayList<>();
        ArrayList<String> equipmentSettings = new ArrayList<>();
        ArrayList<String> mechanicsSettings = new ArrayList<>();
        ArrayList<String> forceSettings = new ArrayList<>();
        ArrayList<String> difficultySettings = new ArrayList<>();
        ArrayList<String> sportsSettings = new ArrayList<>();

        // Exercise Types section
        if (checkBoxCardio.isChecked()) {typeSettings.add("Cardio");}
        if (checkBoxOlympic.isChecked()) {typeSettings.add("Olympic Weightlifting");}
        if (checkBoxPlyometrics.isChecked()) {typeSettings.add("Plyometrics");}
        if (checkBoxPowerlifting.isChecked()) {typeSettings.add("Powerlifting");}
        if (checkBoxStrength.isChecked()) {typeSettings.add("Strength");}
        if (checkBoxStretching.isChecked()) {typeSettings.add("Stretching");}
        if (checkBoxStrongman.isChecked()) {typeSettings.add("Strongman");}

        // Equipment section
        if (checkBoxBands.isChecked()) {equipmentSettings.add("Bands");}
        if (checkBoxBarbell.isChecked()) {equipmentSettings.add("Barbell");}
        if (checkBoxBody.isChecked()) {equipmentSettings.add("Body Only");}
        if (checkBoxCable.isChecked()) {equipmentSettings.add("Cable");}
        if (checkBoxDumbbell.isChecked()) {equipmentSettings.add("Dumbbell");}
        if (checkBoxEzBar.isChecked()) {equipmentSettings.add("E-Z Curl Bar");}
        if (checkBoxExerciseBall.isChecked()) {equipmentSettings.add("Exercise Ball");}
        if (checkBoxFoamRoll.isChecked()) {equipmentSettings.add("Foam Roll");}
        if (checkBoxKettlebells.isChecked()) {equipmentSettings.add("Kettlebells");}
        if (checkBoxMachine.isChecked()) {equipmentSettings.add("Machine");}
        if (checkBoxMedicineBall.isChecked()) {equipmentSettings.add("Medicine Ball");}
        if (checkBoxNone.isChecked()) {equipmentSettings.add("None");}
        if (checkBoxOther.isChecked()) {equipmentSettings.add("Other");}

        // Mechanics Section
        if (checkBoxCompound.isChecked()) {mechanicsSettings.add("Compound");}
        if (checkBoxIsolation.isChecked()) {mechanicsSettings.add("Isolation");}
        if (checkBoxNeither.isChecked()) {mechanicsSettings.add("N/A");}

        // Force Section
        if (checkBoxPush.isChecked()) {forceSettings.add("Push");}
        if (checkBoxPull.isChecked()) {forceSettings.add("Pull");}
        if (checkBoxStatic.isChecked()) {forceSettings.add("Static");}
        if (checkBoxOtherForce.isChecked()) {forceSettings.add("N/A Force");}

        // Difficulty section
        if (checkBoxEasy.isChecked()) {difficultySettings.add("Beginner");}
        if (checkBoxMedium.isChecked()) {difficultySettings.add("Intermediate");}
        if (checkBoxHard.isChecked()) {difficultySettings.add("Expert");}

        // Sports section
        if (checkBoxYes.isChecked()) {sportsSettings.add("Yes");}
        if (checkBoxNo.isChecked()) {sportsSettings.add("No");}

        settings.put(ExerciseContract.ExerciseEntry.COLUMN_TYPE, typeSettings);
        settings.put(ExerciseContract.ExerciseEntry.COLUMN_EQUIPMENT, equipmentSettings);
        settings.put(ExerciseContract.ExerciseEntry.COLUMN_MECHANICS, mechanicsSettings);
        settings.put(ExerciseContract.ExerciseEntry.COLUMN_FORCE, forceSettings);
        settings.put(ExerciseContract.ExerciseEntry.COLUMN_DIFFICULTY, difficultySettings);
        settings.put(ExerciseContract.ExerciseEntry.COLUMN_SPORT, sportsSettings);

        return settings;
    }

    private void recallSettings() {
        SharedPreferences sharedPreferences =
                getActivity().getSharedPreferences(PreferenceTags.PREFERENCES_SETTINGS,
                        Context.MODE_PRIVATE);

        HashMap<String, Boolean> settings = (HashMap) sharedPreferences.getAll();

        Log.d(TAG, settings.toString());

        for (Map.Entry<String, Boolean> settingsEntry : settings.entrySet()) {

            Log.d(TAG, settingsEntry.getKey());

            switch (settingsEntry.getKey()) {
                case "Cardio": checkBoxCardio.setChecked(true); break;
                case "Olympic Weightlifting": checkBoxOlympic.setChecked(true); break;
                case "Plyometrics": checkBoxPlyometrics.setChecked(true); break;
                case "Powerlifting": checkBoxPowerlifting.setChecked(true); break;
                case "Strength": checkBoxStrength.setChecked(true); break;
                case "Stretching": checkBoxStretching.setChecked(true); break;
                case "Strongman": checkBoxStrongman.setChecked(true); break;
                case "Bands": checkBoxBands.setChecked(true); break;
                case "Barbell": checkBoxBarbell.setChecked(true); break;
                case "Body Only": checkBoxBody.setChecked(true); break;
                case "Cable": checkBoxCable.setChecked(true); break;
                case "Dumbbell": checkBoxDumbbell.setChecked(true); break;
                case "E-Z Curl Bar": checkBoxEzBar.setChecked(true); break;
                case "Exercise Ball": checkBoxExerciseBall.setChecked(true); break;
                case "Foam Roll": checkBoxFoamRoll.setChecked(true); break;
                case "Kettlebells": checkBoxKettlebells.setChecked(true); break;
                case "Machine": checkBoxMachine.setChecked(true); break;
                case "Medicine Ball": checkBoxMedicineBall.setChecked(true); break;
                case "None": checkBoxNone.setChecked(true); break;
                case "Other": checkBoxOther.setChecked(true); break;
                case "Compound": checkBoxCompound.setChecked(true); break;
                case "Isolation": checkBoxIsolation.setChecked(true); break;
                case "N/A": checkBoxNeither.setChecked(true); break;
                case "Push": checkBoxPush.setChecked(true); break;
                case "Pull": checkBoxPull.setChecked(true); break;
                case "Static": checkBoxStatic.setChecked(true); break;
                case "N/A Force": checkBoxOtherForce.setChecked(true); break;
                case "Beginner": checkBoxEasy.setChecked(true); break;
                case "Intermediate": checkBoxMedium.setChecked(true); break;
                case "Expert": checkBoxHard.setChecked(true); break;
                case "Yes": checkBoxYes.setChecked(true); break;
                case "No": checkBoxNo.setChecked(true); break;
                default: throw new IllegalArgumentException();
            }
        }
    }

    public void checkAll() {
        checkBoxCardio.setChecked(true);
        checkBoxOlympic.setChecked(true);
        checkBoxPlyometrics.setChecked(true);
        checkBoxPowerlifting.setChecked(true);
        checkBoxStrength.setChecked(true);
        checkBoxStretching.setChecked(true);
        checkBoxStrongman.setChecked(true);
        checkBoxBands.setChecked(true);
        checkBoxBarbell.setChecked(true);
        checkBoxBody.setChecked(true);
        checkBoxCable.setChecked(true);
        checkBoxDumbbell.setChecked(true);
        checkBoxEzBar.setChecked(true);
        checkBoxExerciseBall.setChecked(true);
        checkBoxFoamRoll.setChecked(true);
        checkBoxKettlebells.setChecked(true);
        checkBoxMachine.setChecked(true);
        checkBoxMedicineBall.setChecked(true);
        checkBoxNone.setChecked(true);
        checkBoxOther.setChecked(true);
        checkBoxCompound.setChecked(true);
        checkBoxIsolation.setChecked(true);
        checkBoxNeither.setChecked(true);
        checkBoxEasy.setChecked(true);
        checkBoxMedium.setChecked(true);
        checkBoxHard.setChecked(true);
        checkBoxYes.setChecked(true);
        checkBoxNo.setChecked(true);
    }

    public Map.Entry<String, String[]> getSettingsAsQuery() {
        

        return null;
    }
}
