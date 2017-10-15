package com.example.timotiusek.musikonek;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
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

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.example.timotiusek.musikonek.CustomClass.Schedule;
import com.example.timotiusek.musikonek.CustomClass.Subject;
import com.example.timotiusek.musikonek.CustomClass.SubjectAdapter;
import com.example.timotiusek.musikonek.Helper.Connector;
import com.example.timotiusek.musikonek.Helper.DateFormatter;
import com.example.timotiusek.musikonek.Helper.TextFormater;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SubjectViewFragment extends Fragment {
    @Nullable
    @BindView(R.id.subject_grid_view__subject_view_all_fra)
    GridView subjectGridView;

    @Nullable
    @BindView(R.id.existing_subject_lv__subject_view_existing_fra)
    ListView existingSubjectListView;

    @Nullable
    @BindView(R.id.graduated_subject_lv__subject_view_graduated_fra)
    ListView graduatedSubjectListView;

    String whichView;
    View view = null;
    LayoutInflater inflater;
    ViewGroup container;
    MainActivity ma;
    BrowseFragment bf;
    SubjectAdapter subjectAdapter;
    SubjectAdapter graduatedSubjectAdapter;
    ArrayList<Subject> graduatedSubjects;
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
        subjectAdapter = new SubjectAdapter(subjects, getActivity(), "ALL", getActivity());
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
//        subjects.add(new Subject(R.drawable.guitar, "GUITAR", "Bu Marilyn"));
//        subjects.add(new Subject(R.drawable.bass, "BASS", "Pak Monroe"));
        subjectAdapter = new SubjectAdapter(subjects, getActivity(), "EXISTING", getActivity());
        existingSubjectListView.setAdapter(subjectAdapter);
        registerForContextMenu(existingSubjectListView);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("profile", Context.MODE_PRIVATE);

        String token ="";

        if(!sharedPreferences.getString("token","").equals("")) {
            token = sharedPreferences.getString("token","");
        }

        RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(getContext().getCacheDir(), 1024 * 1024); // 1MB cap
        final Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();
        String url = Connector.getURL() +"/api/v1/course/getOngoingCourseByStudentId?token="+token;
        final DelayedProgressDialog dialog = new DelayedProgressDialog();
        dialog.show(getChildFragmentManager(),"loading2");
        //dialog.setCancelable(false);
        Log.d("ASDF",url);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                            dialog.dismiss();

                        try {
                            JSONObject res = new JSONObject(response);

                            JSONArray arr = res.getJSONArray("data");

                            for(int i=0;i<arr.length();i++){
                                JSONObject jo =  arr.getJSONObject(i);

                                String instrument = jo.getString("name");
                                String teacherName = jo.getString("teacher_name");
                                String id = jo.getString("course_id");
//                                String date = jo.getString("date_created");

                                subjects.add(new Subject(R.drawable.bass, instrument,teacherName, id));

                                subjectAdapter.notifyDataSetChanged();


                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        NetworkResponse networkResponse = error.networkResponse;

                        if(networkResponse == null){

                            Toast.makeText(getContext(), "Connection Error",Toast.LENGTH_SHORT).show();

                        }else{
                            int a = networkResponse.statusCode;
                            if(networkResponse.statusCode == 403){
                                Toast.makeText(getContext(), "TOKEN INVALID, PLEASE RE LOG",Toast.LENGTH_SHORT).show();

                            }

                            if(networkResponse.statusCode == 500){
                                Toast.makeText(getContext(), "INVALID CREDENTIALS",Toast.LENGTH_SHORT).show();
                            }

                            if(networkResponse.statusCode != 401){

                                //Log.d("ASDF","SHIT");

                            }

                        }

                        getActivity().finish();

                    }
                }){

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                int mStatusCode = response.statusCode;
                return super.parseNetworkResponse(response);
            }

        };

        requestQueue.add(stringRequest);



    }

    private void populateGraduatedSubjectData() {

        graduatedSubjects = new ArrayList<>();
//

        graduatedSubjectAdapter = new SubjectAdapter(graduatedSubjects, getActivity(), "GRADUATED", getActivity());
        existingSubjectListView.setAdapter(graduatedSubjectAdapter);
        subjects = new ArrayList<>();
//        subjects.add(new Subject(R.drawable.avatar, 12, "17 March 2017", R.drawable.guitar, "GUITAR", "Bu Marilyn","12"));
//        subjects.add(new Subject(R.drawable.avatar, 120, "1 April 2016", R.drawable.bass, "BASS", "Pak Monroe","13"));
        subjectAdapter = new SubjectAdapter(subjects, getActivity(), "GRADUATED", getActivity());
        existingSubjectListView.setAdapter(subjectAdapter);

        existingSubjectListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getActivity(), ScheduleActivity.class));
            }
        });
        registerForContextMenu(existingSubjectListView);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("profile", Context.MODE_PRIVATE);

        String token ="";

        if(!sharedPreferences.getString("token","").equals("")) {
            token = sharedPreferences.getString("token","");
        }


        RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(getContext().getCacheDir(), 1024 * 1024); // 1MB cap
        final Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();

        final DelayedProgressDialog dialog = new DelayedProgressDialog();
        dialog.show(getActivity().getSupportFragmentManager(),"loading");
//        dialog.setCancelable(false);


        String url = Connector.getURL() +"/api/v1/course/getGraduatedCourseByStudentId?token="+token;


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.cancel();

                        try {
                            //Log.d("ASDF",response);

                            JSONObject res = new JSONObject(response);

                            JSONArray arr = res.getJSONArray("data");

                            for(int i=0;i<arr.length();i++){
                                JSONObject jo =  arr.getJSONObject(i);

                                String instrument = jo.getString("instrument");
                                String teacherName = jo.getString("teacher_name");
                                String id = jo.getString("course_id");
//                                String date = jo.getString("date_created");
//
//                                date = DateFormatter.monthYear(date);

                                graduatedSubjects.add(new Subject(R.drawable.avatar, 12, "17 Maret 2017", R.drawable.guitar, instrument, teacherName, id));


                                graduatedSubjectAdapter.notifyDataSetChanged();


                            }

//                            //Log.d("ASDF","ELEH" + res.toString());


                        } catch (JSONException e) {
                            //Log.d("ASDF","Fail");
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        NetworkResponse networkResponse = error.networkResponse;

                        if(networkResponse == null){

                            Toast.makeText(getContext(), "Connection Error",Toast.LENGTH_SHORT).show();

                        }else{
                            int a = networkResponse.statusCode;
                            if(networkResponse.statusCode == 403){
                                Toast.makeText(getContext(), "TOKEN INVALID, PLEASE RE LOG",Toast.LENGTH_SHORT).show();

                            }

                            if(networkResponse.statusCode == 500){
                                Toast.makeText(getContext(), "INVALID CREDENTIALS",Toast.LENGTH_SHORT).show();
                            }

                            if(networkResponse.statusCode != 401){

                                //Log.d("ASDF","SHIT");

                            }

                        }
                        getActivity().finish();



                    }
                }){

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                int mStatusCode = response.statusCode;
                return super.parseNetworkResponse(response);
            }

        };

        requestQueue.add(stringRequest);





    }

//    public boolean onContextItemSelected (MenuItem item) {
//
//
//        AdapterView.AdapterContextMenuInfo info= (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
//        int index = info.position;
//        switch (item.getItemId()) {
//            case R.id.look_report_menu:
//                startActivity(new Intent(getActivity(), ReportDetailActivity.class));
////                Toast.makeText(getActivity(), "Lihat Laporan " + subjects.get(index).getName(), Toast.LENGTH_LONG).show();
//                return true;
//            case R.id.look_schedule_menu:
//                startActivity(new Intent(getActivity(), ScheduleActivity.class));
////                Toast.makeText(getActivity(), "Lihat Jadwal " + subjects.get(index).getName(), Toast.LENGTH_LONG).show();
//                return true;
//        }
//        return false;
//    }


}
