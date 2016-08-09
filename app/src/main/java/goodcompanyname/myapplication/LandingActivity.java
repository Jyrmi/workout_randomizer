package goodcompanyname.myapplication;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.Response;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;


import java.util.ArrayList;

public class LandingActivity extends AppCompatActivity {

    ImageView imageAbs;
    ImageView imageBiceps;
    ImageView imageChest;
    ImageView imageForearms;
    ImageView imageFrontCalves;
    ImageView imageFrontFeet;
    ImageView imageFrontHamstrings;
    ImageView imageFrontHands;
    ImageView imageFrontHead;
    ImageView imageFrontKnees;
    ImageView imageFrontShoulders;
    ImageView imageFrontTraps;
    ImageView imageNeck;
    ImageView imageQuads;

    Button buttonAbs;
//    Button buttonNeck;
    Button buttonFrontHamstrings;
//    Button buttonTraps;
//    Button buttonShoulders;
    Button buttonChest;
//    Button buttonBiceps;
//    Button buttonForearms;
//    Button buttonLats;
//    Button buttonTriceps;
//    Button buttonMiddleBack;
//    Button buttonLowerBack;
//    Button buttonGlutes;

    RequestQueue queue;
    String url;
    int height;
    int inSampleSize;
    double resizeCoefficient;
    int[] coordinates;

    // A list to display picked items
    private ArrayList<String> muscleGroups = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        imageAbs = (ImageView) findViewById(R.id.image_abs);
        imageFrontHamstrings = (ImageView) findViewById(R.id.image_front_hamstrings);
        imageChest = (ImageView) findViewById(R.id.image_chest);

        buttonAbs = (Button) findViewById(R.id.button_abs);
        buttonFrontHamstrings = (Button) findViewById(R.id.button_front_hamstrings);
        buttonChest = (Button) findViewById(R.id.button_chest);

        buttonAbs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imageAbs.getVisibility() == View.INVISIBLE) {
                    imageAbs.setVisibility(View.VISIBLE);
                } else {
                    imageAbs.setVisibility(View.INVISIBLE);
                }
            }
        });

        buttonFrontHamstrings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imageFrontHamstrings.getVisibility() == View.INVISIBLE) {
                    imageFrontHamstrings.setVisibility(View.VISIBLE);
                } else {
                    imageFrontHamstrings.setVisibility(View.INVISIBLE);
                }
            }
        });

        buttonChest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imageChest.getVisibility() == View.INVISIBLE) {
                    imageChest.setVisibility(View.VISIBLE);
                } else {
                    imageChest.setVisibility(View.INVISIBLE);
                }
            }
        });

        // Getting height of the display
//        Display display = getWindowManager().getDefaultDisplay();
//        Point size = new Point();
//        display.getSize(size);
//        height = size.y;
//        resizeCoefficient = 1024/height;
//        inSampleSize = (int) Math.ceil(resizeCoefficient);

//        imageAbs = (ImageView) findViewById(R.id.image_abs);
//        imageBiceps = (ImageView) findViewById(R.id.image_biceps);
//        imageChest = (ImageView) findViewById(R.id.image_chest);
//        imageForearms = (ImageView) findViewById(R.id.image_forearms);
//        imageFrontCalves = (ImageView) findViewById(R.id.image_front_calves);
//        imageFrontFeet = (ImageView) findViewById(R.id.image_front_feet);
//        imageFrontHamstrings = (ImageView) findViewById(R.id.image_front_hamstrings);
//        imageFrontHands = (ImageView) findViewById(R.id.image_front_hands);
//        imageFrontHead = (ImageView) findViewById(R.id.image_front_head);
//        imageFrontKnees = (ImageView) findViewById(R.id.image_front_knees);
//        imageFrontShoulders = (ImageView) findViewById(R.id.image_front_shoulders);
//        imageFrontTraps = (ImageView) findViewById(R.id.image_front_traps);
//        imageNeck = (ImageView) findViewById(R.id.image_neck);
//        imageQuads = (ImageView) findViewById(R.id.image_quads);

//        imageAbs.setImageBitmap(
//                decodeSampledBitmapFromResource(getResources(), R.id.image_abs));
//        imageBiceps.setImageBitmap(
//                decodeSampledBitmapFromResource(getResources(), R.id.image_biceps));
//        imageChest.setImageBitmap(
//                decodeSampledBitmapFromResource(getResources(), R.id.image_chest));
//        imageForearms.setImageBitmap(
//                decodeSampledBitmapFromResource(getResources(), R.id.image_forearms));
//        imageFrontCalves.setImageBitmap(
//                decodeSampledBitmapFromResource(getResources(), R.id.image_front_calves));
//        imageFrontFeet.setImageBitmap(
//                decodeSampledBitmapFromResource(getResources(), R.id.image_front_feet));
//        imageFrontHamstrings.setImageBitmap(
//                decodeSampledBitmapFromResource(getResources(), R.id.image_front_hamstrings));
//        imageFrontHands.setImageBitmap(
//                decodeSampledBitmapFromResource(getResources(), R.id.image_front_hands));
//        imageFrontHead.setImageBitmap(
//                decodeSampledBitmapFromResource(getResources(), R.id.image_front_head));
//        imageFrontKnees.setImageBitmap(
//                decodeSampledBitmapFromResource(getResources(), R.id.image_front_knees));
//        imageFrontShoulders.setImageBitmap(
//                decodeSampledBitmapFromResource(getResources(), R.id.image_front_shoulders));
//        imageFrontTraps.setImageBitmap(
//                decodeSampledBitmapFromResource(getResources(), R.id.image_front_traps));
//        imageNeck.setImageBitmap(
//                decodeSampledBitmapFromResource(getResources(), R.id.image_neck));
//        imageQuads.setImageBitmap(
//                decodeSampledBitmapFromResource(getResources(), R.id.image_quads));

//        buttonNeck = (Button) findViewById(R.id.button_neck);
//        buttonTraps = (Button) findViewById(R.id.button_traps);
//        buttonShoulders = (Button) findViewById(R.id.button_shoulders);
//        buttonChest = (Button) findViewById(R.id.button_chest);
//        buttonBiceps = (Button) findViewById(R.id.button_biceps);
//        buttonForearms = (Button) findViewById(R.id.button_forearm);
//        buttonLats = (Button) findViewById(R.id.button_lats);
//        buttonTriceps = (Button) findViewById(R.id.button_triceps);
//        buttonMiddleBack = (Button) findViewById(R.id.button_middleback);
//        buttonLowerBack = (Button) findViewById(R.id.button_lowerback);
//        buttonGlutes = (Button) findViewById(R.id.button_glutes);
//
//        setButtonListener(buttonNeck, "Neck");
//        setButtonListener(buttonTraps, "Traps");
//        setButtonListener(buttonShoulders, "Shoulders");
//        setButtonListener(buttonChest, "Chest");
//        setButtonListener(buttonBiceps, "Biceps");
//        setButtonListener(buttonForearms, "Forearm");
//        setButtonListener(buttonTriceps, "Lats");
//        setButtonListener(buttonTriceps, "Triceps");
//        setButtonListener(buttonMiddleBack, "Middle Back");
//        setButtonListener(buttonLowerBack, "Lower Back");
//        setButtonListener(buttonGlutes, "Glutes");

//        Button buttonGenerate = (Button) findViewById(R.id.button_generate_workout);
//        buttonGenerate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                navigateToWorkout();
//            }
//        });
//        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

//        queue = Volley.newRequestQueue(this);
//        url ="http://www.google.com";
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
//
//    /** Called when the user clicks the Send button */
//    public void setButtonListener(Button button, final String bodyPart) {
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                addMuscleGroup(bodyPart);
//            }
//        });
//    }
//
//    /** Called when the user clicks the Send button */
//    public void addMuscleGroup(String muscleGroup) {
//        // Add the muscle group to the muscleGroups array
//        if (muscleGroups != null && !muscleGroups.contains(muscleGroup)) {
//            muscleGroups.add(muscleGroup);
//        }
//
//        updateTextView();
//    }
//
//    public void updateTextView() {
//        TextView muscleGroupList = (TextView) findViewById(R.id.list_muscle_group);
//        muscleGroupList.setText(muscleGroups.toString());
//    }

    /** Called when the user clicks the Send button */
    public void navigateToWorkout() {
        // Do something in response to button
        Intent intent = new Intent(this, WorkoutActivity.class);
        intent.putExtra("EXTRA_MUSCLE_GROUPS", muscleGroups);
        startActivity(intent);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

//    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
//                                                         int reqWidth, int reqHeight) {
//
//        // First decode with inJustDecodeBounds=true to check dimensions
//        final BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;
//        BitmapFactory.decodeResource(res, resId, options);
//
//        // Calculate inSampleSize
//        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
//
//        // Decode bitmap with inSampleSize set
//        options.inJustDecodeBounds = false;
//        return BitmapFactory.decodeResource(res, resId, options);
//    }

    public Bitmap decodeSampledBitmapFromResource(Resources res, int resId) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = inSampleSize;

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

}
