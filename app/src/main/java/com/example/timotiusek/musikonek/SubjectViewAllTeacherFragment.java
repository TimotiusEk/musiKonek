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
public class SubjectViewAllTeacherFragment extends Fragment {
    @BindView(R.id.all_teacher_list_view)
    ListView allTeacherListView;

    String name;
    public SubjectViewAllTeacherFragment() {
        // Required empty public constructor
    }

    public SubjectViewAllTeacherFragment(String name) {
        this.name = name;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle(name);
        View view = inflater.inflate(R.layout.fragment_subject_view_all_teacher, container, false);
        ButterKnife.bind(this, view);
        ArrayList<Teacher> teacherArrayList = new ArrayList<>();

        if(name.equals("GUITAR")){
            teacherArrayList.add(new Teacher(R.drawable.avatar,"Budi", "Maret 2010", 25000));
            teacherArrayList.add(new Teacher(R.drawable.avatar,"Budi", "Maret 2010", 25000000));
            teacherArrayList.add(new Teacher(R.drawable.avatar,"Budi", "Maret 2010", 500000));
            teacherArrayList.add(new Teacher(R.drawable.avatar,"Budi", "Maret 2010", 25000));
            teacherArrayList.add(new Teacher(R.drawable.avatar,"Budi", "Maret 2010", 25000000));
            teacherArrayList.add(new Teacher(R.drawable.avatar,"Budi", "Maret 2010", 500000));
        }
        SubjectViewAllTeacherAdapter subjectViewAllTeacherAdapter = new SubjectViewAllTeacherAdapter(teacherArrayList, getActivity());

        allTeacherListView.setAdapter(subjectViewAllTeacherAdapter);

        // Inflate the layout for this fragment
        return view ;
    }

}
