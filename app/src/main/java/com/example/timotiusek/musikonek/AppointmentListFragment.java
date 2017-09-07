package com.example.timotiusek.musikonek;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.timotiusek.musikonek.CustomClass.TeacherAppointment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AppointmentListFragment extends Fragment {

    @BindView(R.id.tab_layout__student_detail_fra)
    TabLayout tabLayout;
    @BindView(R.id.view_pager__student_detail_fra)
    ViewPager viewPager;
    ActiveCourseActivity ma;

    public TeacherAppointment getTeacherAppointment() {
        return ta;
    }

    public void setTeacherAppointment(TeacherAppointment ta) {
        this.ta = ta;
    }

    private TeacherAppointment ta;

    public AppointmentListFragment() {
        // Required empty public constructor
    }


    public static AppointmentListFragment newInstance(TeacherAppointment ta){

        AppointmentListFragment sdf = new AppointmentListFragment();
        sdf.setTeacherAppointment(ta);

        return sdf;


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_appointment_list_fragment, container, false);
        ButterKnife.bind(this, view);

        Log.d("ASDF",ta.getCourseName() + " id is " + ta.getCourseID());

        ma = (ActiveCourseActivity) getActivity();
        ma.getSupportActionBar().setTitle(ta.getStudentName());
//        ma.clearCheckedItems();
        /**
         * todo : tentuin set checknya
         */


        viewPager.setAdapter(new AppointmentListFragment.MyAdapter(getChildFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);

        Log.d("ASDF","course name here is " + ta.getCourseName());

        viewPager.setCurrentItem(0);
        // Inflate the layout for this fragment
        return view;
    }

    class MyAdapter extends android.support.v4.app.FragmentStatePagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position)
        {
            switch (position){
                case 0 : return ShowAttendanceFragment.newInstance(ta);
                case 1 : return ShortTestimonialFragment.newInstance(ta);
                case 2 :  return StudentDetailScheduleFragment.newInstance(ta);
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0 : return "Attendance";
                case 1 : return "Testimonial";
                case 2 : return "Schedule";
            }
            return null;
        }

    }


}
