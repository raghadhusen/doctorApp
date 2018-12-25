package com.example.rahaf.safeheart1;

public class Patient {
    private String id;
    private String first_name;
    private String last_name;
    private String heartRate;
    private String image;
    private String age;
    private String num;
    private String doctor_id;
    private String doctor_name;

    public String getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(String doctor_id) {
        this.doctor_id = doctor_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(String heartRate) {
        this.heartRate = heartRate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public Patient(String id, String first_name, String last_name, String heartRate, String num, String age, String doctor_id, String doctor_name) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.heartRate = heartRate;
        this.num = num;
        this.age = age;
        this.doctor_id = doctor_id;
        this.doctor_name=doctor_name;
    }


    }

