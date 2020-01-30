package org.eventmodeling.startcleaning.usecase;

import org.eventmodeling.startcleaning.domain.Room;

public class StartRoomCleaning implements Command {
    private final Room room;

    StartRoomCleaning(Room room) {
        this.room = room;
    }

    public static StartRoomCleaning from(String roomId) {
        Room room = new Room(Integer.valueOf(roomId));
        return new StartRoomCleaning(room);
    }

    Room getRoom() {
        return room;
    }

    @Override
    public String toString() {
        return "StartRoomCleaning{" +
                "room=" + room +
                '}';
    }
}
