package goodcompanyname.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.Response;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;


import java.util.ArrayList;

public class LandingActivity extends ActionBarActivity {

    RequestQueue queue;
    String url;

    // A list to display picked items
    private ArrayList<String> muscleGroups = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        queue = Volley.newRequestQueue(this);
        url ="http://www.google.com";
//        final TextView mTextView = (TextView) findViewById(R.id.volley_request);
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        // Display the first 500 characters of the response string.
//                        mTextView.setText("Response is: "+ response.substring(0,500));
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                mTextView.setText("That didn't work!");
//            }
//        });
//        queue.add(stringRequest);

        Button button_neck = (Button) findViewById(R.id.button_neck);
        Button button_traps = (Button) findViewById(R.id.button_traps);
        Button button_shoulders = (Button) findViewById(R.id.button_shoulders);
        Button button_chest = (Button) findViewById(R.id.button_chest);
        Button button_biceps = (Button) findViewById(R.id.button_biceps);
        Button button_forearm = (Button) findViewById(R.id.button_forearm);
        Button button_lats = (Button) findViewById(R.id.button_lats);
        Button button_triceps = (Button) findViewById(R.id.button_triceps);
        Button button_middleback = (Button) findViewById(R.id.button_middleback);
        Button button_lowerback = (Button) findViewById(R.id.button_lowerback);

        setButtonListener(button_neck, "Neck");
        setButtonListener(button_traps, "Traps");
        setButtonListener(button_shoulders, "Shoulders");
        setButtonListener(button_chest, "Chest");
        setButtonListener(button_biceps, "Biceps");
        setButtonListener(button_forearm, "Forearm");
        setButtonListener(button_lats, "Lats");
        setButtonListener(button_triceps, "Triceps");
        setButtonListener(button_middleback, "Middle Back");
        setButtonListener(button_lowerback, "Lower Back");


        Button button_login = (Button) findViewById(R.id.button_generate_workout);
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToWorkout();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_landing, menu);
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

    /** Called when the user clicks the Send button */
    public void setButtonListener(Button button, final String bodyPart) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMuscleGroup(bodyPart);
            }
        });
    }

    /** Called when the user clicks the Send button */
    public void addMuscleGroup(String muscleGroup) {
        // Add the muscle group to the muscleGroups array
        if (muscleGroups != null && !muscleGroups.contains(muscleGroup)) {
            muscleGroups.add(muscleGroup);
        }

        updateTextView();
    }

    public void updateTextView() {
        TextView muscleGroupList = (TextView) findViewById(R.id.list_muscle_group);
        muscleGroupList.setText(muscleGroups.toString());
    }

    /** Called when the user clicks the Send button */
    public void navigateToWorkout() {
        // Do something in response to button
        Intent intent = new Intent(this, ActivityWorkout.class);
        intent.putExtra("EXTRA_MUSCLE_GROUPS", muscleGroups);
        startActivity(intent);
    }
}
