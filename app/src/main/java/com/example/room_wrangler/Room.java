package com.example.room_wrangler;

import java.io.Serializable;

public class Room implements Serializable {
    int roomNumber;
    int roomPicture;
    int roomSize;
    boolean hasTV;
    boolean hasWhiteboard;
    String roomName;
    String roomDesc;

    public Room(int roomNumber, int roomPicture, int roomSize, boolean hasTV, boolean hasWhiteboard,
                String roomName, String roomDesc) {
        this.roomNumber = roomNumber;
        this.roomPicture = roomPicture;
        this.roomSize = roomSize;
        this.hasTV = hasTV;
        this.hasWhiteboard = hasWhiteboard;
        this.roomName = roomName;
        this.roomDesc = roomDesc;
    }

    public int getRoomNumber() {
        return roomNumber;
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

    public String getRoomName() {
        return roomName;
    }

    public boolean isHasTV() {
        return hasTV;
    }

    public boolean isHasWhiteboard() {
        return hasWhiteboard;
    }
}
