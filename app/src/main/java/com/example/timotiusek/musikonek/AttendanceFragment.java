package com.example.timotiusek.musikonek;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class AttendanceFragment extends Fragment {
    @BindView(R.id.tab_layout_attendance)
    TabLayout tabLayout;

    @BindView(R.id.view_pager_attendance)
    ViewPager viewPager;

    MainActivity ma;

    public AttendanceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attendance, container, false);
        ButterKnife.bind(this, view);

        ma = (MainActivity) getActivity();
        ma.setChecked(R.id.menu_attendance);

        viewPager.setAdapter(new MyAdapter(getActivity().getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
        // Inflate the layout for this fragment
        return view;
    }

    class MyAdapter extends FragmentStatePagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position)
        {
            switch (position){
                case 0 : return new AttendanceStatusFragment("PENDING");
                case 1 : return new AttendanceStatusFragment("ACCEPTED");
                case 2 :  return new AttendanceStatusFragment("REJECTED");
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
                case 0 : return "PENDING";
                case 1 : return "ACCEPTED";
                case 2 : return "REJECTED";
            }
            return null;
        }

    }

}
