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

public class new_patient extends AppCompatActivity {
    EditText patient_id, patient_first, patient_last, patient_phone, patient_age , patient_pass;
    Button add_patient,exit;
    String doctorID, doctorName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_patient);

        patient_id =(EditText) findViewById(R.id.patientID);
        patient_first =(EditText) findViewById(R.id.patientFirst);
        patient_last =(EditText) findViewById(R.id.patientLast);
        patient_phone =(EditText) findViewById(R.id.phone);
        patient_age =(EditText) findViewById(R.id.age);
        patient_pass =(EditText) findViewById(R.id.pass);

        add_patient=(Button) findViewById(R.id.addPatient);
        exit =(Button) findViewById(R.id.exit);

        Bundle extra = getIntent().getExtras();
        if(extra!= null)
        {
            doctorID = extra.getString("ID");
            doctorName=extra.getString("firstName");

        }
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(new_patient.this,User_Activity.class);
                intent.putExtra("ID", doctorID);
                intent.putExtra("firstName", doctorName);
                startActivity(intent);

            }
        });



    }

    public void addNewPatient(View view)
    {
        final String patientID = patient_id.getText().toString().trim();
        final String first_name = patient_first.getText().toString().trim();
        final String last_name = patient_last.getText().toString().trim();
        final String phone = patient_phone.getText().toString().trim();
        final String age = patient_age.getText().toString().trim();
        final String password = patient_pass.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.ADD_PATIENT,
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

                                Toast.makeText(new_patient.this, "Successfully ADD NEW PATIENT" ,Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(new_patient.this,User_Activity.class);
                                intent.putExtra("ID", doctorID);
                                intent.putExtra("firstName", doctorName);
                                startActivity(intent);



                            }else{
                                //If the server response is not success
                                //Displaying an error message on toast
                                Toast.makeText(new_patient.this, "Something went WRONG, Please Try new Patient ID", Toast.LENGTH_LONG).show();
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
            public byte[] getBody() throws AuthFailureError {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put(Config.KEY_USERNAME, doctorID);
                    jsonObject.put("id", patientID);
                    jsonObject.put("firstName", first_name);
                    jsonObject.put("lastName", last_name);
                    jsonObject.put("password", password);
                    jsonObject.put("age", age);
                    jsonObject.put("phone", phone);

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
