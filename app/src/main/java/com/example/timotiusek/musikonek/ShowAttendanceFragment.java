package com.example.timotiusek.musikonek;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.example.timotiusek.musikonek.CustomClass.Attendance;
import com.example.timotiusek.musikonek.CustomClass.ShowAttendanceAdapter;
import com.example.timotiusek.musikonek.CustomClass.TeacherAppointment;
import com.example.timotiusek.musikonek.Helper.Connector;
import com.example.timotiusek.musikonek.Helper.TextFormater;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ShowAttendanceFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ShowAttendanceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShowAttendanceFragment extends Fragment {
    @BindView(R.id.attendances_lv__show_attendance_fra)
    ListView showAttendanceListView;
    ArrayList<Attendance> attendances;
    ShowAttendanceAdapter showAttendanceAdapter;
    ActiveCourseActivity ma;
    public ShowAttendanceFragment() {
        // Required empty public constructor
    }

    public TeacherAppointment getTeacherAppointment() {
        return ta;
    }

    public void setStudent(TeacherAppointment ta) {
        this.ta = ta;
    }

    private TeacherAppointment ta;

    public static ShowAttendanceFragment newInstance(TeacherAppointment ta){

        ShowAttendanceFragment sdf = new ShowAttendanceFragment();
        sdf.setStudent(ta);

        //Log.d("ASDF","Big boi is called");
        return sdf;


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_attendance, container, false);
        ButterKnife.bind(this,view);
        ma = (ActiveCourseActivity) getActivity();
//        ma.clearCheckedItems();

        //Log.d("ASDF", "SURE I AM CALLED");


        attendances = new ArrayList<>();
//        attendances.add(new Attendance(R.drawable.avatar, student.getCourseName(), "Pertemuan 1", student.getStudentName()));
//        attendances.add(new Attendance(R.drawable.avatar, student.getCourseName(), "Pertemuan 2", student.getStudentName()));
//        attendances.add(new Attendance(R.drawable.avatar, student.getCourseName(), "Pertemuan 3", student.getStudentName()));

        showAttendanceAdapter = new ShowAttendanceAdapter(attendances, getActivity());
        showAttendanceListView.setAdapter(showAttendanceAdapter);



        showAttendanceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Log.d("ASDF","tur or fals "+ attendances.get(position).isTeacherAttendance());
                if(attendances.get(position).isTeacherAttendance().equalsIgnoreCase("true")){
                    Intent intent = new Intent(getActivity(), ReportDetailActivity.class);

                    //Log.d("ASDF", "appointment_id is "+attendances.get(position).getAppointmentID());

                    Bundle extras = new Bundle();
                    extras.putString("appointment_id",attendances.get(position).getAppointmentID());

                    intent.putExtras(extras);

                    startActivity(intent);
                }
                //If you declined
                else if(attendances.get(position).isTeacherAttendance().equalsIgnoreCase("false")){
                    Intent intent = new Intent(getActivity(), RejectAttendanceReasonActivity.class);

                    Bundle extras = new Bundle();
                    extras.putString("appointment_id",attendances.get(position).getAppointmentID());

                    intent.putExtras(extras);

                    startActivity(intent);
                }
                else{

                    Intent intent = new Intent(getActivity(), AttendanceVerificationActivity.class);

                    //Log.d("ASDF", "appointment_id is "+attendances.get(position).getAppointmentID());

                    Bundle extras = new Bundle();
                    extras.putString("appointment_id",attendances.get(position).getAppointmentID());
                    extras.putString("name",attendances.get(position).getCourseName());
                    extras.putString("appointment", String.valueOf(position+1 ));

                    intent.putExtras(extras);

                    startActivity(intent);
                }
            }
        });
        // Inflate the layout for this fragment

        //populateAttendance();

        return view;
    }

    @Override
    public void onResume() {
        populateAttendance();
        super.onResume();
    }

    private void populateAttendance(){

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
        String url = Connector.getURL() +"/api/v1/appointment/getAppointments?done=true&token="+token+"&course_id="+ta.getCourseID();

        final DelayedProgressDialog dialog = new DelayedProgressDialog();
        dialog.show(getActivity().getSupportFragmentManager(),"loading");
        //dialog.setCancelable(false);


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.cancel();
                        try {
                            JSONObject res = new JSONObject(response);
                            //Log.d("ASDF",res.toString());
                            JSONArray arr = res.getJSONArray("data");

                            attendances.clear();

                            for(int i=0;i<arr.length();i++){



                                JSONObject jo =  arr.getJSONObject(i);

                                if(jo.get("attendance_student").toString().equals("null")){
                                    //Log.d("ASDF","its null");
                                    attendances.add(new Attendance(R.drawable.avatar, ta.getCourseName(), "Pertemuan "+(i+1), TextFormater.formatTime(jo.getString("appointment_time")), jo.getString("appointment_id"), "null"));
                                }else{
                                    //Log.d("ASDF","its asdf" + jo.get("attendance_student") );
                                    attendances.add(new Attendance(R.drawable.avatar, ta.getCourseName(), "Pertemuan "+(i+1), TextFormater.formatTime(jo.getString("appointment_time")), jo.getString("appointment_id"), String.valueOf(jo.getBoolean("attendance_student"))));
                                }


                            }

                            showAttendanceAdapter.notifyDataSetChanged();


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
