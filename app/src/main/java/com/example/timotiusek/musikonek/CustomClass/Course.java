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
    private String id;

    private String teacherID;
    private int durationMinute;

    private String skillID;



    public Course(int teacherImg, String teacherName, String courseName, String courseDesc, int courseDuration, String coursePrice, String id) {
        this.teacherImg = teacherImg;
        this.teacherName = teacherName;
        this.courseName = courseName;
        this.courseDesc = courseDesc;
        this.courseDuration = courseDuration;
        this.coursePrice = coursePrice;
        this.id = id;
    }

    public Course(int teacherImg, String teacherName, String courseName, String courseDesc, int courseDuration, String coursePrice, String id, int durationMinute, String teacherID, String skillID) {
        this.teacherImg = teacherImg;
        this.teacherName = teacherName;
        this.courseName = courseName;
        this.courseDesc = courseDesc;
        this.courseDuration = courseDuration;
        this.coursePrice = coursePrice;
        this.teacherID = teacherID;
        this.skillID = skillID;
        this.durationMinute = durationMinute;
        this.id = id;
    }

    public String getTeacherID() {
        return teacherID;
    }

    public String getSkillID() {
        return skillID;
    }

    public void setSkillID(String skillID) {
        this.skillID = skillID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTeacherID(String teacherID) {
        this.teacherID = teacherID;
    }

    public int getDurationMinute() {
        return durationMinute;
    }

    public void setDurationMinute(int durationMinute) {
        this.durationMinute = durationMinute;
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
