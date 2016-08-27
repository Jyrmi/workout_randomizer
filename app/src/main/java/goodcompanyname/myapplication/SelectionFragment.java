package goodcompanyname.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import constant.PreferenceTags;

public class SelectionFragment extends Fragment {
    // todo: repartition the images to include abductors/adductors muscle groups (5)

    private static final String TAG = "SelectionFragment";
    public static final String ARG_PAGE = "ARG_PAGE";

    RelativeLayout tooltip;

    ImageView imageGroupA;
    ImageView imageGroupB;
    ImageView imageGroupC;
    ImageView imageGroupD;

    Button buttonAbsLower;
    Button buttonAbsUpper;
    Button buttonBiceps;
    Button buttonChest;
    Button buttonForearms;
    Button buttonFrontCalves;
    Button buttonFrontHamstrings;
    Button buttonFrontLatsLeft;
    Button buttonFrontLatsRight;
    Button buttonFrontShoulders;
    Button buttonFrontTraps;
    Button buttonNeck;
    Button buttonQuads;

    Button buttonRearTraps;
    Button buttonRearShoulders;
    Button buttonTriceps;
    Button buttonRearForearms;
    Button buttonMiddleBack;
    Button buttonLowerBack;
    Button buttonGlutes;
    Button buttonRearHamstrings;
    Button buttonRearCalves;

    FloatingActionButton fabGenerate;
    FloatingActionButton fabRotate;

    OnMuscleGroupsSelectedListener callback;

    ArrayList<String> selectedMuscleGroups;
    ArrayList<Button> frontButtons;
    ArrayList<Button> rearButtons;
    Boolean forward;

    // Container Activity must implement this interface
    protected interface OnMuscleGroupsSelectedListener {
        void onFinishSelection(ArrayList<String> selectedMuscleGroups);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            callback = (OnMuscleGroupsSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    public static SelectionFragment newInstance(int pageNo) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNo);
        SelectionFragment fragment = new SelectionFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_selection, container, false);

        forward = true;
        selectedMuscleGroups = new ArrayList();

        imageGroupA = (ImageView) view.findViewById(R.id.image_group_a);
        imageGroupB = (ImageView) view.findViewById(R.id.image_group_b);
        imageGroupC = (ImageView) view.findViewById(R.id.image_group_c);
        imageGroupD = (ImageView) view.findViewById(R.id.image_group_d);

        buttonAbsLower = (Button) view.findViewById(R.id.button_abs_lower);
        buttonAbsUpper = (Button) view.findViewById(R.id.button_abs_upper);
        buttonBiceps = (Button) view.findViewById(R.id.button_biceps);
        buttonChest = (Button) view.findViewById(R.id.button_chest);
        buttonForearms = (Button) view.findViewById(R.id.button_forearms);
        buttonFrontCalves = (Button) view.findViewById(R.id.button_front_calves);
        buttonFrontHamstrings = (Button) view.findViewById(R.id.button_front_hamstrings);
        buttonFrontLatsLeft = (Button) view.findViewById(R.id.button_front_lats_left);
        buttonFrontLatsRight = (Button) view.findViewById(R.id.button_front_lats_right);
        buttonFrontShoulders = (Button) view.findViewById(R.id.button_front_shoulders);
        buttonFrontTraps = (Button) view.findViewById(R.id.button_front_traps);
        buttonNeck = (Button) view.findViewById(R.id.button_neck);
        buttonQuads = (Button) view.findViewById(R.id.button_quads);

        buttonRearTraps = (Button) view.findViewById(R.id.button_rear_traps);
        buttonRearShoulders = (Button) view.findViewById(R.id.button_rear_shoulders);
        buttonTriceps = (Button) view.findViewById(R.id.button_triceps);
        buttonRearForearms = (Button) view.findViewById(R.id.button_rear_forearms);
        buttonMiddleBack = (Button) view.findViewById(R.id.button_middle_back);
        buttonLowerBack = (Button) view.findViewById(R.id.button_lower_back);
        buttonGlutes = (Button) view.findViewById(R.id.button_glutes);
        buttonRearHamstrings = (Button) view.findViewById(R.id.button_rear_hamstrings);
        buttonRearCalves = (Button) view.findViewById(R.id.button_rear_calves);

        selectedMuscleGroups = new ArrayList<>();

        setButtonListenerFront(buttonAbsLower, "Abdominals", imageGroupC);
        setButtonListenerFront(buttonAbsUpper, "Abdominals", imageGroupC);
        setButtonListenerFront(buttonBiceps, "Biceps", imageGroupB);
        setButtonListenerFront(buttonChest, "Chest", imageGroupA);
        setButtonListenerFront(buttonForearms, "Forearms", imageGroupB);
        setButtonListenerFront(buttonFrontCalves, "Calves", imageGroupD);
        setButtonListenerFront(buttonFrontHamstrings, "Hamstrings", imageGroupC);
        setButtonListenerFront(buttonFrontLatsLeft, "Lats", imageGroupC);
        setButtonListenerFront(buttonFrontLatsRight, "Lats", imageGroupC);
        setButtonListenerFront(buttonFrontShoulders, "Shoulders", imageGroupB);
        setButtonListenerFront(buttonFrontTraps, "Traps", imageGroupA);
        setButtonListenerFront(buttonNeck, "Neck", imageGroupA);
        setButtonListenerFront(buttonQuads, "Quadriceps", imageGroupD);

        setButtonListenerRear(buttonRearTraps, "Traps", imageGroupA);
        setButtonListenerRear(buttonRearShoulders, "Shoulders", imageGroupA);
        setButtonListenerRear(buttonTriceps, "Triceps", imageGroupB);
        setButtonListenerRear(buttonRearForearms, "Forearms", imageGroupB);
        setButtonListenerRear(buttonMiddleBack, "Middle Back", imageGroupC);
        setButtonListenerRear(buttonLowerBack, "Lower Back", imageGroupC);
        setButtonListenerRear(buttonGlutes, "Glutes", imageGroupD);
        setButtonListenerRear(buttonRearHamstrings, "Hamstrings", imageGroupD);
        setButtonListenerRear(buttonRearCalves, "Calves", imageGroupD);

        frontButtons = new ArrayList();
        rearButtons = new ArrayList();

        frontButtons.add(buttonAbsLower);
        frontButtons.add(buttonAbsUpper);
        frontButtons.add(buttonBiceps);
        frontButtons.add(buttonChest);
        frontButtons.add(buttonForearms);
        frontButtons.add(buttonFrontCalves);
        frontButtons.add(buttonFrontHamstrings);
        frontButtons.add(buttonFrontLatsLeft);
        frontButtons.add(buttonFrontLatsRight);
        frontButtons.add(buttonFrontShoulders);
        frontButtons.add(buttonFrontTraps);
        frontButtons.add(buttonNeck);
        frontButtons.add(buttonQuads);

        rearButtons.add(buttonRearTraps);
        rearButtons.add(buttonRearShoulders);
        rearButtons.add(buttonTriceps);
        rearButtons.add(buttonRearForearms);
        rearButtons.add(buttonMiddleBack);
        rearButtons.add(buttonLowerBack);
        rearButtons.add(buttonGlutes);
        rearButtons.add(buttonRearHamstrings);
        rearButtons.add(buttonRearCalves);

        toggleButtonsVisibility(frontButtons, View.VISIBLE);

        fabGenerate = (FloatingActionButton) view.findViewById(R.id.fab_selection);
        fabGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedMuscleGroups.isEmpty()) {
                    Snackbar.make(view, "Select at least one", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                } else {
                    callback.onFinishSelection(selectedMuscleGroups);
                    updatePreferences(PreferenceTags.PREFERENCES_SELECTED_MUSCLE_GROUPS,
                            selectedMuscleGroups);
                    resetSelection();
                }
            }
        });

        fabRotate = (FloatingActionButton) view.findViewById(R.id.fab_selection_rotate);
        fabRotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turnBody();
            }
        });

        firstRunSettingsCheck(view);

        return view;
    }

    public void firstRunSettingsCheck(View view) {
        // Show tooltips on first run
        SharedPreferences defaultPreferences =
                PreferenceManager.getDefaultSharedPreferences(getActivity());

        if (defaultPreferences.getBoolean("showtooltips", true)) {
            tooltip = (RelativeLayout) view.findViewById(R.id.selection_tooltip);
            tooltip.setVisibility(View.VISIBLE);
            tooltip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tooltip.setVisibility(View.GONE);
                }
            });
        }
    }

    private void updatePreferences(String sharedPreferencesTag, ArrayList<?> selections) {
        SharedPreferences sharedPreferences =
                getActivity().getSharedPreferences(sharedPreferencesTag, Context.MODE_PRIVATE);
        if (!selections.isEmpty()) {
            int count;
            SharedPreferences.Editor editor = sharedPreferences.edit();

            for (Object key : selections) {
                count = sharedPreferences.getInt(key.toString(), 0);
                editor.putInt(key.toString(), count + 1);
            }

            editor.apply();
        }
    }

    public void resetSelection() {
        selectedMuscleGroups.clear();
        if (forward) {
            setGroupImageResourceFront(imageGroupA); // Update image resources
            setGroupImageResourceFront(imageGroupB);
            setGroupImageResourceFront(imageGroupC);
            setGroupImageResourceFront(imageGroupD);
        } else {
            setGroupImageResourceRear(imageGroupA); // Update image resources
            setGroupImageResourceRear(imageGroupB);
            setGroupImageResourceRear(imageGroupC);
            setGroupImageResourceRear(imageGroupD);
        }
    }

    /**
     * Turns the body around by changing image assets.
     */
    public void turnBody() {
        if (forward) { // Turn to the back
            forward = false; // Switch parity of forward facing boolean
            setGroupImageResourceRear(imageGroupA); // Update image resources
            setGroupImageResourceRear(imageGroupB);
            setGroupImageResourceRear(imageGroupC);
            setGroupImageResourceRear(imageGroupD);
            toggleButtonsVisibility(frontButtons, View.INVISIBLE); // Hide and reveal buttons
            toggleButtonsVisibility(rearButtons, View.VISIBLE);
        } else { // Turn to the front
            forward = true; // Switch parity of forward facing boolean
            setGroupImageResourceFront(imageGroupA); // Update image resources
            setGroupImageResourceFront(imageGroupB);
            setGroupImageResourceFront(imageGroupC);
            setGroupImageResourceFront(imageGroupD);
            toggleButtonsVisibility(frontButtons, View.VISIBLE); // Hide and reveal buttons
            toggleButtonsVisibility(rearButtons, View.INVISIBLE);
        }
    }

    private void toggleButtonsVisibility(ArrayList<Button> list, int visibility) {
        for (Button button : list) {
            button.setVisibility(visibility);
        }
    }

    /**
     * Sets listeners for this activity's buttons.
     * @param button the button.
     * @param imageView the ImageView associated with the button.
     */
    private void setButtonListenerFront(Button button, final String muscleGroup,
                                        final ImageView imageView) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedMuscleGroups.contains(muscleGroup)) {
                    selectedMuscleGroups.remove(muscleGroup);
                } else {
                    selectedMuscleGroups.add(muscleGroup);
                }

                setGroupImageResourceFront(imageView);
            }
        });
    }

    /**
     * Sets listeners for this activity's buttons.
     * @param button the button.
     * @param imageView the ImageView associated with the button.
     */
    private void setButtonListenerRear(Button button, final String muscleGroup,
                                       final ImageView imageView) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedMuscleGroups.contains(muscleGroup)) {
                    selectedMuscleGroups.remove(muscleGroup);
                } else {
                    selectedMuscleGroups.add(muscleGroup);
                }

                setGroupImageResourceRear(imageView);
            }
        });
    }

    private void setGroupImageResourceFront(ImageView imageView) {
        if (imageView == imageGroupA) {
            if (selectedMuscleGroups.contains("Neck") &&
                    selectedMuscleGroups.contains("Traps") &&
                    selectedMuscleGroups.contains("Chest")) {
                imageView.setImageResource(R.drawable.a_all);
            } else if (selectedMuscleGroups.contains("Neck") &&
                    selectedMuscleGroups.contains("Traps")) {
                imageView.setImageResource(R.drawable.a_neck_traps);
            } else if (selectedMuscleGroups.contains("Neck") &&
                    selectedMuscleGroups.contains("Chest")) {
                imageView.setImageResource(R.drawable.a_neck_chest);
            } else if (selectedMuscleGroups.contains("Traps") &&
                    selectedMuscleGroups.contains("Chest")) {
                imageView.setImageResource(R.drawable.a_traps_chest);
            } else if (selectedMuscleGroups.contains("Neck")) {
                imageView.setImageResource(R.drawable.a_neck);
            } else if (selectedMuscleGroups.contains("Traps")) {
                imageView.setImageResource(R.drawable.a_traps);
            } else if (selectedMuscleGroups.contains("Chest")) {
                imageView.setImageResource(R.drawable.a_chest);
            } else {
                imageView.setImageResource(R.drawable.a_none);
            }
        } else if (imageView == imageGroupB) {
            if (selectedMuscleGroups.contains("Shoulders") &&
                    selectedMuscleGroups.contains("Biceps") &&
                    selectedMuscleGroups.contains("Forearms")) {
                imageView.setImageResource(R.drawable.b_all);
            } else if (selectedMuscleGroups.contains("Shoulders") &&
                    selectedMuscleGroups.contains("Biceps")) {
                imageView.setImageResource(R.drawable.b_shoulders_biceps);
            } else if (selectedMuscleGroups.contains("Shoulders") &&
                    selectedMuscleGroups.contains("Forearms")) {
                imageView.setImageResource(R.drawable.b_shoulders_forearms);
            } else if (selectedMuscleGroups.contains("Biceps") &&
                    selectedMuscleGroups.contains("Forearms")) {
                imageView.setImageResource(R.drawable.b_biceps_forearms);
            } else if (selectedMuscleGroups.contains("Shoulders")) {
                imageView.setImageResource(R.drawable.b_shoulders);
            } else if (selectedMuscleGroups.contains("Biceps")) {
                imageView.setImageResource(R.drawable.b_biceps);
            } else if (selectedMuscleGroups.contains("Forearms")) {
                imageView.setImageResource(R.drawable.b_forearms);
            } else {
                imageView.setImageResource(R.drawable.b_none);
            }
        } else if (imageView == imageGroupC) {
            if (selectedMuscleGroups.contains("Lats") &&
                    selectedMuscleGroups.contains("Abdominals") &&
                    selectedMuscleGroups.contains("Hamstrings")) {
                imageView.setImageResource(R.drawable.c_all);
            } else if (selectedMuscleGroups.contains("Lats") &&
                    selectedMuscleGroups.contains("Abdominals")) {
                imageView.setImageResource(R.drawable.c_lats_abs);
            } else if (selectedMuscleGroups.contains("Lats") &&
                    selectedMuscleGroups.contains("Hamstrings")) {
                imageView.setImageResource(R.drawable.c_lats_hamstrings);
            } else if (selectedMuscleGroups.contains("Abdominals") &&
                    selectedMuscleGroups.contains("Hamstrings")) {
                imageView.setImageResource(R.drawable.c_abs_hamstrings);
            } else if (selectedMuscleGroups.contains("Lats")) {
                imageView.setImageResource(R.drawable.c_lats);
            } else if (selectedMuscleGroups.contains("Abdominals")) {
                imageView.setImageResource(R.drawable.c_abs);
            } else if (selectedMuscleGroups.contains("Hamstrings")) {
                imageView.setImageResource(R.drawable.c_hamstrings);
            } else {
                imageView.setImageResource(R.drawable.c_none);
            }
        } else if (imageView == imageGroupD) {
            if (selectedMuscleGroups.contains("Quadriceps") &&
                    selectedMuscleGroups.contains("Calves")) {
                imageView.setImageResource(R.drawable.d_all);
            } else if (selectedMuscleGroups.contains("Quadriceps")) {
                imageView.setImageResource(R.drawable.d_quads);
            } else if (selectedMuscleGroups.contains("Calves")) {
                imageView.setImageResource(R.drawable.d_calves);
            } else {
                imageView.setImageResource(R.drawable.d_none);
            }
        }
    }

    private void setGroupImageResourceRear(ImageView imageView) {
        if (imageView == imageGroupA) {
            if (selectedMuscleGroups.contains("Traps") &&
                    selectedMuscleGroups.contains("Shoulders")) {
                imageView.setImageResource(R.drawable.e_all);
            } else if (selectedMuscleGroups.contains("Traps")) {
                imageView.setImageResource(R.drawable.e_traps);
            } else if (selectedMuscleGroups.contains("Shoulders")) {
                imageView.setImageResource(R.drawable.e_shoulders);
            } else {
                imageView.setImageResource(R.drawable.e_none);
            }
        } else if (imageView == imageGroupB) {
            if (selectedMuscleGroups.contains("Triceps") &&
                    selectedMuscleGroups.contains("Forearms")) {
                imageView.setImageResource(R.drawable.f_all);
            } else if (selectedMuscleGroups.contains("Triceps")) {
                imageView.setImageResource(R.drawable.f_triceps);
            } else if (selectedMuscleGroups.contains("Forearms")) {
                imageView.setImageResource(R.drawable.f_forearms);
            } else {
                imageView.setImageResource(R.drawable.f_none);
            }
        } else if (imageView == imageGroupC) {
            if (selectedMuscleGroups.contains("Middle Back") &&
                    selectedMuscleGroups.contains("Lower Back")) {
                imageView.setImageResource(R.drawable.g_all);
            } else if (selectedMuscleGroups.contains("Middle Back")) {
                imageView.setImageResource(R.drawable.g_middle_back);
            } else if (selectedMuscleGroups.contains("Lower Back")) {
                imageView.setImageResource(R.drawable.g_lower_back);
            } else {
                imageView.setImageResource(R.drawable.g_none);
            }
        } else if (imageView == imageGroupD) {
            if (selectedMuscleGroups.contains("Glutes") &&
                    selectedMuscleGroups.contains("Hamstrings") &&
                    selectedMuscleGroups.contains("Calves")) {
                imageView.setImageResource(R.drawable.h_all);
            } else if (selectedMuscleGroups.contains("Glutes") &&
                    selectedMuscleGroups.contains("Hamstrings")) {
                imageView.setImageResource(R.drawable.h_glutes_hamstrings);
            } else if (selectedMuscleGroups.contains("Glutes") &&
                    selectedMuscleGroups.contains("Calves")) {
                imageView.setImageResource(R.drawable.h_glutes_calves);
            } else if (selectedMuscleGroups.contains("Hamstrings") &&
                    selectedMuscleGroups.contains("Calves")) {
                imageView.setImageResource(R.drawable.h_hamstrings_calves);
            } else if (selectedMuscleGroups.contains("Glutes")) {
                imageView.setImageResource(R.drawable.h_glutes);
            } else if (selectedMuscleGroups.contains("Hamstrings")) {
                imageView.setImageResource(R.drawable.h_hamstrings);
            } else if (selectedMuscleGroups.contains("Calves")) {
                imageView.setImageResource(R.drawable.h_calves);
            } else {
                imageView.setImageResource(R.drawable.h_none);
            }
        }
    }
}
