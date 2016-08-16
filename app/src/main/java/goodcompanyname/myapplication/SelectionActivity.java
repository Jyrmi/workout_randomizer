package goodcompanyname.myapplication;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

public class SelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Front"));
        tabLayout.addTab(tabLayout.newTab().setText("Back"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        Button buttonGenerate = (Button) findViewById(R.id.button_generate);
        buttonGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ArrayList<MuscleGroup> selectedMuscleGroups = new ArrayList();
//                ArrayList<Fragment> selectionFragments =
//                        (ArrayList) getSupportFragmentManager().get;
//                for (Fragment fragment : selectionFragments) {
//                    for (MuscleGroup muscleGroup : fragment.getSelectedMuscleGroups()) {
//                        if (!selectedMuscleGroups.contains((muscleGroup)))
//                            selectedMuscleGroups.add(muscleGroup);
//                    }
//                }
//
//                if (selectedMuscleGroups.isEmpty()) {
//                    toastInvalidSelection();
//                } else {
//                    navigateToWorkout(selectedMuscleGroups);
//                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /** Called when the user clicks the Send button */
    public void navigateToWorkout(ArrayList<MuscleGroup> selectedMuscleGroups) {
        // Do something in response to button
        Intent intent = new Intent(this, WorkoutActivity.class);
        intent.putExtra("EXTRA_MUSCLE_GROUPS", selectedMuscleGroups);
        startActivity(intent);
    }

    /** Called when the user clicks the Send button */
    public void toastInvalidSelection() {
        Toast.makeText(this, R.string.invalid_selection, Toast.LENGTH_SHORT).show();
    }
}
