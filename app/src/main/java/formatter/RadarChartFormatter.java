package formatter;

import android.util.Log;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.AxisValueFormatter;

import java.util.ArrayList;

import constant.MuscleGroup;

/**
 * Created by jeremy on 8/20/16.
 */
public class RadarChartFormatter implements AxisValueFormatter {
    private static final String TAG = "RadarChartFormatter";

    private ArrayList<String> mValues;

    public RadarChartFormatter(ArrayList<String> values) {
        this.mValues = values;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        // "value" represents the position of the label on the axis (x or y)
        try {
            return mValues.get((int) value).toString();
        } catch (IndexOutOfBoundsException e) {
            Log.d(TAG, "attempted to access index " + Float.toString(value)
                    + ", but array is of size " + Integer.toString(mValues.size()));
            return "bounds error";
        }
    }

    /** this is only needed if numbers are returned, else return 0 */
    @Override
    public int getDecimalDigits() { return 0; }
}
