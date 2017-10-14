package com.example.timotiusek.musikonek;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.timotiusek.musikonek.CustomClass.TeacherAppointment;

public class ActiveCourseActivity extends AppCompatActivity {


    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_active);

        getSupportActionBar().setTitle("Course Detail");
//        getActionBar().setTitle("Course Detail");

        //Log.d("ASDF","COURSE DETAIL ACTIVITY");

        Intent incoming = getIntent();
        Bundle params = incoming.getExtras();

        TeacherAppointment ta = new TeacherAppointment(R.drawable.percussion, params.getString("courseName"), params.getString("coursePackage"),params.getString("studentName"),"",params.getString("courseID"));

        changeFragment(AppointmentListFragment.newInstance(ta));
    }

    public void changeFragment(Fragment newFragment) {
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.fragment_student_list, newFragment);
        mFragmentTransaction.commit();
    }
}
