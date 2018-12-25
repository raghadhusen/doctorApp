package com.example.rahaf.safeheart1;

import android.app.Notification;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User_Activity extends AppCompatActivity {
    RecyclerView recyclerView;
    PatientAdapter adapter;
    TextView txtName;
    List<Patient> patientList;
    String doctor_name="";
    String doctorID = "";
    EditText searchTxt;
    Button addPatient, seeApp, addNewApp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        txtName=(TextView) findViewById(R.id.txtName);
        addPatient= (Button) findViewById(R.id.add_patient);
        seeApp =(Button)findViewById(R.id.see_appointments);
        addNewApp = (Button)findViewById(R.id.add_appointments);

        patientList = new ArrayList<>();


        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager( new LinearLayoutManager(this));

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            doctorID = extras.getString("ID");
            doctor_name = extras.getString("firstName");

        }
        txtName.setText(doctor_name);
        loadPatientsList();


        adapter = new PatientAdapter(this,patientList);
        recyclerView.setAdapter(adapter);

    }

    private void loadPatientsList()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.PATIENTS_LIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("tag", "onResponse: "+ response);
                        try {
                            JSONArray patients = new JSONArray(response);
                            for(int i =0 ; i<patients.length();i++)
                            {
                                JSONObject patientObject = patients.getJSONObject(i);
                                String patientID = patientObject.getString("patient_id");
                                String first_name = patientObject.getString("firstName");
                                String last_name = patientObject.getString("lastName");
                                String rate = patientObject.getString("heartRate");
                                String num = patientObject.getString("num");
                                String age = patientObject.getString("age");

                                Patient patient = new Patient(patientID, first_name, last_name , rate, num, age,doctorID,doctor_name);

                                patientList.add(patient);

                            }
                            adapter = new PatientAdapter(User_Activity.this,patientList);
                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }






                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(User_Activity.this,error.getMessage(),Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            public byte[] getBody() throws AuthFailureError {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put(Config.KEY_USERNAME, doctorID);

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


        Volley.newRequestQueue(this).add(stringRequest);
    }
    public void AddPatient(View view)
    {
        Intent intent = new Intent(User_Activity.this, new_patient.class);
        intent.putExtra("ID",doctorID);
        intent.putExtra("firstName",doctor_name);
        startActivity(intent);
    }
}

