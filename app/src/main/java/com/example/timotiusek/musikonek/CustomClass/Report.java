package com.example.timotiusek.musikonek.CustomClass;

/**
 * Created by TimotiusEk on 5/6/2017.
 */

public class Report {
    String title;
    String dateAndTime;

    public Report(String title, String dateAndTime) {
        this.title = title;
        this.dateAndTime = dateAndTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(String date) {
        this.dateAndTime = date;
    }
}
