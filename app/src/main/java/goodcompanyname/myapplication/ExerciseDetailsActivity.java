package goodcompanyname.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import constant.PreferenceTags;
import sqlite.ExerciseContract;

/**
 * Created by jeremy on 8/26/16.
 */
public class ExerciseDetailsActivity extends AppCompatActivity {

    public static final String TAG = "ExerciseDetailsActivity";
    public static final String ARG_PAGE = "ARG_PAGE";

    TextView textViewName;
    TextView textViewGroup;
    ImageView exerciseImage1;
    ImageView exerciseImage2;
    ImageView exerciseImage3;
    ImageView exerciseImage4;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_details);

        textViewName = (TextView) findViewById(R.id.details_name);
        textViewGroup = (TextView) findViewById(R.id.details_group);

        exerciseImage1 = (ImageView) findViewById(R.id.exercise_image_1);
        exerciseImage2 = (ImageView) findViewById(R.id.exercise_image_2);
        exerciseImage3 = (ImageView) findViewById(R.id.exercise_image_3);
        exerciseImage4 = (ImageView) findViewById(R.id.exercise_image_4);

        textViewName.setText(getIntent().getExtras().getString("EXTRA_NAME"));
        textViewGroup.setText(getIntent().getExtras().getString("EXTRA_GROUP"));

        setMedia();
    }

    public Boolean setMedia() {
//        String videoUrl = getIntent().getExtras().getString("EXTRA_VIDEO");
        String images = getIntent().getExtras().getString("EXTRA_IMAGES");

        if (images == null) return false;

        String[] imageUrls = TextUtils.split(images , ",");

        // Get Images of the exercise
        int imagesSet = 0;
        for (String imageUrlString : imageUrls) {

            Bitmap bmp = null;
            try {
                bmp = new RetrieveImageTask().execute(imageUrlString).get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

            if (imagesSet == 0) {
                exerciseImage1.setImageBitmap(bmp);
            } else if (imagesSet == 1) {
                exerciseImage2.setImageBitmap(bmp);
            } else if (imagesSet == 2) {
                exerciseImage3.setImageBitmap(bmp);
            } else {
                exerciseImage4.setImageBitmap(bmp);
            }

            imagesSet++;
        }

        return true;

//        // Setup the VideoView with the exercise's video
//        VideoView cardVideo = (VideoView) v.findViewById(R.id.card_video);
//        MediaController mediaController = new MediaController(v.getContext());
//        mediaController.setAnchorView(cardVideo);
//        cardVideo.setMediaController(mediaController);
//        cardVideo.setVideoURI(Uri.parse("http:" + videoUrl));
//        cardVideo.start();
//
//        try {
//            cardVideo.setVideoURI(new RetrieveVideoTask().execute(queryResult.a).get());
//            cardVideo.start();
//            Log.d("ViewHolder.onClick()", "started video at http:" + queryResult.a);
//        } catch (InterruptedException | ExecutionException e) {
//            e.printStackTrace();
//        }
    }

    private class RetrieveImageTask extends AsyncTask<String, Void, Bitmap> {
        protected Bitmap doInBackground(String... urls) {
            return loadImageFromNetwork(urls[0]);
        }
    }

    private Bitmap loadImageFromNetwork(String url) {
        try {
            URL imageUrl = new URL(url);
            try {
                return BitmapFactory.decodeStream(imageUrl.openConnection().getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
