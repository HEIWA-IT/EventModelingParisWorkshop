package org.eventmodeling.startcleaning.domain;

import java.util.Objects;

public class RoomCleaningStarted implements Event {
    private final Room room;

    public RoomCleaningStarted(Room room) {
        this.room = room;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomCleaningStarted that = (RoomCleaningStarted) o;
        return Objects.equals(room, that.room);
    }

    @Override
    public int hashCode() {
        return Objects.hash(room);
    }

    @Override
    public String toString() {
        return "RoomCleaningStarted{" +
                "room=" + room +
                '}';
    }
}
