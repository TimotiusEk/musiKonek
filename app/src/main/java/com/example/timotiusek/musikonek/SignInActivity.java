package com.example.timotiusek.musikonek;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignInActivity extends AppCompatActivity {

    private final String TAG = "ASDF";

    @BindView(R.id.input_email__sign_in_act)
    EditText inputEmail;

    @BindView(R.id.input_password__sign_in_act)
    EditText inputPassword;

    @BindView(R.id.link_to_register__sign_in_act)
    TextView linkToRegister;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);

        sharedPreferences = getSharedPreferences("profile", Context.MODE_PRIVATE);
        if(!sharedPreferences.getString("email","").equals("")) {
            inputEmail.setText(sharedPreferences.getString("email",""));
        }

    }

    @OnClick(R.id.sign_in_btn__sign_in_act)
    public void signIn()
    {

        loginCall();




//        if(inputEmail.getText().toString().equals("admin") && inputPassword.getText().toString().equals("admin")){
//            Intent intent = new Intent(SignInActivity.this, MainActivity.class);
//            startActivity(intent);
//        } else{
//            Toast.makeText(this, "Email/Password Salah", Toast.LENGTH_SHORT).show();
//        }
    }

    @OnClick(R.id.link_to_register__sign_in_act)
    void goToRegisterPage(){
        startActivity(new Intent(this, SignUpActivity.class));
    }


    void loginCall(){

//        Log.d("ASDF","Called");

        RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
        final Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();
        String url = Connector.getURL() +"/api/v1/studentlogin";


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject res = new JSONObject(response);
//                            String name = String.valueOf(res.get("name"));
                            Log.d(TAG, "YOSAH + \n"+res.toString() );

                            if(res.getString("token")!=null){

//                                Toast.makeText(SignInActivity.this, "token is "+ res.getString("token"), Toast.LENGTH_SHORT).show();

//                                Bundle extras = new Bundle();
//                                extras.putString("email",inputEmail.getText().toString());
//                                extras.putString("username","USERNAME HERE");


                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("email",inputEmail.getText().toString());
                                editor.putString("token",res.getString("token"));
                                editor.putString("username",res.getString("username"));
                                editor.apply();


                                Intent intent = new Intent(SignInActivity.this, MainActivity.class);

//                                intent.putExtras(extras);

                                startActivity(intent);
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

                            Toast.makeText(SignInActivity.this, "Connection Error",Toast.LENGTH_SHORT).show();

                        }else{
                            int a = networkResponse.statusCode;
                            if(networkResponse.statusCode == 401){
                            }

                            if(networkResponse.statusCode == 500){
                                Toast.makeText(SignInActivity.this, "INVALID CREDENTIALS",Toast.LENGTH_SHORT).show();
                            }

                            if(networkResponse.statusCode != 401){

                                Log.d("ASDF","SHIT");

                            }

                        }



                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> reqBody = new HashMap<String, String>();

                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();

                try {
                    password = ShaConverter.SHA1(password);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                reqBody.put("email", email);
                reqBody.put("password", password);

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
