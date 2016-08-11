package goodcompanyname.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jeremy on 8/11/16.
 */
public class ResultsActivity extends AppCompatActivity {

    TextView textViewResults;
    Button buttonRestart;

    ArrayList<String> completedExercises;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        completedExercises = bundle.getStringArrayList("COMPLETED_EXERCISES");

        textViewResults = (TextView) findViewById(R.id.results_text_view);
        textViewResults.setText(completedExercises.toString());

        buttonRestart = (Button) findViewById(R.id.restart_button);
        buttonRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), SelectionActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
