package com.NITK.ACM.iACM;

import java.util.Date;

public class EventsList {
    String title;
    Integer points;
    String sig;
    String venue;
    String eID;
    Date date;
    Date time;
    String details;
    Integer datevalue;

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public EventsList() {
    }

    public EventsList(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getPoints() {
        return points;
    }

    public String getSig() {
        return sig;
    }

    public void setSig(String sig) {
        this.sig = sig;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public void seteID(String eID) {
        this.eID = eID;
    }

    public String geteID() {
        return eID;
    }

    public void setDatevalue(Integer datevalue) {
        this.datevalue = datevalue;
    }

    public Integer getDatevalue() {
        return datevalue;
    }
}
