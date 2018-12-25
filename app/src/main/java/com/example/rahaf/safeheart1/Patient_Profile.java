package com.example.rahaf.safeheart1;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

import java.util.HashMap;
import java.util.Map;

public class Patient_Profile extends AppCompatActivity {
    Button patient_call,patient_location, patient_edit, patient_delete, home;
    TextView name,age,status;
    String patient_name, patient_age, patient_status, patient_number,patient_id,doctor_id, doctor_name;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient__profile);
        patient_call = (Button) findViewById(R.id.call);
        patient_location = (Button) findViewById(R.id.location);
        patient_edit = (Button)findViewById(R.id.edit);
        patient_delete= (Button) findViewById(R.id.del);
        home=(Button)findViewById(R.id.home);

        img = (ImageView) findViewById(R.id.imageView2);

        name = (TextView)findViewById(R.id.txtName);
        age=(TextView) findViewById(R.id.txtAge);
        status = (TextView) findViewById(R.id.txtStatus);

        Bundle extra = getIntent().getExtras();
        if(extra != null)
        {
            patient_id = extra.getString("patient_id");
            patient_name = extra.getString("name");
            patient_age = extra.getString("age");
            patient_status = extra.getString("status");
            patient_number =extra.getString("num");
            doctor_id = extra.getString("doctor_id");
            doctor_name = extra.getString("doctor_name");


        }
        name.setText(patient_name );
        age.setText(patient_age + " Years OLD");
        status.setText(patient_status + " BPM");

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Patient_Profile.this, User_Activity.class);
                intent.putExtra("ID" , doctor_id);
                intent.putExtra("firstName",doctor_name);
                startActivity(intent);
            }
        });

        //call patient

        patient_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+patient_number));
                if (ActivityCompat.checkSelfPermission(Patient_Profile.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(callIntent);

            }
        });

        //Edit patient Information
        patient_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Patient_Profile.this , edit_patient.class);
                intent.putExtra("patient_id",patient_id);
                intent.putExtra("doctor_id", doctor_id);
                intent.putExtra("doctor_name", doctor_name);
                startActivity(intent);


            }
        });

        //Delete Patient from System
        patient_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(Patient_Profile.this );
                builder.setTitle("Delete");
                builder.setMessage("Are you sure you want to delete this patient ")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deletePatient();
                            }
                        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        builder.setCancelable(true);
                    }
                });
                AlertDialog alert=builder.create();
                alert.show();

            }
        });


    }
    public void deletePatient()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.DELETE_PATIENT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        Log.d("TAG!1" , response);
                        // String userName = responseJSON.getString("user");
                        if (response.equalsIgnoreCase(Config.LOGIN_SUCCESS)) {

                            Toast.makeText(Patient_Profile.this, "Successfully you delete this patient!!", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(Patient_Profile.this, User_Activity.class);
                            intent.putExtra("ID",doctor_id );
                            intent.putExtra("firstName", doctor_name);
                            startActivity(intent);

                        } else {
                            //If the server response is not success
                            //Displaying an error message on toast
                            Toast.makeText(Patient_Profile.this, "Failed!! Please try AGAIN", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //Adding parameters to request
                params.put(Config.KEY_USERNAME, patient_id);


                //returning parameter
                return params;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put(Config.KEY_USERNAME, patient_id);

                } catch (JSONException e) {

                }

                return jsonObject.toString().getBytes();
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
