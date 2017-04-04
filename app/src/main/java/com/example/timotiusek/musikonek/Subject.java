package com.example.timotiusek.musikonek;

/**
 * Created by TimotiusEk on 4/2/2017.
 */

public class Subject {

    private int image;
    private String name;
    private String teacher;
    private int coursePackage;
    private String dateGraduated;
    private int studentImage;
    private String time;
    private int teacherImage;
    private String status;

    /**
     * Todo: time ini nanti diganti datatype nya
     */

    public Subject(int image, String name) {
        this.image = image;
        this.name = name;
    }

    public Subject(int image, String name, String teacher) {
        this.image = image;
        this.name = name;
        this.teacher = teacher;
    }

    public Subject(int teacherImage, int coursePackage, String dateGraduated, int image, String name, String teacher, String status) {
        this.image = image;
        this.name = name;
        this.teacher = teacher;
        this.coursePackage = coursePackage;
        this.dateGraduated = dateGraduated;
        this.teacherImage = teacherImage;
        this.status = status;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public int getCoursePackage() {
        return coursePackage;
    }

    public void setCoursePackage(int coursePackage) {
        this.coursePackage = coursePackage;
    }

    public String getDateGraduated() {
        return dateGraduated;
    }

    public void setDateGraduated(String dateGraduated) {
        this.dateGraduated = dateGraduated;
    }

    public int getStudentImage() {
        return studentImage;
    }

    public void setStudentImage(int studentImage) {
        this.studentImage = studentImage;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getTeacherImage() {
        return teacherImage;
    }

    public void setTeacherImage(int teacherImage) {
        this.teacherImage = teacherImage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
