package com.example.timotiusek.musikonek;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class RejectAttendanceVerificationActivity extends AppCompatActivity {

    String appointmentID;

    Bundle params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reject_attendance_verification);
        getSupportActionBar().setTitle("Alasan Menolak Verifikasi");


        params = getIntent().getExtras();

        appointmentID = params.getString("appointment_id");

        TextView appointmentView = (TextView) findViewById(R.id.which_meeting__reject_attendance_verification_act);
        appointmentView.setText(params.getString("appointment"));

        Button btn = (Button) findViewById(R.id.save_btn__reject_attendance_verification_act);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                studentReject();
            }
        });


    }

    public void studentReject(){



        RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
        final Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();
        String url = Connector.getURL() +"/api/v1/appointment/rejectAttendanceStudent";
        //Log.d("ASDF",url);

        final DelayedProgressDialog dialog = new DelayedProgressDialog();
        dialog.show(getSupportFragmentManager(),"loading");
        dialog.setCancelable(false);


        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Log.d("ASDF","SUCCESS");
                        setResult(RESULT_OK);
                        RejectAttendanceVerificationActivity.this.finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.cancel();
                        NetworkResponse networkResponse = error.networkResponse;

                        if(networkResponse == null){

                            Toast.makeText(RejectAttendanceVerificationActivity.this, "Connection Error",Toast.LENGTH_SHORT).show();

                        }else{
                            int a = networkResponse.statusCode;
                            if(networkResponse.statusCode == 401){
                            }

                            if(networkResponse.statusCode == 500){
                                Toast.makeText(RejectAttendanceVerificationActivity.this, "ERROR",Toast.LENGTH_SHORT).show();
                            }

                            if(networkResponse.statusCode != 401){


                            }

                        }



                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> reqBody = new HashMap<String, String>();

                SharedPreferences sharedPreferences = getSharedPreferences("profile", Context.MODE_PRIVATE);

                String token ="";

                if(!sharedPreferences.getString("token","").equals("")) {
                    token = sharedPreferences.getString("token","");
                }

                EditText rejectReasonEdit = (EditText) findViewById(R.id.rejection_reason__reject_attendance_verification_act);

//                EditText usernameText = (EditText) findViewById(R.id.input_username_sign_up);
//                EditText fullnameText  = (EditText)  findViewById(R.id.input_fullname_signup);

                String teacherRemark = rejectReasonEdit.getText().toString();


//                String username = usernameText.getText().toString();
//                String fullname = fullnameText.getText().toString();

                reqBody.put("teacher_remark", teacherRemark);
                reqBody.put("token",token);
                reqBody.put("appointment_id",appointmentID);




                return checkParams(reqBody);
            }
            private Map<String, String> checkParams(Map<String, String> map){
                Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<String, String> pairs = (Map.Entry<String, String>)it.next();
                    if(pairs.getValue()==null){
                        map.put(pairs.getKey(), "");
                    }
                }
                return map;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                int mStatusCode = response.statusCode;
                return super.parseNetworkResponse(response);
            }

        };

//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(stringRequest);

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(stringRequest);



    }

}
