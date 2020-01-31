package com.github.newlight77.repository;

import com.github.newlight77.repository.database.HotelDatabase;
import com.github.newlight77.model.Room;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class RoomReadRepository {

    private HotelDatabase database;

    public RoomReadRepository(HotelDatabase database) {
        this.database = database;
    }

    public List<Room> getAvailableRooms() {
        return database.getRooms().stream()
                .filter(Room::isAvailable)
                .collect(toList());
    }
}
