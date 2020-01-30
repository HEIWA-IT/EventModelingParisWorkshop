package org.eventmodeling.startcleaning.domain;

import org.eventmodeling.startcleaning.domain.Room;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

public class CleaningPlanning {
    private Collection<Room> rooms = new HashSet<>();

    public void add(Room room) {
        rooms.add(room);
    }

    @Override
    public String toString() {
        return "CleaningPlanning{" +
                "rooms=" + rooms +
                '}';
    }

    public boolean isEmpty() {
        return false;
    }

    public Collection<Room> getRoomsToClean(LocalDate date) {
        return rooms;
    }
}
