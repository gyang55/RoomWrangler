package com.example.room_wrangler;

import java.io.Serializable;
import java.time.LocalTime;

/**
 * Booking details for rooms. The room object itself stores a list of bookings.
 */
public class RoomBooking implements Serializable {
    private final String startTime;
    private final String endTime;
    private final String room;
    private final String owner;

    public RoomBooking(LocalTime startTime, LocalTime endTime, Room room, String owner) {
        this.startTime = startTime.toString();
        this.endTime = endTime.toString();
        this.room = room.getRoomNumber();
        this.owner = owner;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getRoom() {
        return room;
    }

    //    public User getOwner() {
//        return owner;
//    }
}
