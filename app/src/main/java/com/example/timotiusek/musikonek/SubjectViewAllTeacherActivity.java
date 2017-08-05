package com.example.timotiusek.musikonek;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.timotiusek.musikonek.CustomClass.SubjectViewAllTeacherAdapter;
import com.example.timotiusek.musikonek.CustomClass.Teacher;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SubjectViewAllTeacherActivity extends AppCompatActivity {
    @BindView(R.id.all_teacher_lv__subject_view_all_teacher_act)
    ListView allTeacherListView;

    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_view_all_teacher);
        ButterKnife.bind(this);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.toolbar__subject_view_all_teacher_act);
        Intent intent = getIntent();
        toolbar.setTitle(intent.getStringExtra("subject"));
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        ArrayList<Teacher> teacherArrayList = new ArrayList<>();

//        if(name.equals("GUITAR")){
            teacherArrayList.add(new Teacher(R.drawable.avatar,"Budi", "Maret 2010", 25000));
            teacherArrayList.add(new Teacher(R.drawable.avatar,"Budi", "Maret 2010", 25000000));
            teacherArrayList.add(new Teacher(R.drawable.avatar,"Budi", "Maret 2010", 500000));
            teacherArrayList.add(new Teacher(R.drawable.avatar,"Budi", "Maret 2010", 25000));
            teacherArrayList.add(new Teacher(R.drawable.avatar,"Budi", "Maret 2010", 25000000));
            teacherArrayList.add(new Teacher(R.drawable.avatar,"Budi", "Maret 2010", 500000));
//        }
        SubjectViewAllTeacherAdapter subjectViewAllTeacherAdapter = new SubjectViewAllTeacherAdapter(teacherArrayList, this);

        allTeacherListView.setAdapter(subjectViewAllTeacherAdapter);

        allTeacherListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SubjectViewAllTeacherActivity.this, CourseDetailActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;
        }
        return (super.onOptionsItemSelected(menuItem));
    }
}
