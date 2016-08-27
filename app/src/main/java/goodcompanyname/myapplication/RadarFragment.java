package goodcompanyname.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;

import java.util.ArrayList;

import constant.MuscleGroup;
import constant.PreferenceTags;
import constant.TwoTuple;
import formatter.RadarChartFormatter;

/**
 * Created by jeremy on 8/11/16.
 */
public class RadarFragment extends Fragment {

    private final static String TAG = "StatsFragment";
    private static final String emptyChart = "Generate some workouts to see your selections.";
    public static final String ARG_PAGE = "ARG_PAGE";

    TextView tooltip;
    SharedPreferences sharedPreferences;

    RadarChart muscleGroupsChart;
    RadarDataSet radarDataSet;
    RadarData radarData;

    public static RadarFragment newInstance(int pageNo) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNo);
        RadarFragment fragment = new RadarFragment();
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
        View view = inflater.inflate(R.layout.fragment_radar, container, false);

        tooltip = (TextView) view.findViewById(R.id.results_tooltip);
        muscleGroupsChart = (RadarChart) view.findViewById(R.id.radar_chart);

        return view;
    }

    private ArrayList<TwoTuple<String, Integer>> getPreferences(String sharedPreferencesTag) {
        ArrayList<TwoTuple<String, Integer>> entries = new ArrayList<>();

        sharedPreferences = getActivity().getSharedPreferences(sharedPreferencesTag,
                Context.MODE_PRIVATE);

        int count;
        for (String muscleGroup : MuscleGroup.getGroupsAsStrings()) {
            count = sharedPreferences.getInt(muscleGroup.toString(), 0);
            if (count > 0) {
                entries.add(new TwoTuple(muscleGroup, count));
            }
        }

        return entries;
    }

    public Boolean refreshChart() {
        tooltip.setVisibility(View.GONE);

        ArrayList<String> correspondingMuscleGroups = new ArrayList<>();

        muscleGroupsChart.setDescription("");
        muscleGroupsChart.setNoDataText("");
        muscleGroupsChart.setNoDataTextDescription("");

        muscleGroupsChart.setWebLineWidth(1f);
        muscleGroupsChart.setWebLineWidthInner(1f);
        muscleGroupsChart.setWebAlpha(100);

        ArrayList<RadarEntry> radarChartEntries = new ArrayList<>();
        for (TwoTuple<String, Integer> entry :
                getPreferences(PreferenceTags.PREFERENCES_SELECTED_MUSCLE_GROUPS)) {
            radarChartEntries.add(new RadarEntry(entry.b));
            correspondingMuscleGroups.add(entry.a);
        }

        if (radarChartEntries.isEmpty()) {
            muscleGroupsChart.setVisibility(View.GONE);
            tooltip.setVisibility(View.VISIBLE);
            tooltip.setText(emptyChart);
            return false;
        }

        radarDataSet = new RadarDataSet(radarChartEntries, "Selected Muscle Groups");
        radarDataSet.setColor(Color.parseColor("#"+Integer.toHexString(ContextCompat.getColor(
                getActivity(), R.color.colorAccent))));
        radarDataSet.setFillColor(Color.parseColor("#"+Integer.toHexString(ContextCompat.getColor(
                getActivity(), R.color.colorLight))));
        radarDataSet.setDrawFilled(true);
        radarDataSet.setFillAlpha(180);
        radarDataSet.setLineWidth(2f);
        radarDataSet.setDrawHighlightCircleEnabled(true);
        radarDataSet.setDrawHighlightIndicators(false);
//
        radarData = new RadarData(radarDataSet);
//        radarData.setValueTextSize(8f);
//        radarData.setDrawValues(false);
//        radarData.setValueTextColor(Color.BLACK);

        muscleGroupsChart.setData(radarData);
        muscleGroupsChart.invalidate();

        muscleGroupsChart.animateXY(1400, 1400,
                Easing.EasingOption.EaseInOutQuad,
                Easing.EasingOption.EaseInOutQuad);

        XAxis xAxis = muscleGroupsChart.getXAxis();
        xAxis.setTextSize(9f);
        xAxis.setYOffset(0f);
        xAxis.setXOffset(0f);
        xAxis.setValueFormatter(new RadarChartFormatter(correspondingMuscleGroups));

        YAxis yAxis = muscleGroupsChart.getYAxis();
        yAxis.setLabelCount(5, false);
        yAxis.setTextSize(9f);
        yAxis.setDrawLabels(false);
        yAxis.setAxisMinValue(0f);

        Legend l = muscleGroupsChart.getLegend();
        l.setPosition(Legend.LegendPosition.ABOVE_CHART_CENTER);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(5f);
        l.setTextColor(Color.BLACK);

        muscleGroupsChart.setVisibility(View.VISIBLE);

        return true;
    }
}
