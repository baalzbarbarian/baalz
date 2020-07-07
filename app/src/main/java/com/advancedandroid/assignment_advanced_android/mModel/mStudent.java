package com.advancedandroid.assignment_advanced_android.mModel;

public class mStudent {
    private int id;
    private String classCode;
    private String studentCode;
    private String name;
    private String born;

    public mStudent() {
    }

    public mStudent(int id, String classCode, String studentCode, String name, String born) {
        this.id = id;
        this.classCode = classCode;
        this.studentCode = studentCode;
        this.name = name;
        this.born = born;
    }

    public String getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public String getBorn() {
        return born;
    }

    public void setBorn(String born) {
        this.born = born;
    }
}
