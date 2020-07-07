package com.advancedandroid.assignment_advanced_android.mModel;

public class mClass {
    private int id;
    private String classCode;
    private String speciality;

    public mClass() {
    }

    public mClass(String classCode, String speciality) {
        this.classCode = classCode;
        this.speciality = speciality;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }
}
