package com.example.timotiusek.musikonek;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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
import com.example.timotiusek.musikonek.CustomClass.TeacherAppointment;
import com.example.timotiusek.musikonek.Helper.Connector;
import com.example.timotiusek.musikonek.Helper.TextFormater;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class StudentDetailScheduleFragment extends Fragment {
    @BindView(R.id.schedules_lv__student_detail_schedule_fra)
    ListView scheduleLv;
    ArrayList<Schedule> schedules;
    ScheduleAdapter scheduleAdapter;

    public void setTeacherAppointment(TeacherAppointment ta) {
        this.ta = ta;
    }

    TeacherAppointment ta;


    public StudentDetailScheduleFragment() {
        // Required empty public constructor
    }

    public static StudentDetailScheduleFragment newInstance(TeacherAppointment ta) {

        StudentDetailScheduleFragment fragment = new StudentDetailScheduleFragment();

        fragment.setTeacherAppointment(ta);

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_student_detail_schedule, container, false);
        ButterKnife.bind(this, v);

        schedules = new ArrayList<>();
//        schedules.add(new Schedule("Pertemuan 1", "12 Desember 1931, 12:12 WIB"));
//        schedules.add(new Schedule("Pertemuan 2", "12 Desember 1911, 12:12 WIB"));
//        schedules.add(new Schedule("Pertemuan 3", "12 Desember 1968, 12:12 WIB"));
//        schedules.add(new Schedule("Pertemuan 4", "12 Desember 1980, 12:12 WIB"));
//        schedules.add(new Schedule("Pertemuan 5", "12 Desember 1994, 12:12 WIB"));
//        schedules.add(new Schedule("Pertemuan 6", "12 Desember 2010, 12:12 WIB"));

        scheduleAdapter = new ScheduleAdapter(schedules, getActivity());
        scheduleLv.setAdapter(scheduleAdapter);
        // Inflate the layout for this fragment
        return v;
    }

    @Override
    public void onResume() {
        populateAppointments();
        super.onResume();
    }

    private void populateAppointments(){

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
        String url = Connector.getURL() +"/api/v1/appointment/getAppointments?token="+token+"&course_id="+ta.getCourseID();
        Log.d("ASDF",url);

        final DelayedProgressDialog dialog = new DelayedProgressDialog();
        dialog.show(getChildFragmentManager(),"loading");

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        dialog.cancel();
                        try {
                            JSONObject res = new JSONObject(response);
                            Log.d("ASDF",res.toString());
                            JSONArray arr = res.getJSONArray("data");


                            schedules.clear();
                            for(int i=0;i<arr.length();i++){



                                JSONObject jo =  arr.getJSONObject(i);
                                Log.d("ASDF","added "+arr.length());
                                schedules.add(new Schedule("Pertemuan " + String.valueOf(i+1), TextFormater.formatTime(jo.getString("appointment_time"))));
//                                schedules.add(new Schedule(R.drawable.avatar, student.getCourseName(), "Pertemuan "+(i+1), TextFormater.formatTime(jo.getString("appointment_time")), jo.getString("appointment_id"), jo.getBoolean("attendance_teacher")));

                            }

                            scheduleAdapter.notifyDataSetChanged();


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
                            if(getContext()!=null){
                                Toast.makeText(getContext(), "Connection Error",Toast.LENGTH_SHORT).show();
                            }

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

}
