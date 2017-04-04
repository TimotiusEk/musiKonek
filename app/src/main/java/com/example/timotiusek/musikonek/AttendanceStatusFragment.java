package com.example.timotiusek.musikonek;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class AttendanceStatusFragment extends Fragment {
    @BindView(R.id.attendance_list_view)
    ListView listView;
    String whichView;
    LayoutInflater inflater;
    ViewGroup container;
    ArrayList<Subject> notFilteredData;
    ArrayList<Subject> filteredData;

    public AttendanceStatusFragment() {
        // Required empty public constructor
    }

    public AttendanceStatusFragment(String whichView){
        this.whichView = whichView;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_attendance_status, container, false);
        ButterKnife.bind(this,v);
        this.inflater = inflater;
        this.container = container;
        if(whichView.equals("PENDING")){

        } else if(whichView.equals("ACCEPTED")){

        } else if(whichView.equals("PENDING")){

        }
        // Inflate the layout for this fragment
        return v;
    }

}
