package com.example.james.cyclecoach;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.androidplot.ui.AnchorPosition;
import com.androidplot.ui.XLayoutStyle;
import com.androidplot.ui.YLayoutStyle;
import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYStepMode;

import java.text.FieldPosition;
import java.text.Format;
import java.text.NumberFormat;
import java.text.ParsePosition;

public class ProgressActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private XYPlot _plot;
    private Spinner _timesSpinner;
    private Spinner _categorySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        // initialize our XYPlot reference:
        _plot = (XYPlot) findViewById(R.id.plot);

        // Change the range, domain and title colors to black:
        _plot.getTitleWidget().getLabelPaint().setColor(Color.BLACK);
        _plot.getDomainLabelWidget().getLabelPaint().setColor(Color.BLACK);
        _plot.getRangeLabelWidget().getLabelPaint().setColor(Color.BLACK);

        // Change the range and domain tick labels to black:
        _plot.getGraphWidget().getDomainOriginTickLabelPaint().setColor(Color.BLACK);
        _plot.getGraphWidget().getRangeOriginTickLabelPaint().setColor(Color.BLACK);
        _plot.getGraphWidget().getDomainTickLabelPaint().setColor(Color.BLACK);
        _plot.getGraphWidget().getRangeTickLabelPaint().setColor(Color.BLACK);

        _plot.getDomainLabelWidget().position(0, XLayoutStyle.ABSOLUTE_FROM_CENTER, 0,
                YLayoutStyle.RELATIVE_TO_BOTTOM, AnchorPosition.BOTTOM_MIDDLE);

        // Change the far left and very bottom grid lines to black:
        _plot.getGraphWidget().getDomainOriginLinePaint().setColor(Color.BLACK);
        _plot.getGraphWidget().getRangeOriginLinePaint().setColor(Color.BLACK);

        _timesSpinner = (Spinner) findViewById(R.id.timesSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.progress_times, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        _timesSpinner.setAdapter(adapter);
        _timesSpinner.setOnItemSelectedListener(this);

        _categorySpinner = (Spinner) findViewById(R.id.categorySpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        adapter = ArrayAdapter.createFromResource(this, R.array.progress_categories,
                android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        _categorySpinner.setAdapter(adapter);
        _categorySpinner.setOnItemSelectedListener(this);
    }

    private void updateXAxes(String domainLabel, int lowerBound, int upperBound, String[] xLabels) {
        _plot.setDomainLabel(domainLabel);
        _plot.setDomainBoundaries(lowerBound, upperBound, BoundaryMode.FIXED);
        _plot.setDomainStep(XYStepMode.INCREMENT_BY_VAL, 1);
        _plot.getGraphWidget().setDomainValueFormat(new GraphAxisLabelFormat(xLabels));
    }

    private void updateYAxes(String rangeLabel, int lowerBound, int upperBound, String[] yLabels) {
        _plot.setRangeLabel(rangeLabel);
        _plot.setRangeBoundaries(lowerBound, upperBound, BoundaryMode.FIXED);
        _plot.setRangeStep(XYStepMode.INCREMENT_BY_VAL, 1);
        _plot.getGraphWidget().setRangeValueFormat(new GraphAxisLabelFormat(yLabels));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ((TextView) parent.getChildAt(0)).setGravity(Gravity.CENTER);

        int pId = parent.getId();
        switch (pId) {
            case R.id.timesSpinner:
                if (_timesSpinner.getItemAtPosition(position).toString().equals("Daily")) {
                    String[] xLabels = {"12am", "3am", "6am", "9am", "12pm", "3pm", "6pm", "9pm"};
                    updateXAxes("Time", 0, 7, xLabels);
                } else if (_timesSpinner.getItemAtPosition(position).toString().equals("Weekly")) {
                    String[] xLabels = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
                    updateXAxes("Day of Week", 0, 6, xLabels);
                } else if (_timesSpinner.getItemAtPosition(position).toString().equals("Monthly")) {
                    String[] xLabels = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep",
                            "Oct", "Nov", "Dec"};
                    updateXAxes("Month", 0, 11, xLabels);
                }
                break;
            case R.id.categorySpinner:
                if (_categorySpinner.getItemAtPosition(position).toString().equals("Goals")) {
                    _plot.setRangeLabel("Goals");
                } else if (_categorySpinner.getItemAtPosition(position).toString().equals("Distance")) {
                    String[] yLabels = {"0", "10", "20", "30", "40", "50", "60", "70", "80", "90", "100"};
                    updateYAxes("Miles", 0, 10, yLabels);
                }
                break;
        }
        _plot.redraw();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

class GraphAxisLabelFormat extends Format {

    String[] xLabels;

    public GraphAxisLabelFormat(String[] xLabels) {
        this.xLabels = xLabels;
    }

    @Override
    public StringBuffer format(Object arg0, StringBuffer arg1, FieldPosition arg2) {
        int parsedInt = Math.round(Float.parseFloat(arg0.toString()));
        Log.d("test", parsedInt + " " + arg1 + " " + arg2);
        String labelString = xLabels[parsedInt];
        arg1.append(labelString);
        return arg1;
    }

    @Override
    public Object parseObject(String arg0, ParsePosition arg1) {
        // TODO Auto-generated method stub
        return java.util.Arrays.asList(xLabels).indexOf(arg0);
    }
}
