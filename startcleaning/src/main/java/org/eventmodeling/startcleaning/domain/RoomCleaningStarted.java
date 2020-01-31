package org.eventmodeling.startcleaning.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class RoomCleaningStarted implements Event {
    private final Room room;
    private final LocalDate cleaningDate;

    public RoomCleaningStarted(Room room, LocalDate cleaningStartDate) {
        this.room = room;
        this.cleaningDate = cleaningStartDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomCleaningStarted that = (RoomCleaningStarted) o;
        return room.equals(that.room) &&
                cleaningDate.equals(that.cleaningDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(room, cleaningDate);
    }

    @Override
    public String toString() {
        return "RoomCleaningStarted{" +
                "room=" + room +
                ", date=" + cleaningDate +
                '}';
    }

    public Room getRoom() {
        return room;
    }

    public LocalDate getCleaningDate() {
        return cleaningDate;
    }


}
