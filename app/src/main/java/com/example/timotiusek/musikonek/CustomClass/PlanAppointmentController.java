package com.example.timotiusek.musikonek.CustomClass;

import android.content.Context;
import android.util.Log;
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
import com.example.timotiusek.musikonek.PlanAppointmentActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by USER on 13/09/2017.
 */

public class PlanAppointmentController {

    /**
     * Use these variables to detect wether a teacher is unavailable, available or already assigned to an appointment
     * Used to avoid a hard coded int to make a comparison
     */
    public static final int UNAVAILABLE = 0;
    public static final int AVAILABLE = 1;
    public static final int OCCUPIED = 2;
    /**
     * This field can be used outside the class to generate the JSONObject with a correct key
     * Call MagicBox.days[x]
     */
    public static final String[] days =
            new String[] {"Monday", "Tuesday", "Wednessday", "Thursday", "Friday", "Saturday", "Sunday"};

    private void getSchedule(final PlanAppointmentActivity activity, final Context context) {
        RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(context.getCacheDir(), 1024 * 1024); // 1MiB cap
        final Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();

        String token = context.getSharedPreferences("profile", Context.MODE_PRIVATE).getString("token", "");
        String url = Connector.getURL() +"/api/v1/schedule/getTeacherLatestSchedule?token=" + token +
                     "&id=" + activity.getIntent().getExtras().getString("teacher_id") +
                     "&is_from_student=true";;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject responseJSON = new JSONObject(response);
                            JSONArray data = responseJSON.optJSONArray("data");
                            if(data.length() == 0) {
                                activity.onDataReady(new int[1]);
                                return;
                            } else {
                                responseJSON = (JSONObject) data.get(0);
                            }
                            int[] schedule = new int[7];
                            for(int i = 0; i < days.length; i++) {
                                if(responseJSON.has(days[i].toLowerCase())) {
                                    schedule[i] = responseJSON.getInt(days[i].toLowerCase());
                                }
                            }
                            activity.onDataReady(schedule);
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
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                return super.parseNetworkResponse(response);
            }
        };
        requestQueue.add(stringRequest);
    }

    public void getDataAsync(final PlanAppointmentActivity activity) {
        getSchedule(activity, activity.getBaseContext());
    }
}
