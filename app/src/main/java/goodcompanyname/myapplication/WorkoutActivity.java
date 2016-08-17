package goodcompanyname.myapplication;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class WorkoutActivity extends AppCompatActivity {

    private static final String TAG = "WorkoutActivity";

    private ListView listViewExercises;
    private Button buttonDone;

    private ArrayList<MuscleGroup> selectedMuscleGroups;
    private DbHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        // DEBUG
//        Log.d(TAG, Calendar.getInstance().getTime().toString());

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        selectedMuscleGroups = (ArrayList<MuscleGroup>) bundle.get("EXTRA_MUSCLE_GROUPS");

        // DEBUG
//        Log.d(TAG, selectedMuscleGroups.toString());

        ArrayList<String> muscleGroupList = new ArrayList();
        ArrayList<String> exerciseList = new ArrayList();
        for (MuscleGroup muscleGroup : selectedMuscleGroups) {
            for (String exercise : muscleGroup.getExercises()) {
                muscleGroupList.add(muscleGroup.toString());
                exerciseList.add(exercise);
            }
        }

        StringStringAdapter exerciseAdapter = new StringStringAdapter(this, muscleGroupList, exerciseList);

        listViewExercises = (ListView) findViewById(R.id.exercises_list_view);
        listViewExercises.setAdapter(exerciseAdapter);

        buttonDone = (Button) findViewById(R.id.button_done);
        buttonDone.setOnClickListener(new View.OnClickListener() {
            ArrayList<String> checkedExercises = new ArrayList<>();

            @Override
            public void onClick(View view) {
                int i;
                for (i = 0; i < listViewExercises.getChildCount(); i++) {
                    View cardView = listViewExercises.getChildAt(i);
                    CheckBox checkBox =
                            (CheckBox) cardView.findViewById(R.id.list_view_checkbox);

                    if (checkBox.isChecked()) {
                        TextView textViewExercise =
                                (TextView) cardView.findViewById(R.id.list_view_exercise);
                        checkedExercises.add(textViewExercise.getText().toString());
//                        Log.d(TAG, textViewExercise.getText().toString());
                    }
                }

                writeDbEntry(checkedExercises);

                // Start the Results activity
                Intent intent = new Intent(view.getContext(), ResultsActivity.class);
                intent.putExtra("EXTRA_COMPLETED_EXERCISES", checkedExercises);
                intent.putExtra("EXTRA_MUSCLE_GROUPS", selectedMuscleGroups);
                startActivity(intent);
                finish();
            }
        });
    }

    private void writeDbEntry(ArrayList<String> checkedExercises) {
        mHelper = new DbHelper(this);
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(WorkoutContract.TaskEntry.COL_TIME, Calendar.getInstance().toString());
        values.put(WorkoutContract.TaskEntry.COL_EXERCISES, checkedExercises.toString());
        db.insertWithOnConflict(WorkoutContract.TaskEntry.TABLE,
                null,
                values,
                SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    private class StringStringAdapter extends BaseAdapter {
        private ArrayList<String> muscleGroups;
        private ArrayList<String> exercises;
        Activity activity;
        TextView textViewExercise;
        TextView textViewMuscleGroup;

        public StringStringAdapter(Activity activity, ArrayList<String> muscleGroups,
                               ArrayList<String> exercises) {
            super();
            this.activity = activity;
            this.muscleGroups = muscleGroups;
            this.exercises = exercises;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return muscleGroups.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return muscleGroups.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(int position, View cardView, ViewGroup parent) {
            // TODO Auto-generated method stub
            LayoutInflater inflater = activity.getLayoutInflater();

            if (cardView == null) {
                cardView = inflater.inflate(R.layout.card_view, null);

                textViewExercise = (TextView) cardView.findViewById(R.id.list_view_exercise);
                textViewMuscleGroup = (TextView) cardView.findViewById(R.id.list_view_muscle_group);
            }

            textViewExercise.setText(exercises.get(position));
            textViewMuscleGroup.setText(muscleGroups.get(position));

            return cardView;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_workout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
