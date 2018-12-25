package com.example.rahaf.safeheart1;

import android.content.Intent;
import android.media.audiofx.DynamicsProcessing;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

public class Login_Activity extends AppCompatActivity {
    Button loginBtn;
    TextView txtID, txtPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginBtn=(Button) findViewById(R.id.loginBtn);
        txtID=(TextView)  findViewById(R.id.txtID);
        txtPassword=(TextView)  findViewById(R.id.txtPassword);
    }

    public void LoginButton(View view)
    {

        //Getting values from edit texts
        final String username = txtID.getText().toString().trim();
        final String password = txtPassword.getText().toString().trim();

        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        Log.d("tag", "onResponse: "+ response);
                        JSONObject responseJSON = null;
                        try {
                            responseJSON = new JSONObject(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String status = null;
                        try {
                            if (responseJSON != null) {
                                status = responseJSON.getString("status");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            // String userName = responseJSON.getString("user");
                            if(status.equalsIgnoreCase(Config.LOGIN_SUCCESS)) {

                                String first_name = responseJSON.getString("firstName");


                                //Creating a shared preference
                                //SharedPreferences sharedPreferences = Login_Activity.this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                                //Creating editor to store values to shared preferences
                                //SharedPreferences.Editor editor = sharedPreferences.edit();

                                //Adding values to editor
                                //editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, true);
                                //editor.putString(Config.USER_SHARED_PREF, username);
                                //Saving values to editor
                                //editor.commit();
                                //Starting profile activity
                                //  Toast.makeText(Login_Activity.this,"Success from params", Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(Login_Activity.this, User_Activity.class);
                                intent.putExtra("ID", username);
                                intent.putExtra("firstName", first_name);
                                startActivity(intent);
                            }else{
                                //If the server response is not success
                                //Displaying an error message on toast
                                Toast.makeText(Login_Activity.this, "Invalid username or password", Toast.LENGTH_LONG).show();
                            }
                        }
                        catch (JSONException e) {
                            e.printStackTrace();}
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                //Adding parameters to request
                params.put(Config.KEY_USERNAME, username);
                params.put(Config.KEY_PASSWORD, password);

                //returning parameter
                return params;
            }


            @Override
            public byte[] getBody() throws AuthFailureError {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put(Config.KEY_USERNAME, username);
                    jsonObject.put(Config.KEY_PASSWORD, password);
                }catch(JSONException e)
                {

                }

                return jsonObject.toString().getBytes() ;
            }
            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);




    }

}
