package com.example.timotiusek.musikonek;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.example.timotiusek.musikonek.CustomClass.Course;
import com.example.timotiusek.musikonek.CustomClass.CourseAdapter;
import com.example.timotiusek.musikonek.CustomClass.Teacher;
import com.example.timotiusek.musikonek.Helper.Connector;
import com.example.timotiusek.musikonek.Helper.DateFormatter;
import com.example.timotiusek.musikonek.Helper.TextFormater;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CourseListSpecificTeacherActivity extends AppCompatActivity {

    @BindView(R.id.course_list_lv__course_list_specific_teacher_act)
    ListView courseListLv;

    ArrayList<Course> courses;
    CourseAdapter courseAdapter;

    String id = "";
    String teacherName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list_specific_teacher);
        ButterKnife.bind(this);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Kursus Tersedia");
        }

        Bundle params = getIntent().getExtras();
        id = params.getString("teacher_id");
        teacherName = params.getString("name");


        courses = new ArrayList<>();
//        courses.add(new Course(R.drawable.avatar, teacherName, "Kursus Piano Basic", "Kursus ini cocok bagi mereka yang sg ingin belajar piano tanpa memiliki konsep dasar tentang musik. Kursus dibagi dalam enam pertemuan singkat.", 6, "Rp 45.000"));
//        courses.add(new Course(R.drawable.avatar, teacherName, "Kursus Piano Basic", "Kursus ini cocok bagi mereka yang sedang ingin belajar piano tanpa memiliki konsep dasar tentang musik. Kursus dibagi dalam enam pertemuan singkat.", 6, "Rp 45.000"));
//        courses.add(new Course(R.drawable.avatar, teacherName, "Kursus Piano Basic", "Kursus ini cocok bagi mereka yang sedang ingin belajar piano tanpa memiliki konsep dasar tentang musik. Kursus dibagi dalam enam pertemuan singkat.", 6, "Rp 45.000"));
//        courses.add(new Course(R.drawable.avatar, teacherName, "Kursus Piano Basic", "Kursus ini cocok bagi mereka yang sedang ingin belajar piano tanpa memiliki konsep dasar tentang musik. Kursus dibagi dalam enam pertemuan singkat.", 6, "Rp 45.000"));

        courseAdapter = new CourseAdapter(courses, this);
        courseListLv.setAdapter(courseAdapter);
        courseListLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Course courseDataToBeSent = (Course) courseAdapter.getItem(position);
                /**
                 * set bundle and send data
                 */
                Intent intent = new Intent(CourseListSpecificTeacherActivity.this, CourseDetailActivity.class);

                Bundle extras = new Bundle();
                extras.putString("id",courses.get(position).getId());
                extras.putString("course_name", courses.get(position).getCourseName());
                extras.putString("program_id",courses.get(position).getId());
                extras.putString("teacher_name",courses.get(position).getTeacherName());
                extras.putInt("appointments", courses.get(position).getCourseDuration());
                extras.putString("description", courses.get(position).getCourseDesc());
                extras.putString("price", courses.get(position).getCoursePrice());
                extras.putInt("image", courses.get(position).getTeacherImg());
                extras.putInt("duration_minute",courses.get(position).getDurationMinute());
                extras.putString("teacher_id",courses.get(position).getTeacherID());
                extras.putString("skill_id",courses.get(position).getSkillID());

//                extras.putString("teacher_id",CourseListSpecificTeacherActivity.this.getIntent().getExtras().getString("teacher_id"));
//                Log.d("DEBUG", "Angkanya: " + CourseListSpecificTeacherActivity.this.getIntent().getExtras().getString("teacher_id"));
                intent.putExtras(extras);

                startActivity(intent);

            }
        });

        populateData();



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

    void populateData(){

        RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
        final Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();

        String token="";
        SharedPreferences sharedPreferences = getSharedPreferences("profile", Context.MODE_PRIVATE);

        if(!sharedPreferences.getString("token","").equals("")) {
            token = sharedPreferences.getString("token","");
        }

        String url = Connector.getURL() +"/api/v1/program/list?skill_teacher_id="+id+"&token="+token;


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            courses.clear();

                            Log.d("ASDF",response);

                            JSONObject res = new JSONObject(response);
//                            String name = String.valueOf(res.get("name"));

                            JSONArray arr = res.getJSONArray("data");

                            for(int i=0;i<arr.length();i++){
                                JSONObject jo =  arr.getJSONObject(i);

                                String name = jo.getString("name");
                                String price = "Rp. " + TextFormater.priceFormatter(jo.getDouble("price"));
                                String description = jo.getString("description");
                                int appointments = jo.getInt("appointments");

                                String programId = jo.getString("program_id");

                                String teacherID = jo.getString("teacher_id");
                                int duration = jo.getInt("duration_minute");
                                String skillID = jo.getString("skill_id");

                                courses.add(new Course(R.drawable.avatar, teacherName, name, description, appointments, price, programId, duration, teacherID, skillID));

                                courseAdapter.notifyDataSetChanged();

                                Log.d("ASDF","yes data + length "+ courses.size());

                            }

                            Log.d("ASDF",res.toString());


                        } catch (JSONException e) {
                            Log.d("ASDF","ELEH");
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        NetworkResponse networkResponse = error.networkResponse;

                        if(networkResponse == null){

                            Toast.makeText(CourseListSpecificTeacherActivity.this, "Connection Error",Toast.LENGTH_SHORT).show();

                        }else{
                            int a = networkResponse.statusCode;
                            if(networkResponse.statusCode == 403){
                                Toast.makeText(CourseListSpecificTeacherActivity.this, "TOKEN INVALID, PLEASE RE LOG",Toast.LENGTH_SHORT).show();

                            }

                            if(networkResponse.statusCode == 500){
                                Toast.makeText(CourseListSpecificTeacherActivity.this, "INVALID CREDENTIALS",Toast.LENGTH_SHORT).show();
                            }

                            if(networkResponse.statusCode != 401){

                                Log.d("ASDF","SHIT");

                            }

                        }



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
