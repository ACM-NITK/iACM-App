package com.NITK.ACM.iACM;

public class AttendeeRow {
    String name;
    String aID;
    Integer points;
    String attended;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getaID() {
        return aID;
    }

    public void setaID(String aID) {
        this.aID = aID;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public void setAttended(String attended) {
        this.attended = attended;
    }

    public String getAttended() {
        return attended;
    }
}
