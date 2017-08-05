package com.example.timotiusek.musikonek;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.timotiusek.musikonek.CustomClass.Report;
import com.example.timotiusek.musikonek.CustomClass.ReportPageAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReportPageFragment extends Fragment {
    @BindView(R.id.report_lv__report_page_fra)
    ListView reportListView;
    @BindView(R.id.add_report_fab__report_page_fra)
    FloatingActionButton addReportFAB;
    ArrayList<Report> reports;
    ReportPageAdapter reportPageAdapter;
    MainActivity ma;

    public ReportPageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_report_page, container, false);
        ButterKnife.bind(this, v);
        ma = (MainActivity) getActivity();
        ma.setTitle("Laporan");
        reports = new ArrayList<>();
        reports.add(new Report("Laporan Minggu 1", "28 April 2017, 17:30 WIB"));
        reports.add(new Report("Laporan Minggu 2", "5 Mei 2017, 17:30 WIB"));


        reportPageAdapter = new ReportPageAdapter(reports, ma);
        reportListView.setAdapter(reportPageAdapter);

        addReportFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "FAB Clicked", Toast.LENGTH_SHORT).show();
            }
        });


        // Inflate the layout for this fragment
        return v;
    }

}
