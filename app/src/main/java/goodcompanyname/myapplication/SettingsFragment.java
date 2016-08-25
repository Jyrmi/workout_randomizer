package goodcompanyname.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import adapter.TwoTuple;
import constant.PreferenceTags;
import constant.Setting;
import constant.SettingsCategory;

/**
 * Created by jeremy on 8/20/16.
 */
public class SettingsFragment extends Fragment {

    private static final String TAG = "SettingsFragment";
    public static final String ARG_PAGE = "ARG_PAGE";

    // Exercise Types section
    CheckBox checkBoxCardio;
    CheckBox checkBoxOlympic;
    CheckBox checkBoxPlyometrics;
    CheckBox checkBoxPowerlifting;
    CheckBox checkBoxStrength;
    CheckBox checkBoxStretching;
    CheckBox checkBoxStrongman;

    // Equipment section
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
    CheckBox checkBoxCompound;
    CheckBox checkBoxIsolation;
    CheckBox checkBoxNeither;

    // Force Section
    CheckBox checkBoxPush;
    CheckBox checkBoxPull;
    CheckBox checkBoxStatic;
    CheckBox checkBoxOtherForce;

    // Difficulty section
    CheckBox checkBoxEasy;
    CheckBox checkBoxMedium;
    CheckBox checkBoxHard;

    // Sports section
    CheckBox checkBoxYes;
    CheckBox checkBoxNo;

    private int mPageNo;
    private HashMap<CheckBox, Setting> checkBoxToSetting;
    private HashMap<Setting, CheckBox> settingToCheckBox;

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

        checkBoxToSetting = new HashMap<>();
        settingToCheckBox = new HashMap<>();

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



        // Associate CheckBoxes with their Setting

        // Exercise Types section
        checkBoxToSetting.put(checkBoxCardio, Setting.CARDIO);
        checkBoxToSetting.put(checkBoxOlympic, Setting.OLYMPIC_WEIGHTLIFTING);
        checkBoxToSetting.put(checkBoxPlyometrics, Setting.PLYOMETRICS);
        checkBoxToSetting.put(checkBoxPowerlifting, Setting.POWERLIFTING);
        checkBoxToSetting.put(checkBoxStrength, Setting.STRENGTH);
        checkBoxToSetting.put(checkBoxStretching, Setting.STRETCHING);
        checkBoxToSetting.put(checkBoxStrongman, Setting.STRONGMAN);

        // Equipment section
        checkBoxToSetting.put(checkBoxBands, Setting.BANDS);
        checkBoxToSetting.put(checkBoxBarbell, Setting.BARBELL);
        checkBoxToSetting.put(checkBoxBody, Setting.BODY_ONLY);
        checkBoxToSetting.put(checkBoxCable, Setting.CABLE);
        checkBoxToSetting.put(checkBoxDumbbell, Setting.DUMBBELL);
        checkBoxToSetting.put(checkBoxEzBar, Setting.EZ_CURL_BAR);
        checkBoxToSetting.put(checkBoxExerciseBall, Setting.EXERCISE_BALL);
        checkBoxToSetting.put(checkBoxFoamRoll, Setting.FOAM_ROLL);
        checkBoxToSetting.put(checkBoxKettlebells, Setting.KETTLEBELLS);
        checkBoxToSetting.put(checkBoxMachine, Setting.MACHINE);
        checkBoxToSetting.put(checkBoxMedicineBall, Setting.MEDICINE_BALL);
        checkBoxToSetting.put(checkBoxNone, Setting.NONE);
        checkBoxToSetting.put(checkBoxOther, Setting.OTHER);

        // Mechanics Section
        checkBoxToSetting.put(checkBoxCompound, Setting.COMPOUND);
        checkBoxToSetting.put(checkBoxIsolation, Setting.ISOLATION);
        checkBoxToSetting.put(checkBoxNeither, Setting.NEITHER);

        // Force Section
        checkBoxToSetting.put(checkBoxPush, Setting.PUSH);
        checkBoxToSetting.put(checkBoxPull, Setting.PULL);
        checkBoxToSetting.put(checkBoxStatic, Setting.STATIC);
        checkBoxToSetting.put(checkBoxOtherForce, Setting.OTHER_FORCE);

        // Difficulty section
        checkBoxToSetting.put(checkBoxEasy, Setting.BEGINNER);
        checkBoxToSetting.put(checkBoxMedium, Setting.INTERMEDIATE);
        checkBoxToSetting.put(checkBoxHard, Setting.EXPERT);

        // Sports section
        checkBoxToSetting.put(checkBoxYes, Setting.YES);
        checkBoxToSetting.put(checkBoxNo, Setting.NO);



        // Exercise Types section
        settingToCheckBox.put(Setting.CARDIO, checkBoxCardio);
        settingToCheckBox.put(Setting.OLYMPIC_WEIGHTLIFTING, checkBoxOlympic);
        settingToCheckBox.put(Setting.PLYOMETRICS, checkBoxPlyometrics);
        settingToCheckBox.put(Setting.POWERLIFTING, checkBoxPowerlifting);
        settingToCheckBox.put(Setting.STRENGTH, checkBoxStrength);
        settingToCheckBox.put(Setting.STRETCHING, checkBoxStretching);
        settingToCheckBox.put(Setting.STRONGMAN, checkBoxStrongman);

        // Equipment section
        settingToCheckBox.put(Setting.BANDS, checkBoxBands);
        settingToCheckBox.put(Setting.BARBELL, checkBoxBarbell);
        settingToCheckBox.put(Setting.BODY_ONLY, checkBoxBody);
        settingToCheckBox.put(Setting.CABLE, checkBoxCable);
        settingToCheckBox.put(Setting.DUMBBELL, checkBoxDumbbell);
        settingToCheckBox.put(Setting.EZ_CURL_BAR, checkBoxEzBar);
        settingToCheckBox.put(Setting.EXERCISE_BALL, checkBoxExerciseBall);
        settingToCheckBox.put(Setting.FOAM_ROLL, checkBoxFoamRoll);
        settingToCheckBox.put(Setting.KETTLEBELLS, checkBoxKettlebells);
        settingToCheckBox.put(Setting.MACHINE, checkBoxMachine);
        settingToCheckBox.put(Setting.MEDICINE_BALL, checkBoxMedicineBall);
        settingToCheckBox.put(Setting.NONE, checkBoxNone);
        settingToCheckBox.put(Setting.OTHER, checkBoxOther);

        // Mechanics Section
        settingToCheckBox.put(Setting.COMPOUND, checkBoxCompound);
        settingToCheckBox.put(Setting.ISOLATION, checkBoxIsolation);
        settingToCheckBox.put(Setting.NEITHER, checkBoxNeither);

        // Force Section
        settingToCheckBox.put(Setting.PUSH, checkBoxPush);
        settingToCheckBox.put(Setting.PULL, checkBoxPull);
        settingToCheckBox.put(Setting.STATIC, checkBoxStatic);
        settingToCheckBox.put(Setting.OTHER_FORCE, checkBoxOtherForce);

        // Difficulty section
        settingToCheckBox.put(Setting.BEGINNER, checkBoxEasy);
        settingToCheckBox.put(Setting.INTERMEDIATE, checkBoxMedium);
        settingToCheckBox.put(Setting.EXPERT, checkBoxHard);

        // Sports section
        settingToCheckBox.put(Setting.YES, checkBoxYes);
        settingToCheckBox.put(Setting.NO, checkBoxNo);

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

//        Button testButton = (Button) view.findViewById(R.id.test_button);
//        testButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                TwoTuple<String, ArrayList<String>> settingsQuery = getSettingsAsQuery();
//                TwoTuple<String, ArrayList<String>> settingsQuery = generateWhereClause();
//                Log.d(TAG, settingsQuery.a);
//                Log.d(TAG, settingsQuery.b.toString());
//            }
//        });

        return view;
    }

    private void recallSettings() {
        SharedPreferences sharedPreferences =
                getActivity().getSharedPreferences(PreferenceTags.PREFERENCES_SETTINGS,
                        Context.MODE_PRIVATE);

        HashMap<String, String> settings = (HashMap) sharedPreferences.getAll();

        for (Map.Entry<String, String> settingsEntry : settings.entrySet()) {
            Setting s = Setting.toSetting(settingsEntry.getKey());
            settingToCheckBox.get(s).setChecked(true);
        }
    }

    public HashMap<SettingsCategory, ArrayList<Setting>> getSettings() {
        HashMap<SettingsCategory, ArrayList<Setting>> settings = new HashMap<>();

        for (Map.Entry<CheckBox, Setting> entry : checkBoxToSetting.entrySet()) {
            if (entry.getKey().isChecked()) {
                Setting setting = entry.getValue();
                if (!settings.containsKey(setting.getCategory())) {
                    settings.put(setting.getCategory(), new ArrayList<Setting>());
                }
                settings.get(setting.getCategory()).add(setting);
            }
        }

        return settings;
    }

    public void updatePreferences() {
        SharedPreferences sharedPreferences =
                getActivity().getSharedPreferences(PreferenceTags.PREFERENCES_SETTINGS,
                        Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        for (Map.Entry<CheckBox, Setting> entry : checkBoxToSetting.entrySet()) {
            Setting s = entry.getValue();
            if (entry.getKey().isChecked()) {
                editor.putString(s.toString(), s.getCategory().toString());
            } else {
                editor.remove(s.toString());
            }
        }

        editor.apply();
    }

    public void checkAll() {
        for (Map.Entry<CheckBox, Setting> entry : checkBoxToSetting.entrySet()) {
            entry.getKey().setChecked(true);
        }
    }

//    public TwoTuple<String, ArrayList<String>> getSettingsAsQuery() {
//
//        ArrayList<String> whereClauseSections = new ArrayList<>();
//        ArrayList<String> queryArgs = new ArrayList<>();
//
//        HashMap<SettingsCategory, ArrayList<Setting>> settings = getSettings();
//
//        for (Map.Entry<SettingsCategory, ArrayList<Setting>> entry : settings.entrySet()) {
//            ArrayList<String> whereStatements = new ArrayList<>();
//            for (Setting value : entry.getValue()) {
//                whereStatements.add(entry.getKey() + "=?");
//                queryArgs.add(value.toString());
//            }
//            whereClauseSections.add("(" + TextUtils.join(" OR ", whereStatements) + ")");
//        }
//
//        String fullWhereClause = TextUtils.join(" AND ", whereClauseSections);
//
//        return new TwoTuple<>(fullWhereClause, queryArgs);
//    }

//    public TwoTuple<String, ArrayList<String>> generateWhereClause() {
//        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(
//                PreferenceTags.PREFERENCES_SETTINGS, Context.MODE_PRIVATE);
//
//        ArrayList<String> whereClauseSections = new ArrayList<>();
//        ArrayList<String> queryArgs = new ArrayList<>();
//
//        HashMap<String, String> settings = (HashMap) sharedPreferences.getAll();
//        HashMap<String, ArrayList<String>> categories = new HashMap<>();
//
//        for (Map.Entry<String, String> entry : settings.entrySet()) {
//            String category = entry.getValue();
//            if (!categories.containsKey(category)) {
//                categories.put(category, new ArrayList<String>());
//            }
//            categories.get(category).add(entry.getKey());
//        }
//
//        for (Map.Entry<String, ArrayList<String>> entry : categories.entrySet()) {
//            ArrayList<String> whereStatements = new ArrayList<>();
//            for (String setting : entry.getValue()) {
//                whereStatements.add(entry.getKey() + "=?");
//                queryArgs.add(setting);
//            }
//            whereClauseSections.add("(" + TextUtils.join(" OR ", whereStatements) + ")");
//        }
//
//        String fullWhereClause = TextUtils.join(" AND ", whereClauseSections);
//
//        return new TwoTuple<>(fullWhereClause, queryArgs);
//    }
}
