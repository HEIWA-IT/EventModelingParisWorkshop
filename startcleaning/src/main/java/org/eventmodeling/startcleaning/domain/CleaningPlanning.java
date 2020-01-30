package org.eventmodeling.startcleaning.domain;

import org.eventmodeling.startcleaning.domain.Room;

import java.time.LocalDate;
import java.util.*;

public class CleaningPlanning {
    private HashMap<LocalDate, Collection<Room>> planning = new HashMap<>();

    public void add(LocalDate date, Room room) {
        Collection<Room> rooms = Optional.ofNullable(planning.get(date)).orElse(new HashSet<>());
        rooms.add(room);
        planning.put(date, rooms);
    }

    public boolean isEmpty() {
        return false;
    }

    public Collection<Room> getRoomsToClean(LocalDate date) {
        return Optional.ofNullable(planning.get(date)).orElse(new HashSet<>());
    }
}
