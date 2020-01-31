package com.github.newlight77.repository.database;

import com.github.newlight77.model.Room;
import java.util.Collection;

public class HotelDatabase {

    private Rooms rooms;

    public HotelDatabase() {
        this(5);
    }
    public HotelDatabase(int roomsCount) {
        this.rooms = new Rooms(roomsCount);
    }

    public Collection<Room> getRooms() {
        return rooms.allRooms();
    }
}
