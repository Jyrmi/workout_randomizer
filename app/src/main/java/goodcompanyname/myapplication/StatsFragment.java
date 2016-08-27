package goodcompanyname.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import constant.PreferenceTags;
import sqlite.LogsContract;
import sqlite.LogsDbHelper;

/**
 * Created by jeremy on 8/11/16.
 */
public class StatsFragment extends Fragment {
    // todo: sub-tabs to display the different sets of data (logs, charts, ...)
    // todo: more graphs of relevant information

    private final static String TAG = "StatsFragment";
    private static final String emptyChart = "Generate some workouts to see your selections.";
    public static final String ARG_PAGE = "ARG_PAGE";

    TabLayout tabLayout;
    ViewPager viewPager;
    FloatingActionButton fabClearData;
    TextView tooltip;

    RadarFragment radarFragment;
    LogsFragment logsFragment;

    SharedPreferences sharedPreferences;
    DialogInterface.OnClickListener dialogClickListener;

    private int[] tabIcons = {
            R.drawable.ic_track_changes_white_selector,
            R.drawable.ic_format_list_bulleted_white_selector
    };

    public static StatsFragment newInstance(int pageNo) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNo);
        StatsFragment fragment = new StatsFragment();
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
        View view = inflater.inflate(R.layout.fragment_stats, container, false);

        // Setup the viewPager
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.view_pager_stats);
        PagerAdapter pagerAdapter = new PagerAdapter(getChildFragmentManager());
        if (viewPager != null) {
            viewPager.setOffscreenPageLimit(1);
            viewPager.setAdapter(pagerAdapter);
        }

        tabLayout = (TabLayout) view.findViewById(R.id.tab_layout_stats);
        if (tabLayout != null) {
            tabLayout.setupWithViewPager(viewPager);

            for (int i = 0; i < tabLayout.getTabCount(); i++) {
                TabLayout.Tab tab = tabLayout.getTabAt(i);
                if (tab != null)
                    tab.setCustomView(pagerAdapter.getTabView(i));
            }

            tabLayout.getTabAt(0).getCustomView().setSelected(true);
        }

        tooltip = (TextView) view.findViewById(R.id.results_tooltip);

        dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        clearPreferences(PreferenceTags.PREFERENCES_SELECTED_MUSCLE_GROUPS);
                        clearTable();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        fabClearData = (FloatingActionButton) view.findViewById(R.id.fab_clear_data);
        fabClearData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Clear all data?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });

        return view;
    }

    private void clearTable() {
        LogsDbHelper mDbHelper = new LogsDbHelper(getActivity());
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        db.delete(LogsContract.LogEntry.TABLE_NAME, null, null);
        db.close();
    }

    /**
     * Clears a shared preference file of the specified tag.
     * @param sharedPreferencesTag the string representing the preferences file.
     */
    private void clearPreferences(String sharedPreferencesTag) {
        sharedPreferences = getActivity().getSharedPreferences(sharedPreferencesTag, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    private class PagerAdapter extends FragmentPagerAdapter {

        public final int PAGE_COUNT = 2;

        private final String[] mTabsTitle = {"Groups", "Logs"};

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public View getTabView(int position) {
            // Given you have a custom layout in `res/layout/custom_tab.xml` with a TextView and ImageView
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.tab, null);
            ImageView icon = (ImageView) view.findViewById(R.id.icon);
            icon.setImageResource(tabIcons[position]);
            return view;
        }

        @Override
        public Fragment getItem(int pos) {
            switch (pos) {

                case 0:
                    radarFragment = RadarFragment.newInstance(1);
                    return radarFragment;
                case 1:
                    logsFragment = LogsFragment.newInstance(2);
                    return logsFragment;

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
}
