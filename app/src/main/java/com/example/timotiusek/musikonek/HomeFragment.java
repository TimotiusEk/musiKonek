package com.example.timotiusek.musikonek;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    MainActivity ma;
    public HomeFragment() {
        // Required empty public constructor
    }

    @OnClick(R.id.see_recommendation_btn__home_fra)
    void reccomendationClick(){
        goToBrowse();
        Log.d("ASDF","called");
    }

    @OnClick(R.id.search_btn__home_fra)
    void searchButtonClick(){
        goToBrowse();
        Log.d("ASDF","called");
    }

    private void goToBrowse(){
        ma.changeFragment(new BrowseFragment());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ma = (MainActivity) getActivity();
        ma.setTitle("Beranda");
        ma.setChecked(R.id.menu_home);

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
