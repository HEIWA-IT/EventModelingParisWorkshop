package org.eventmodeling.startcleaning.domain;

import java.util.Objects;

public class Room {
    private final int id;

    public Room(int roomId) {
        id = roomId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return id == room.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                '}';
    }

}
