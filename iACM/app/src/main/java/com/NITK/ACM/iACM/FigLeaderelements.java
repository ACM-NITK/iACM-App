package com.NITK.ACM.iACM;

public class FigLeaderelements {
    String person_name;
    String points;
    String email;
    String admin;
    String streak;
    String uID;

    public FigLeaderelements() {
    }

    public FigLeaderelements(String title,String points) {
        this.person_name = title;
        this.points=points;
    }

    public String getPerson_name() {
        return person_name;
    }

    public void setPerson_name(String name) {
        this.person_name = name;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getStreak() {
        return streak;
    }

    public void setStreak(String streak) {
        this.streak = streak;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail()
    {
        return email;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }



}
