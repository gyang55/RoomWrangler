package com.example.room_wrangler;

import java.time.LocalDateTime;

/**
 * Booking details for rooms. The room object itself stores a list of bookings.
 */
public class RoomBooking {
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final User owner;

    public RoomBooking(LocalDateTime startTime, LocalDateTime endTime, User owner) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.owner = owner;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public User getOwner() {
        return owner;
    }
}
