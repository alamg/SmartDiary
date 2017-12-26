package com.cou.cse.alamgir.smartdiary.Model;

/**
 * Created by Alamgir on 4/19/2017.
 */

public class Teacher {
    private String teacherName;
    private String Designation;
    private String Email;
    private String Mobile;

    public Teacher() {

    }

    public Teacher(String teacherName, String designation, String email, String mobile) {
        this.teacherName = teacherName;
        Designation = designation;
        Email = email;
        Mobile = mobile;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getDesignation() {
        return Designation;
    }

    public void setDesignation(String designation) {
        Designation = designation;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }
}
