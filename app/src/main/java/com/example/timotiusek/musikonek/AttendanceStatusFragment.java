package com.example.timotiusek.musikonek;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.timotiusek.musikonek.CustomClass.Subject;

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
        notFilteredData = new ArrayList<>();
        filteredData = new ArrayList<>();
        notFilteredData.add(new Subject(R.drawable.avatar, "Kursus Piano Pemula", 12, R.drawable.piano, "21:01 WIB", "Bu Vonny", "PENDING"));
        notFilteredData.add(new Subject(R.drawable.avatar, "Kursus Bass Pemula", 21, R.drawable.bass, "06:66 WIB", "Pak Epen", "ACCEPTED"));
        notFilteredData.add(new Subject(R.drawable.avatar, "Kursus Trumpet Intermediate", 30, R.drawable.trumpet, "11:11 WIB", "Bu Aya", "REJECTED"));
        this.inflater = inflater;
        this.container = container;

        for(Subject subject : notFilteredData){
            if(subject.getStatus().equals(whichView)){
                filteredData.add(subject);
            }
        }

        AttendanceStatusAdapter statusAdapter = new AttendanceStatusAdapter(filteredData, getActivity());
        listView.setAdapter(statusAdapter);

        // Inflate the layout for this fragment
        return v;
    }

}
