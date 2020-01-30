package org.eventmodeling.startcleaning.usecase;

import org.eventmodeling.startcleaning.domain.Event;
import org.eventmodeling.startcleaning.domain.Room;

import java.time.LocalDate;

class ExtraCleaningRequested implements Event {
    private final LocalDate date;
    private final Room room;

    public ExtraCleaningRequested(int roomId, LocalDate date) {
        room = new Room(roomId);
        this.date = date;
    }

    @Override
    public String toString() {
        return "ExtraCleaningRequested{" +
                "room=" + room +
                ", date=" + date +
                '}';
    }

    public Room getRoom() {
        return room;
    }
}
