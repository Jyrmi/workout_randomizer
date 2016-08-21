package goodcompanyname.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import constant.MuscleGroup;

public class SelectionFragment extends Fragment {
    // todo: change "finish" button to circle with + symbol

    private static final String TAG = "SelectionFragment";
    public static final String ARG_PAGE = "ARG_PAGE";

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

    ArrayList<MuscleGroup> selectedMuscleGroups;
    ArrayList<Button> frontButtons;
    ArrayList<Button> rearButtons;
    Boolean forward;

    // Container Activity must implement this interface
    protected interface OnMuscleGroupsSelectedListener {
        void onFinishSelection(ArrayList<MuscleGroup> selectedMuscleGroups);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            callback = (OnMuscleGroupsSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnMuscleGroupsSelectedListener");
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

        setButtonListenerFront(buttonAbsLower, MuscleGroup.ABS, imageGroupC);
        setButtonListenerFront(buttonAbsUpper, MuscleGroup.ABS, imageGroupC);
        setButtonListenerFront(buttonBiceps, MuscleGroup.BICEPS, imageGroupB);
        setButtonListenerFront(buttonChest, MuscleGroup.CHEST, imageGroupA);
        setButtonListenerFront(buttonForearms, MuscleGroup.FOREARMS, imageGroupB);
        setButtonListenerFront(buttonFrontCalves, MuscleGroup.CALVES, imageGroupD);
        setButtonListenerFront(buttonFrontHamstrings, MuscleGroup.HAMSTRINGS, imageGroupC);
        setButtonListenerFront(buttonFrontLatsLeft, MuscleGroup.LATS, imageGroupC);
        setButtonListenerFront(buttonFrontLatsRight, MuscleGroup.LATS, imageGroupC);
        setButtonListenerFront(buttonFrontShoulders, MuscleGroup.SHOULDERS, imageGroupB);
        setButtonListenerFront(buttonFrontTraps, MuscleGroup.TRAPS, imageGroupA);
        setButtonListenerFront(buttonNeck, MuscleGroup.NECK, imageGroupA);
        setButtonListenerFront(buttonQuads, MuscleGroup.QUADS, imageGroupD);

        setButtonListenerRear(buttonRearTraps, MuscleGroup.TRAPS, imageGroupA);
        setButtonListenerRear(buttonRearShoulders, MuscleGroup.SHOULDERS, imageGroupA);
        setButtonListenerRear(buttonTriceps, MuscleGroup.TRICEPS, imageGroupB);
        setButtonListenerRear(buttonRearForearms, MuscleGroup.FOREARMS, imageGroupB);
        setButtonListenerRear(buttonMiddleBack, MuscleGroup.MIDDLE_BACK, imageGroupC);
        setButtonListenerRear(buttonLowerBack, MuscleGroup.LOWER_BACK, imageGroupC);
        setButtonListenerRear(buttonGlutes, MuscleGroup.GLUTES, imageGroupD);
        setButtonListenerRear(buttonRearHamstrings, MuscleGroup.HAMSTRINGS, imageGroupD);
        setButtonListenerRear(buttonRearCalves, MuscleGroup.CALVES, imageGroupD);

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

        return view;
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
    private void setButtonListenerFront(Button button, final MuscleGroup muscleGroup,
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
    private void setButtonListenerRear(Button button, final MuscleGroup muscleGroup,
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
            if (selectedMuscleGroups.contains(MuscleGroup.NECK) &&
                    selectedMuscleGroups.contains(MuscleGroup.TRAPS) &&
                    selectedMuscleGroups.contains(MuscleGroup.CHEST)) {
                imageView.setImageResource(R.drawable.a_all);
            } else if (selectedMuscleGroups.contains(MuscleGroup.NECK) &&
                    selectedMuscleGroups.contains(MuscleGroup.TRAPS)) {
                imageView.setImageResource(R.drawable.a_neck_traps);
            } else if (selectedMuscleGroups.contains(MuscleGroup.NECK) &&
                    selectedMuscleGroups.contains(MuscleGroup.CHEST)) {
                imageView.setImageResource(R.drawable.a_neck_chest);
            } else if (selectedMuscleGroups.contains(MuscleGroup.TRAPS) &&
                    selectedMuscleGroups.contains(MuscleGroup.CHEST)) {
                imageView.setImageResource(R.drawable.a_traps_chest);
            } else if (selectedMuscleGroups.contains(MuscleGroup.NECK)) {
                imageView.setImageResource(R.drawable.a_neck);
            } else if (selectedMuscleGroups.contains(MuscleGroup.TRAPS)) {
                imageView.setImageResource(R.drawable.a_traps);
            } else if (selectedMuscleGroups.contains(MuscleGroup.CHEST)) {
                imageView.setImageResource(R.drawable.a_chest);
            } else {
                imageView.setImageResource(R.drawable.a_none);
            }
        } else if (imageView == imageGroupB) {
            if (selectedMuscleGroups.contains(MuscleGroup.SHOULDERS) &&
                    selectedMuscleGroups.contains(MuscleGroup.BICEPS) &&
                    selectedMuscleGroups.contains(MuscleGroup.FOREARMS)) {
                imageView.setImageResource(R.drawable.b_all);
            } else if (selectedMuscleGroups.contains(MuscleGroup.SHOULDERS) &&
                    selectedMuscleGroups.contains(MuscleGroup.BICEPS)) {
                imageView.setImageResource(R.drawable.b_shoulders_biceps);
            } else if (selectedMuscleGroups.contains(MuscleGroup.SHOULDERS) &&
                    selectedMuscleGroups.contains(MuscleGroup.FOREARMS)) {
                imageView.setImageResource(R.drawable.b_shoulders_forearms);
            } else if (selectedMuscleGroups.contains(MuscleGroup.BICEPS) &&
                    selectedMuscleGroups.contains(MuscleGroup.FOREARMS)) {
                imageView.setImageResource(R.drawable.b_biceps_forearms);
            } else if (selectedMuscleGroups.contains(MuscleGroup.SHOULDERS)) {
                imageView.setImageResource(R.drawable.b_shoulders);
            } else if (selectedMuscleGroups.contains(MuscleGroup.BICEPS)) {
                imageView.setImageResource(R.drawable.b_biceps);
            } else if (selectedMuscleGroups.contains(MuscleGroup.FOREARMS)) {
                imageView.setImageResource(R.drawable.b_forearms);
            } else {
                imageView.setImageResource(R.drawable.b_none);
            }
        } else if (imageView == imageGroupC) {
            if (selectedMuscleGroups.contains(MuscleGroup.LATS) &&
                    selectedMuscleGroups.contains(MuscleGroup.ABS) &&
                    selectedMuscleGroups.contains(MuscleGroup.HAMSTRINGS)) {
                imageView.setImageResource(R.drawable.c_all);
            } else if (selectedMuscleGroups.contains(MuscleGroup.LATS) &&
                    selectedMuscleGroups.contains(MuscleGroup.ABS)) {
                imageView.setImageResource(R.drawable.c_lats_abs);
            } else if (selectedMuscleGroups.contains(MuscleGroup.LATS) &&
                    selectedMuscleGroups.contains(MuscleGroup.HAMSTRINGS)) {
                imageView.setImageResource(R.drawable.c_lats_hamstrings);
            } else if (selectedMuscleGroups.contains(MuscleGroup.ABS) &&
                    selectedMuscleGroups.contains(MuscleGroup.HAMSTRINGS)) {
                imageView.setImageResource(R.drawable.c_abs_hamstrings);
            } else if (selectedMuscleGroups.contains(MuscleGroup.LATS)) {
                imageView.setImageResource(R.drawable.c_lats);
            } else if (selectedMuscleGroups.contains(MuscleGroup.ABS)) {
                imageView.setImageResource(R.drawable.c_abs);
            } else if (selectedMuscleGroups.contains(MuscleGroup.HAMSTRINGS)) {
                imageView.setImageResource(R.drawable.c_hamstrings);
            } else {
                imageView.setImageResource(R.drawable.c_none);
            }
        } else if (imageView == imageGroupD) {
            if (selectedMuscleGroups.contains(MuscleGroup.QUADS) &&
                    selectedMuscleGroups.contains(MuscleGroup.CALVES)) {
                imageView.setImageResource(R.drawable.d_all);
            } else if (selectedMuscleGroups.contains(MuscleGroup.QUADS)) {
                imageView.setImageResource(R.drawable.d_quads);
            } else if (selectedMuscleGroups.contains(MuscleGroup.CALVES)) {
                imageView.setImageResource(R.drawable.d_calves);
            } else {
                imageView.setImageResource(R.drawable.d_none);
            }
        }
    }

    private void setGroupImageResourceRear(ImageView imageView) {
        if (imageView == imageGroupA) {
            if (selectedMuscleGroups.contains(MuscleGroup.TRAPS) &&
                    selectedMuscleGroups.contains(MuscleGroup.SHOULDERS)) {
                imageView.setImageResource(R.drawable.e_all);
            } else if (selectedMuscleGroups.contains(MuscleGroup.TRAPS)) {
                imageView.setImageResource(R.drawable.e_traps);
            } else if (selectedMuscleGroups.contains(MuscleGroup.SHOULDERS)) {
                imageView.setImageResource(R.drawable.e_shoulders);
            } else {
                imageView.setImageResource(R.drawable.e_none);
            }
        } else if (imageView == imageGroupB) {
            if (selectedMuscleGroups.contains(MuscleGroup.TRICEPS) &&
                    selectedMuscleGroups.contains(MuscleGroup.FOREARMS)) {
                imageView.setImageResource(R.drawable.f_all);
            } else if (selectedMuscleGroups.contains(MuscleGroup.TRICEPS)) {
                imageView.setImageResource(R.drawable.f_triceps);
            } else if (selectedMuscleGroups.contains(MuscleGroup.FOREARMS)) {
                imageView.setImageResource(R.drawable.f_forearms);
            } else {
                imageView.setImageResource(R.drawable.f_none);
            }
        } else if (imageView == imageGroupC) {
            if (selectedMuscleGroups.contains(MuscleGroup.MIDDLE_BACK) &&
                    selectedMuscleGroups.contains(MuscleGroup.LOWER_BACK)) {
                imageView.setImageResource(R.drawable.g_all);
            } else if (selectedMuscleGroups.contains(MuscleGroup.MIDDLE_BACK)) {
                imageView.setImageResource(R.drawable.g_middle_back);
            } else if (selectedMuscleGroups.contains(MuscleGroup.LOWER_BACK)) {
                imageView.setImageResource(R.drawable.g_lower_back);
            } else {
                imageView.setImageResource(R.drawable.g_none);
            }
        } else if (imageView == imageGroupD) {
            if (selectedMuscleGroups.contains(MuscleGroup.GLUTES) &&
                    selectedMuscleGroups.contains(MuscleGroup.HAMSTRINGS) &&
                    selectedMuscleGroups.contains(MuscleGroup.CALVES)) {
                imageView.setImageResource(R.drawable.h_all);
            } else if (selectedMuscleGroups.contains(MuscleGroup.GLUTES) &&
                    selectedMuscleGroups.contains(MuscleGroup.HAMSTRINGS)) {
                imageView.setImageResource(R.drawable.h_glutes_hamstrings);
            } else if (selectedMuscleGroups.contains(MuscleGroup.GLUTES) &&
                    selectedMuscleGroups.contains(MuscleGroup.CALVES)) {
                imageView.setImageResource(R.drawable.h_glutes_calves);
            } else if (selectedMuscleGroups.contains(MuscleGroup.HAMSTRINGS) &&
                    selectedMuscleGroups.contains(MuscleGroup.CALVES)) {
                imageView.setImageResource(R.drawable.h_hamstrings_calves);
            } else if (selectedMuscleGroups.contains(MuscleGroup.GLUTES)) {
                imageView.setImageResource(R.drawable.h_glutes);
            } else if (selectedMuscleGroups.contains(MuscleGroup.HAMSTRINGS)) {
                imageView.setImageResource(R.drawable.h_hamstrings);
            } else if (selectedMuscleGroups.contains(MuscleGroup.CALVES)) {
                imageView.setImageResource(R.drawable.h_calves);
            } else {
                imageView.setImageResource(R.drawable.h_none);
            }
        }
    }
}
