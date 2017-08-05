package com.example.timotiusek.musikonek;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment {

    private MainActivity ma;
    public DashboardFragment() {
        // Required empty public constructor
    }

    public class MyValueFormatter implements IAxisValueFormatter {
        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            String returnValue = "";
            if(value == 1){
                returnValue = "M";
            } else if(value == 2 || value == 4){
                returnValue = "T";
            } else if(value == 3){
                returnValue = "W";
            } else if(value == 5){
                returnValue = "F";
            } else if(value == 6 || value == 7){
                returnValue = "S";
            }
            return returnValue;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ma = (MainActivity) getActivity();
        ma.setChecked(R.id.menu_home);
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        BarChart reportChart = (BarChart) view.findViewById(R.id.report_bar_chart__dashboard_fra);
        BarChart taskChart = (BarChart) view.findViewById(R.id.task_bar_chart__dashboard_fra);
        reportChart.animateY(1000, Easing.EasingOption.Linear);
        taskChart.animateY(1000, Easing.EasingOption.Linear);

        setupChart(reportChart);
        setupChart(taskChart);


        List<BarEntry> reportEntries = new ArrayList<BarEntry>();
        // turn your data into Entry objects
        reportEntries.add(new BarEntry(1, 4));
        reportEntries.add(new BarEntry(2, 2));
        reportEntries.add(new BarEntry(3, 6));
        reportEntries.add(new BarEntry(4, 8));
        reportEntries.add(new BarEntry(5, 5));
        reportEntries.add(new BarEntry(6, 3));
        reportEntries.add(new BarEntry(7, 7));

        BarDataSet data = new BarDataSet(reportEntries , "");
        data.setDrawValues(false);
        data.setColors(Color.parseColor("#6889FF"),  Color.parseColor("#42BD41"), Color.parseColor("#FFB74D"),
                Color.parseColor("#FFB74D") , Color.parseColor("#F36C60"), Color.parseColor("#6889FF"), Color.parseColor("#FFB74D") );


        BarData barData = new BarData(data);

        reportChart.setData(barData);
        reportChart.invalidate(); // refresh

        /**
         * Second Chart
         */

        List<BarEntry> taskEntries = new ArrayList<BarEntry>();
        // turn your data into Entry objects
        taskEntries.add(new BarEntry(1, 1));
        taskEntries.add(new BarEntry(2, 4));
        taskEntries.add(new BarEntry(3, 2));
        taskEntries.add(new BarEntry(4, 7));
        taskEntries.add(new BarEntry(5, 3));
        taskEntries.add(new BarEntry(6, 8));
        taskEntries.add(new BarEntry(7, 3));

        BarDataSet dataEntries = new BarDataSet(taskEntries , "");
        dataEntries.setDrawValues(false);

        dataEntries.setColors(Color.parseColor("#F36C60"),  Color.parseColor("#F36C60"), Color.parseColor("#42BD41"),
                Color.parseColor("#42BD41") , Color.parseColor("#FFB74D"), Color.parseColor("#FFB74D"), Color.parseColor("#6889FF") );


        BarData barDataEntries = new BarData(dataEntries);

        taskChart.setData(barDataEntries);
        taskChart.invalidate(); // refresh

        // Inflate the layout for this fragment
        return view;
    }

    public void setupChart(BarChart chart){
        chart.setDrawBorders(false);
        XAxis xAxis = chart.getXAxis();
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new MyValueFormatter());
        xAxis.setTextColor(Color.parseColor("#B0B0B0"));

        YAxis leftAxis = chart.getAxisLeft();
        YAxis rightAxis = chart.getAxisRight();

        leftAxis.setDrawAxisLine(false);
        leftAxis.setDrawGridLines(false);
        leftAxis.setDrawLabels(false);

        rightAxis.setDrawAxisLine(false);
        rightAxis.setDrawGridLines(false);
        rightAxis.setDrawLabels(false);

        Legend legend = chart.getLegend();

        legend.setEnabled(false);

        chart.setTouchEnabled(false);

        Description description = new Description();
        description.setText("");
        chart.setDescription(description);
    }

}
