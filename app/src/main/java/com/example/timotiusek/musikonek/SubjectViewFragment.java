package com.example.timotiusek.musikonek;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SubjectViewFragment extends Fragment {
    @Nullable
    @BindView(R.id.subject_grid_view)
    GridView subjectGridView;

    @Nullable
    @BindView(R.id.existing_subject_list_view)
    ListView existingSubjectListView;

    @Nullable
    @BindView(R.id.graduated_subject_list_view)
    ListView graduatedSubjectListView;

    String whichView;
    View view = null;
    LayoutInflater inflater;
    ViewGroup container;
    MainActivity ma;
    BrowseFragment bf;
    SubjectAdapter subjectAdapter;
    ArrayList<Subject> subjects;

    public SubjectViewFragment() {
    }

    public SubjectViewFragment(String whichView) {
        this.whichView = whichView;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.inflater = inflater;
        this.container = container;
        if (whichView.equals("ALL")) {
            setLayout(R.layout.fragment_subject_view_all);
            populateAllSubjectData();
        } else if (whichView.equals("EXISTING") || whichView.equals("GRADUATED")) {
            /**
             * inflate the same layout, because they have same template which is a list view
             * it's different between 'ALL' because 'ALL' uses gridview
             */

            setLayout(R.layout.fragment_subject_view_existing);
            if (whichView.equals("EXISTING"))
                populateExistingSubjectData();
            else if (whichView.equals("GRADUATED"))
                populateGraduatedSubjectData();

        }


        // Inflate the layout for this fragment
        return view;
    }

    //    @Override
//    public boolean onCreateOptionsMenu (Menu menu) { // Inflate the menu; this adds items to the action bar if it is present.
//        getActivity().getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) { // Inflate the menu; this adds items to the action bar if it is present.
        if(whichView.equals("EXISTING")) {
            getActivity().getMenuInflater().inflate(R.menu.browse_on_item_click_menu, menu);
        } else if(whichView.equals("GRADUATED")){
            getActivity().getMenuInflater().inflate(R.menu.browse_graduated_on_click_menu, menu);
        }
    }


    public void setLayout(int layout) {
        view = inflater.inflate(layout, container, false);
        ButterKnife.bind(this, view);
    }

    public void populateAllSubjectData() {
        subjects = new ArrayList<>();
        subjects.add(new Subject(R.drawable.guitar, "GUITAR"));
        subjects.add(new Subject(R.drawable.bass, "BASS"));
        subjects.add(new Subject(R.drawable.drum, "DRUM"));
        subjects.add(new Subject(R.drawable.keyboard, "KEYBOARD"));
        subjects.add(new Subject(R.drawable.piano, "PIANO"));
        subjects.add(new Subject(R.drawable.violin, "VIOLIN"));
        subjects.add(new Subject(R.drawable.contra_bass, "CONTRA BASS"));
        subjects.add(new Subject(R.drawable.saxophone, "SAXOPHONE"));
        subjects.add(new Subject(R.drawable.vocal, "VOCAL"));
        subjects.add(new Subject(R.drawable.percussion, "PERCUSSION"));
        subjects.add(new Subject(R.drawable.flute, "FLUTE"));
        subjects.add(new Subject(R.drawable.trumpet, "TRUMPET"));
        ma = (MainActivity) getActivity();
        bf = new BrowseFragment();
        subjectAdapter = new SubjectAdapter(subjects, getActivity(), "ALL");
        subjectGridView.setAdapter(subjectAdapter);
        subjectGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), SubjectViewAllTeacherActivity.class);
                intent.putExtra("subject", subjects.get(position).getName());
                startActivity(intent);

            }
        });
    }

    public void populateExistingSubjectData() {
        subjects = new ArrayList<>();
        subjects.add(new Subject(R.drawable.guitar, "GUITAR", "Bu Marilyn"));
        subjects.add(new Subject(R.drawable.bass, "BASS", "Pak Monroe"));
        subjectAdapter = new SubjectAdapter(subjects, getActivity(), "EXISTING");
        existingSubjectListView.setAdapter(subjectAdapter);
        registerForContextMenu(existingSubjectListView);
    }

    private void populateGraduatedSubjectData() {
        subjects = new ArrayList<>();
        subjects.add(new Subject(R.drawable.avatar, 12, "17 March 2017", R.drawable.guitar, "GUITAR", "Bu Marilyn"));
        subjects.add(new Subject(R.drawable.avatar, 120, "1 April 2016", R.drawable.bass, "BASS", "Pak Monroe"));
        subjectAdapter = new SubjectAdapter(subjects, getActivity(), "GRADUATED");
        existingSubjectListView.setAdapter(subjectAdapter);
        registerForContextMenu(existingSubjectListView);
    }

    public boolean onContextItemSelected (MenuItem item) {


        AdapterView.AdapterContextMenuInfo info= (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int index = info.position;
        switch (item.getItemId()) {
            case R.id.look_report_menu:
                startActivity(new Intent(getActivity(), ReportDetailActivity.class));
//                Toast.makeText(getActivity(), "Lihat Laporan " + subjects.get(index).getName(), Toast.LENGTH_LONG).show();
                return true;
            case R.id.look_schedule_menu:
                startActivity(new Intent(getActivity(), ScheduleActivity.class));
//                Toast.makeText(getActivity(), "Lihat Jadwal " + subjects.get(index).getName(), Toast.LENGTH_LONG).show();
                return true;
        }
        return false;
    }


}
