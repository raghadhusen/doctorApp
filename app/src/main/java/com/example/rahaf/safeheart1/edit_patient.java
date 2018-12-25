package com.example.rahaf.safeheart1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.HashMap;
import java.util.Map;

public class edit_patient extends AppCompatActivity {
    EditText patient_id, patient_first, patient_last, patient_phone, patient_age , patient_pass;
    Button save,exit;
    String patientID, doctor_id, doctor_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_patient);
        patient_id =(EditText) findViewById(R.id.patientID);
        patient_first =(EditText) findViewById(R.id.patientFirst);
        patient_last =(EditText) findViewById(R.id.patientLast);
        patient_phone =(EditText) findViewById(R.id.phone);
        patient_age =(EditText) findViewById(R.id.age);
        patient_pass =(EditText) findViewById(R.id.pass);

        save=(Button) findViewById(R.id.editInfo);
        exit =(Button) findViewById(R.id.exit);

        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
            patientID = extras.getString("patient_id");
            doctor_id = extras.getString("doctor_id");
            doctor_name = extras.getString("doctor_name");
        }
        loadData();

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(edit_patient.this, User_Activity.class);
                intent.putExtra("ID",doctor_id );
                intent.putExtra("firstName", doctor_name);
                startActivity(intent);
            }
        });

        }
        public void loadData()
        {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.PATIENT_INFO,
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
                                    String id = responseJSON.getString("id");
                                    String first_name = responseJSON.getString("firstName");
                                    String last_name = responseJSON.getString("lastName");
                                    String phone =responseJSON.getString("phone");
                                    String age = responseJSON.getString("age");
                                    String password = responseJSON.getString("pass");

                                    patient_id.setText(id);
                                    patient_first.setText(first_name);
                                    patient_last.setText(last_name);
                                    patient_phone.setText(phone);
                                    patient_age.setText(age);
                                    patient_pass.setText(password);


                                }else{
                                    //If the server response is not success
                                    //Displaying an error message on toast
                                    Toast.makeText(edit_patient.this, "Something went WRONG during upload patient info", Toast.LENGTH_LONG).show();
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
                    params.put(Config.KEY_USERNAME, patientID);

                    //returning parameter
                    return params;
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put(Config.KEY_USERNAME, patientID);
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

        public void saveChanges(View view)
        {
            String id = patient_id.getText().toString().trim();
            final String first_name = patient_first.getText().toString().trim();
            final String last_name = patient_last.getText().toString().trim();
            final String phone = patient_phone.getText().toString().trim();
            final String age = patient_age.getText().toString().trim();
            final String password = patient_pass.getText().toString().trim();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.UPDATE_INFO,
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
                                if(status.equalsIgnoreCase(Config.LOGIN_SUCCESS)) {

                                    Toast.makeText(edit_patient.this, "Successfully Save Changes" ,Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(edit_patient.this,edit_patient.class);
                                    intent.putExtra("patient_id",patientID);
                                    intent.putExtra("doctor_id", doctor_id);
                                    intent.putExtra("doctor_name", doctor_name);
                                    startActivity(intent);



                                }else{
                                    //If the server response is not success
                                    //Displaying an error message on toast
                                    Toast.makeText(edit_patient.this, "Something went WRONG during upload patient info", Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

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
                    params.put(Config.KEY_USERNAME, patientID);

                    //returning parameter
                    return params;
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put(Config.KEY_USERNAME, patientID);
                        jsonObject.put("firstName", first_name);
                        jsonObject.put("lastName", last_name);
                        jsonObject.put("password", password);
                        jsonObject.put("phone", phone);
                        jsonObject.put("age", age);

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
