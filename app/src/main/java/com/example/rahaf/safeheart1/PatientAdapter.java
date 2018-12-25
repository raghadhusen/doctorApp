package com.example.rahaf.safeheart1;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.PatientViewHolder> {
    private Context mCtx;
    private List<Patient> patientList;

    public PatientAdapter(Context mCtx, List<Patient> patientList) {
        this.mCtx = mCtx;
        this.patientList = patientList;
    }

    @NonNull
    @Override
    public PatientViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //return viewHolder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_layout, null);
        return new PatientViewHolder(view); }

    @Override
    public void onBindViewHolder(@NonNull PatientViewHolder holder, int i) {
        final Patient patient = patientList.get(i);

        holder.textViewName.setText(patient.getFirst_name()+" "+patient.getLast_name());
        holder.textViewRate.setText(patient.getHeartRate());
        /**Glide.with(mCtx)
                .load(patient.getImage()).into(holder.imageView);**/
        holder.patients_layout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String patient_id = patient.getId();
                String name = patient.getFirst_name()+" "+patient.getLast_name();
                String age = patient.getAge();
                String status = patient.getHeartRate();
                String num = patient.getNum();
                String doctor_id = patient.getDoctor_id();
                String doctor_name = patient.getDoctor_name();

                Intent intent = new Intent(mCtx ,Patient_Profile.class );
                intent.putExtra("patient_id",patient_id);
                intent.putExtra("name" , name);
                intent.putExtra("age" , age);
                intent.putExtra("status",status);
                intent.putExtra("num", num);
                intent.putExtra("doctor_id" , doctor_id);
                intent.putExtra("doctor_name" , doctor_name);
                mCtx.startActivity(intent);

            }
        });





    }

    @Override
    public int getItemCount() {
        return patientList.size();
    }

    class PatientViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageView;
        TextView textViewName, textViewRate;
        RelativeLayout patients_layout;
        public PatientViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewRate = itemView.findViewById(R.id.textViewRating);
            patients_layout = itemView.findViewById(R.id.patients_list);

        }
    }




}
