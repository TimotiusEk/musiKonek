package com.example.timotiusek.musikonek.CustomClass;

/**
 * Created by TimotiusEk on 8/5/2017.
 */

public class Course {
    private int teacherImg;
    private String teacherName;
    private String courseName;
    private String courseDesc;
    private int courseDuration;
    private String coursePrice;

    public Course(int teacherImg, String teacherName, String courseName, String courseDesc, int courseDuration, String coursePrice) {
        this.teacherImg = teacherImg;
        this.teacherName = teacherName;
        this.courseName = courseName;
        this.courseDesc = courseDesc;
        this.courseDuration = courseDuration;
        this.coursePrice = coursePrice;
    }

    public int getTeacherImg() {
        return teacherImg;
    }

    public void setTeacherImg(int teacherImg) {
        this.teacherImg = teacherImg;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseDesc() {
        return courseDesc;
    }

    public void setCourseDesc(String courseDesc) {
        this.courseDesc = courseDesc;
    }

    public int getCourseDuration() {
        return courseDuration;
    }

    public void setCourseDuration(int courseDuration) {
        this.courseDuration = courseDuration;
    }

    public String getCoursePrice() {
        return coursePrice;
    }

    public void setCoursePrice(String coursePrice) {
        this.coursePrice = coursePrice;
    }
}
