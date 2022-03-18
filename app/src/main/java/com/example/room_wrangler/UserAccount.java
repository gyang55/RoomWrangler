package com.example.room_wrangler;

public class UserAccount {

    private String emailId;
    private String password;
    private String idToken; // firebase Uid
    private String fName;
    private String studentId;


    //Must have the empty constructor


    public UserAccount(String emailId, String password, String fName, String studentId) {
        this.emailId = emailId;
        this.password = password;
        this.fName = fName;
        this.studentId = studentId;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
}
