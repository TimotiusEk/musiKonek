package com.example.timotiusek.musikonek.CustomClass;

import android.content.Context;
import android.util.Log;
import android.widget.EditText;
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
import com.example.timotiusek.musikonek.Helper.ShaConverter;
import com.example.timotiusek.musikonek.PlanAppointmentActivity;
import com.example.timotiusek.musikonek.R;
import com.example.timotiusek.musikonek.SetScheduleActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by USER on 21/09/2017.
 */

public class SetScheduleController {

    private void postSchedule(final SetScheduleActivity activity, final Context context) {
        RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(context.getCacheDir(), 1024 * 1024); // 1MiB cap
        final Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();

        String url = Connector.getURL() +"/api/v1/course/studentRequestCourse";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject status = new JSONObject(response);
                            if(status.getBoolean("success")) {
                                activity.onDataSubmitted(1);
                            } else {
                                activity.onDataSubmitted(-1);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(error.networkResponse == null) {
                            Toast.makeText(context, "Connection Error",Toast.LENGTH_SHORT).show();
                        } else if(error.networkResponse.statusCode == 403) {
                            Toast.makeText(context, "TOKEN INVALID, PLEASE RE-LOG",Toast.LENGTH_SHORT).show();
                        } else if(error.networkResponse.statusCode == 500) {
                            Toast.makeText(context, "INVALID CREDENTIALS",Toast.LENGTH_SHORT).show();
                        } else if(error.networkResponse.statusCode != 401) {
                            Log.d("DEBUG","Error 401");
                        } else {
                            Toast.makeText(context, "Unknown error: " + error.networkResponse.statusCode, Toast.LENGTH_SHORT).show();
                        }
                    }
                }) {
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> reqBody = new HashMap<String, String>();
                JSONArray appointmentTime = new JSONArray();
                Date[] appointmentDates = activity.getAppointmentDates();
                for(int i = 0; i < appointmentDates.length; i++){
                    appointmentTime.put(appointmentDates[i].getYear()+"-"+appointmentDates[i].getMonth()+"-"+
                            appointmentDates[i].getDay()+" "+appointmentDates[i].getHours()+":"+appointmentDates[i].getMinutes()+ ":00");
                }

                reqBody.put("teacher_id", activity.getIntent().getExtras().getString("teacher_id"));
                reqBody.put("student_id", context.getSharedPreferences("profile", Context.MODE_PRIVATE).getString("token", ""));
                reqBody.put("appointment", activity.getIntent().getExtras().getString("appointments"));
                reqBody.put("skill_id", activity.getIntent().getExtras().getString("skill_id"));
                reqBody.put("course_duration_minute", activity.getIntent().getExtras().getString("duration_minute"));
                reqBody.put("description", activity.getIntent().getExtras().getString("description"));
                reqBody.put("appointment_time", appointmentTime.toString());

                return reqBody;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                return super.parseNetworkResponse(response);
            }
        };
        requestQueue.add(stringRequest);
    }

    public void postDataAsync(final SetScheduleActivity activity) {
        postSchedule(activity, activity.getBaseContext());
    }
}
