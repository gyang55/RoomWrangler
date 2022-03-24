package com.example.room_wrangler;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Booking details for rooms. The room object itself stores a list of bookings.
 */
public class RoomBooking implements Serializable {
    private final String date;
    private final String startTime;
    private final String endTime;
    private final String room;
    private final String owner;

    public RoomBooking(LocalDate date, LocalTime startTime, LocalTime endTime, Room room, String owner) {
        this.date = date.toString();
        this.startTime = startTime.toString();
        this.endTime = endTime.toString();
        this.room = room.getRoomNumber();
        this.owner = owner;
    }

    public RoomBooking(String date, String startTime, String endTime, String room, String owner) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.room = room;
        this.owner = owner;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getDate() {
        return date;
    }

    public String getOwner() {
        return owner;
    }

    public String getRoom() {
        return room;
    }

}
