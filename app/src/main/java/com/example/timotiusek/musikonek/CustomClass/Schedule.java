package com.example.timotiusek.musikonek.CustomClass;

/**
 * Created by TimotiusEk on 5/6/2017.
 */

public class Schedule {
    private String name;
    private String dateAndTime;
    private String status;

    public Schedule(String name, String dateAndTime, String status) {
        this.name = name;
        this.dateAndTime = dateAndTime;
        this.status = status;
    }

    public Schedule(String name, String dateAndTime) {
        this.name = name;
        this.dateAndTime = dateAndTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
