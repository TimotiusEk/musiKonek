package com.example.timotiusek.musikonek;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
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
import com.example.timotiusek.musikonek.Helper.Connector;
import com.example.timotiusek.musikonek.Helper.DateFormatter;
import com.example.timotiusek.musikonek.Helper.ShaConverter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import com.example.timotiusek.musikonek.CustomClass.SubjectViewAllTeacherAdapter;
import com.example.timotiusek.musikonek.CustomClass.Teacher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SubjectViewAllTeacherActivity extends AppCompatActivity {
    @BindView(R.id.all_teacher_lv__subject_view_all_teacher_act)
    ListView allTeacherListView;

    String name;
    String token;

    SharedPreferences sharedPreferences;
    ArrayList<Teacher> teacherArrayList;
    SubjectViewAllTeacherAdapter subjectViewAllTeacherAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_view_all_teacher);
        ButterKnife.bind(this);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.toolbar__subject_view_all_teacher_act);
        Intent intent = getIntent();
        toolbar.setTitle(intent.getStringExtra("subject"));

        name = intent.getStringExtra("subject");

        sharedPreferences = getSharedPreferences("profile", Context.MODE_PRIVATE);

        if(!sharedPreferences.getString("token","").equals("")) {
            token = sharedPreferences.getString("token","");
        }

        allProgramCall();

        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        teacherArrayList = new ArrayList<>();

//        if(name.equals("GUITAR")){
//            teacherArrayList.add(new Teacher(R.drawable.avatar,"Budi", "Maret 2010", 25000));
//            teacherArrayList.add(new Teacher(R.drawable.avatar,"Budi", "Maret 2010", 25000000));
//            teacherArrayList.add(new Teacher(R.drawable.avatar,"Budi", "Maret 2010", 500000));
//            teacherArrayList.add(new Teacher(R.drawable.avatar,"Budi", "Maret 2010", 25000));
//            teacherArrayList.add(new Teacher(R.drawable.avatar,"Budi", "Maret 2010", 25000000));
//            teacherArrayList.add(new Teacher(R.drawable.avatar,"Budi", "Maret 2010", 500000));
//        }
        subjectViewAllTeacherAdapter = new SubjectViewAllTeacherAdapter(teacherArrayList, this);

        allTeacherListView.setAdapter(subjectViewAllTeacherAdapter);

        allTeacherListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SubjectViewAllTeacherActivity.this, CourseListSpecificTeacherActivity.class);

                Bundle extras = new Bundle();
                extras.putString("name",teacherArrayList.get(position).getName());
                extras.putString("teacher_id",teacherArrayList.get(position).getId());

                intent.putExtras(extras);

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

    private void allProgramCall(){
        RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
        final Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();
        String url = Connector.getURL() +"/api/v1/teacher/list?skill="+name+"&token="+token;

        final DelayedProgressDialog dialog = new DelayedProgressDialog();
        dialog.show(getSupportFragmentManager(),"loading");


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(!dialog.fragmentManagerIsNull()){
                            dialog.dismiss();
                        }
                        try {

                            //Log.d("ASDF",response);

                            JSONObject res = new JSONObject(response);
//                            String name = String.valueOf(res.get("name"));

                            JSONArray arr = res.getJSONArray("data");

                            for(int i=0;i<arr.length();i++){
                                JSONObject jo =  arr.getJSONObject(i);

                                String username = jo.getString("fullname");
                                String rate = jo.getString("rate");
                                String date = jo.getString("date_created");
                                String id = jo.getString("skill_teacher_id");

                                int intrate = Integer.valueOf(rate);


                                teacherArrayList.add(new Teacher(R.drawable.avatar,username, DateFormatter.monthYear(date), intrate, id));
                                subjectViewAllTeacherAdapter.notifyDataSetChanged();


                            }

                            //Log.d("ASDF",res.toString());


                        } catch (JSONException e) {
                            //Log.d("ASDF","ELEH2");
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        NetworkResponse networkResponse = error.networkResponse;

                        if(networkResponse == null){

                            Toast.makeText(SubjectViewAllTeacherActivity.this, "Connection Error",Toast.LENGTH_SHORT).show();

                        }else{
                            int a = networkResponse.statusCode;
                            if(networkResponse.statusCode == 403){
                                Toast.makeText(SubjectViewAllTeacherActivity.this, "TOKEN INVALID, PLEASE RE LOG",Toast.LENGTH_SHORT).show();

                            }

                            if(networkResponse.statusCode == 500){
                                Toast.makeText(SubjectViewAllTeacherActivity.this, "INVALID CREDENTIALS",Toast.LENGTH_SHORT).show();
                            }

                            if(networkResponse.statusCode != 401){

                                //Log.d("ASDF","SHIT");

                            }

                        }

                        finish();



                    }
                }){

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                int mStatusCode = response.statusCode;
                return super.parseNetworkResponse(response);
            }

        };

//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(stringRequest);

        requestQueue.add(stringRequest);


    }
}
