package goodcompanyname.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import constant.PreferenceTags;
import constant.Setting;


public class MainActivity extends AppCompatActivity
        implements SelectionFragment.OnMuscleGroupsSelectedListener {

    private static final String TAG = "MainActivity";

    private TabLayout tabLayout;

    SelectionFragment selectionFragment;
    WorkoutFragment workoutFragment;
    ResultsFragment resultsFragment;
    SettingsFragment settingsFragment;

    private int[] tabIcons = {
            R.drawable.ic_accessibility_white_selector,
            R.drawable.ic_rowing_white_selector,
            R.drawable.ic_trending_up_white_selector,
            R.drawable.ic_tune_white_selector
    };

    public void onFinishSelection(ArrayList<String> selectedMuscleGroups) {
        Log.d(TAG, "MainActivity.onFinishSelection()");
        if (workoutFragment.exerciseList.size() >= 10) { // Don't queue past 10 exercises
            Snackbar.make(selectionFragment.getView(), "You already have 10+ exercises waiting!",
                    Snackbar.LENGTH_SHORT).setAction("Action", null).show();
        } else {
            settingsFragment.updatePreferences();
            workoutFragment.addMuscleGroupSelections(selectedMuscleGroups);
            tabLayout.getTabAt(1).select();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup the viewPager
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        if (viewPager != null)
            viewPager.setAdapter(pagerAdapter);

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        if (tabLayout != null) {
            tabLayout.setupWithViewPager(viewPager);

            for (int i = 0; i < tabLayout.getTabCount(); i++) {
                TabLayout.Tab tab = tabLayout.getTabAt(i);
                if (tab != null)
                    tab.setCustomView(pagerAdapter.getTabView(i));
            }

            tabLayout.getTabAt(0).getCustomView().setSelected(true);
        }

        firstRunSettingsCheck();
    }

    @Override
    public void onPause() {
        super.onPause();

        settingsFragment.updatePreferences();
        workoutFragment.updatePreferences();
    }

    public void firstRunSettingsCheck() {
        // Set all settings to true on first run
        SharedPreferences settingsPreferences = getSharedPreferences(
                PreferenceTags.PREFERENCES_SETTINGS, Context.MODE_PRIVATE);

        SharedPreferences defaultPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        if (defaultPreferences.getBoolean("firstrun", true)) {

            clearPreferences();

            // Do first run stuff here
            SharedPreferences.Editor editor = settingsPreferences.edit();
            for (Setting value : Setting.values()) {
                editor.putString(value.toString(), value.getCategory().toString());
            }
            editor.apply();

            // Set "firstrun" to false
            editor = defaultPreferences.edit();
            editor.putBoolean("firstrun", false).apply();
        }
    }

    public void clearPreferences() {
        PreferenceManager.getDefaultSharedPreferences(this).edit().clear().apply();
        getSharedPreferences(PreferenceTags.PREFERENCES_SETTINGS, Context.MODE_PRIVATE).edit().clear().apply();
        getSharedPreferences(PreferenceTags.PREFERENCES_QUEUED_EXERCISES, Context.MODE_PRIVATE).edit().clear().apply();
        getSharedPreferences(PreferenceTags.PREFERENCES_SELECTED_MUSCLE_GROUPS, Context.MODE_PRIVATE).edit().clear().apply();
    }

    private class PagerAdapter extends FragmentPagerAdapter {

        public final int PAGE_COUNT = 4;

        private final String[] mTabsTitle = {"Target", "Workout", "Stats", "Settings"};

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public View getTabView(int position) {
            // Given you have a custom layout in `res/layout/custom_tab.xml` with a TextView and ImageView
            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.tab, null);
            TextView title = (TextView) view.findViewById(R.id.title);
            title.setText(mTabsTitle[position]);
            ImageView icon = (ImageView) view.findViewById(R.id.icon);
            icon.setImageResource(tabIcons[position]);
            return view;
        }

        @Override
        public Fragment getItem(int pos) {
            switch (pos) {

                case 0:
                    selectionFragment = SelectionFragment.newInstance(1);
                    return selectionFragment;
                case 1:
                    workoutFragment = WorkoutFragment.newInstance(2);
                    return workoutFragment;
                case 2:
                    resultsFragment = ResultsFragment.newInstance(3);
                    return resultsFragment;
                case 3:
                    settingsFragment = SettingsFragment.newInstance(4);
                    return settingsFragment;

            }
            return null;
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTabsTitle[position];
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
    public void navigate_to_landing(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, SelectionFragment.class);
        startActivity(intent);
        finish();
    }
}
