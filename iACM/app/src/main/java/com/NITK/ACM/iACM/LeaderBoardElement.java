package com.NITK.ACM.iACM;

public class LeaderBoardElement {
    String person_name;
    Integer points;
    String email;
    String admin;
    Integer streak;
    String uID;

    public LeaderBoardElement() {
    }

    public LeaderBoardElement(String title,Integer points) {
        this.person_name = title;
        this.points=points;
    }

    public String getPerson_name() {
        return person_name;
    }

    public void setPerson_name(String name) {
        this.person_name = name;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
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

    public void setStreak(Integer streak) {
        this.streak = streak;
    }

    public Integer getStreak() { return streak; }

    //public void setStreak(Integer Streak){ this.streak=Streak; }
}
