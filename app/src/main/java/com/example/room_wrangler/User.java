package com.example.room_wrangler;

public class User {
    private static int userCount = 0;
    private final int userId;
    private final String userName;

    public User(String userName) {
        userCount++;
        this.userName = userName;
        this.userId = userCount;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }
}
