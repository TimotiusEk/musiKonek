package com.example.timotiusek.musikonek;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import com.example.timotiusek.musikonek.CustomClass.FavouriteInstrumentAdapter;
import com.example.timotiusek.musikonek.Helper.TextFormater;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditProfileActivity extends AppCompatActivity {

    @BindView(R.id.favourite_instrument_list_view)
    ListView favouriteInstrumentLV;
    FavouriteInstrumentAdapter favouriteInstrumentAdapter;

    ArrayList<String> instruments;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        ButterKnife.bind(this);
        getSupportActionBar().setTitle("Ubah Profil");
        instruments = new ArrayList<>();
        instruments.add("Gitar");
        instruments.add("Piano");

        callGetMyProfile();

        favouriteInstrumentLV.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        setListViewHeightBasedOnChildren(favouriteInstrumentLV);
        favouriteInstrumentAdapter = new FavouriteInstrumentAdapter(instruments, EditProfileActivity.this);
        favouriteInstrumentLV.setAdapter(favouriteInstrumentAdapter);
        // Inflate the layout for this fragment

        Button saveButton = (Button) findViewById(R.id.save_btn);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callUpdateProfile();
            }
        });

    }

    /**** Method for Setting the Height of the ListView dynamically.
     **** Hack to fix the issue of not showing all the items of the ListView
     **** when placed inside a ScrollView  ****/
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    public void callGetMyProfile(){

        RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
        final Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();

        String token = "";
        SharedPreferences sharedPreferences = getSharedPreferences("profile", Context.MODE_PRIVATE);

        if(!sharedPreferences.getString("token","").equals("")) {
            token = sharedPreferences.getString("token","");
        }
        String url = Connector.getURL() +"/api/v1/student/getProfileData?token="+token;

        final DelayedProgressDialog dialog = new DelayedProgressDialog();
        dialog.show(getSupportFragmentManager(),"loading");
        dialog.setCancelable(false);


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        try {
                            JSONObject res = new JSONObject(response);
//                            String name = String.valueOf(res.get("name"));

                            JSONObject data = res.getJSONObject("data");

                            String fullname = data.getString("fullname");
//                            String username = data.getString("username");

                            Spinner genderSpinner = (Spinner) findViewById(R.id.gender_spinner);

                            if(data.getString("gender").equalsIgnoreCase("male")){
                                genderSpinner.setSelection(0);
                            }else{
                                genderSpinner.setSelection(1);
                            }


                            String firstname = TextFormater.firstNameSplitter(fullname);
                            String lastname = TextFormater.lastNameSplitter(fullname);

                            JSONObject location = data.getJSONObject("address");

                            String x = location.getString("x");
                            String y  = location.getString("y");

                            EditText firstnameEdti = (EditText) findViewById(R.id.firstname_edit);
                            firstnameEdti.setText(firstname);

                            EditText lastNameEdit = (EditText) findViewById(R.id.lastname_edit);
                            lastNameEdit.setText(lastname);

                            EditText addressEdit = (EditText) findViewById(R.id.address);
                            addressEdit.setText(data.getString("address_string"));


                            //Log.d("ASDF",res.toString());


                        } catch (JSONException e) {
                            //Log.d("ASDF","ELEH");
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        NetworkResponse networkResponse = error.networkResponse;

                        if(networkResponse == null){

                            Toast.makeText(EditProfileActivity.this, "Connection Error",Toast.LENGTH_SHORT).show();

                        }else{
                            int a = networkResponse.statusCode;
                            if(networkResponse.statusCode == 403){
                                Toast.makeText(EditProfileActivity.this, "TOKEN INVALID, PLEASE RE LOG",Toast.LENGTH_SHORT).show();

                            }

                            if(networkResponse.statusCode == 500){
                                Toast.makeText(EditProfileActivity.this, "INVALID CREDENTIALS",Toast.LENGTH_SHORT).show();
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

    public void callUpdateProfile(){

        RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
        final Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();


        String url = Connector.getURL() +"/api/v1/student/update";

        final DelayedProgressDialog dialog = new DelayedProgressDialog();
        dialog.show(getSupportFragmentManager(),"loading");
        dialog.setCancelable(false);


        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject res = new JSONObject(response);
//                            String name = String.valueOf(res.get("name"));
                            //Log.d("ASDF", "YOSAH + \n"+res.toString() );

                            SharedPreferences sharedPreferences = getSharedPreferences("profile", Context.MODE_PRIVATE);

                            EditText firstNameEdit = (EditText) findViewById(R.id.firstname_edit);
                            EditText lastNameEdit = (EditText) findViewById(R.id.lastname_edit);



                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("fullname",firstNameEdit.getText().toString() + " " + lastNameEdit.getText().toString());
                            editor.apply();

                            Toast.makeText(EditProfileActivity.this, "Success", Toast.LENGTH_SHORT).show();
                            finish();

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

                            Toast.makeText(EditProfileActivity.this, "Connection Error",Toast.LENGTH_SHORT).show();

                        }else{
                            int a = networkResponse.statusCode;
                            if(networkResponse.statusCode == 401){
                            }

                            if(networkResponse.statusCode == 500){
                                Toast.makeText(EditProfileActivity.this, "ERROR",Toast.LENGTH_SHORT).show();
                            }

                            if(networkResponse.statusCode != 401){


                            }

                        }



                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> reqBody = new HashMap<String, String>();

                EditText firstNameEdit = (EditText) findViewById(R.id.firstname_edit);

                EditText lastNameEdit = (EditText) findViewById(R.id.lastname_edit);

                EditText addressEdit = (EditText) findViewById(R.id.address);

//                EditText usernameText = (EditText) findViewById(R.id.input_username_sign_up);
//                EditText fullnameText  = (EditText)  findViewById(R.id.input_fullname_signup);

                String firstName = firstNameEdit.getText().toString();
                String lastName = lastNameEdit.getText().toString();

//                String username = usernameText.getText().toString();
//                String fullname = fullnameText.getText().toString();

                Spinner genderSpinner = (Spinner) findViewById(R.id.gender_spinner);

                reqBody.put("gender", genderSpinner.getSelectedItem().toString());

                reqBody.put("address_string", addressEdit.getText().toString());

                reqBody.put("fullname", firstName+" "+lastName);

                String token = "";
                SharedPreferences sharedPreferences = getSharedPreferences("profile", Context.MODE_PRIVATE);

                if(!sharedPreferences.getString("token","").equals("")) {
                    token = sharedPreferences.getString("token","");
                }

                reqBody.put("token",token);

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

        requestQueue.add(stringRequest);

    }
}
