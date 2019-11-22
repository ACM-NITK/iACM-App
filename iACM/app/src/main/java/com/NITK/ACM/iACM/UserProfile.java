package com.NITK.ACM.iACM;

public class UserProfile {
    private String UserID;
    private String UserName ,
            Firstname ,
            Lastname ,
            Phone_number ,
            email ,
            dob ,
            Achievements ,
            SIG1 ,
            SIG2;
    private String Bio;
    private String AreasOfInterest;

    private String Points;
    private String Admin;
    private String Batch;

    public String getBatch() {
        return Batch;
    }

    public void setBatch(String batch) {
        Batch = batch;
    }

//    private Integer Points1;
//    private Integer Admin1;
//    private Integer Batch1;


    public UserProfile() {

    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getFirstname() {
        return Firstname;
    }

    public void setFirstname(String firstname) {
        Firstname = firstname;
    }

    public String getLastname() {
        return Lastname;
    }

    public void setLastname(String lastname) {
        Lastname = lastname;
    }

    public String getPhone_number() {
        return Phone_number;
    }

    public void setPhone_number(String phone_number) {
        Phone_number = phone_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAchievements() {
        return Achievements;
    }

    public void setAchievements(String achievements) {
        Achievements = achievements;
    }

    public String getSIG1() {
        return SIG1;
    }

    public void setSIG1(String SIG1) {
        this.SIG1 = SIG1;
    }

    public String getSIG2() {
        return SIG2;
    }

    public void setSIG2(String SIG2) {
        this.SIG2 = SIG2;
    }

    public String getBio() {
        return Bio;
    }

    public void setBio(String bio) {
        Bio = bio;
    }

    public String getAreasOfInterest() {
        return AreasOfInterest;
    }

    public void setAreasOfInterest(String areasOfInterest) {
        AreasOfInterest = areasOfInterest;
    }

//    public Integer getPoints() {
//        return Points1;
//    }
//
//    public void setPoints(Integer points) {
//        Points1 = points;
//    }

    public String getAdmin() {
        return Admin;
    }

    public void setAdmin(String admin) {
        Admin = admin;
    }
//
//    public Integer getBatch() {
//        return Batch1;
//    }
//
//    public void setBatch(Integer batch) {
//        Batch1 = batch;
//    }
}
