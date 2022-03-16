package com.example.room_wrangler;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

public class Room implements Serializable {

    private final int roomPicture;
    private final int roomSize;

    private final boolean hasTV;
    private final boolean hasWhiteboard;
    private final String maxNumOfPeople;
    private final String roomNumber;
    private final String roomDesc;
    private RoomBooking[] bookings;

    public Room(int roomPicture, int roomSize, String maxNumOfPeople, boolean hasTV, boolean hasWhiteboard, String roomNumber, String roomDesc) {
        this.roomPicture = roomPicture;
        this.roomSize = roomSize;
        this.maxNumOfPeople = maxNumOfPeople;
        this.hasTV = hasTV;
        this.hasWhiteboard = hasWhiteboard;
        this.roomNumber = roomNumber;
        this.roomDesc = roomDesc;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public String getMaxNumOfPeople() {
        return maxNumOfPeople;
    }

    public int getRoomPicture() {
        return roomPicture;
    }

    public int getRoomSize() {
        return roomSize;
    }

    public String getRoomDesc() {
        return roomDesc;
    }

    public boolean isHasTV() {
        return hasTV;
    }

    public boolean isHasWhiteboard() {
        return hasWhiteboard;
    }
}
