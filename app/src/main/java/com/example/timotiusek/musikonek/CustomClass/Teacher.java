package com.example.timotiusek.musikonek.CustomClass;

/**
 * Created by TimotiusEk on 4/3/2017.
 */

public class Teacher{

    private int image;
    private String name;
    private String haveTaughtSince;
    private int costPerMeeting;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;


    public Teacher(int image, String name, String haveTaughtSince, int costPerMeeting, String id) {
        this.image = image;
        this.name = name;
        this.haveTaughtSince = haveTaughtSince;
        this.costPerMeeting = costPerMeeting;
        this.id = id;
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

    public String getHaveTaughtSince() {
        return haveTaughtSince;
    }

    public void setHaveTaughtSince(String haveTaughtSince) {
        this.haveTaughtSince = haveTaughtSince;
    }

    public int getCostPerMeeting() {
        return costPerMeeting;
    }

    public void setCostPerMeeting(int costPerMeeting) {
        this.costPerMeeting = costPerMeeting;
    }

}
