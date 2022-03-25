package com.example.room_wrangler;

import java.io.Serializable;

import java.util.ArrayList;

/**
 * Booking details for rooms. The room object itself stores a list of bookings.
 */
public class RoomBooking implements Serializable {
    private final String date;
    private final ArrayList<String> duration;
    private final String roomNumber;
    private final String owner;

    public RoomBooking(String date, ArrayList<String> duration, Room room, String owner) {
        this.date = date;
        this.duration = duration;
        this.roomNumber = room.getRoomNumber();
        this.owner = owner;
    }

    public String getDate() {
        return date;
    }

    public ArrayList<String> getDuration() {
        return duration;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public String getOwner() {
        return owner;
    }
}
